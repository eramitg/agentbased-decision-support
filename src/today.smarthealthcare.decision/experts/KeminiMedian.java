package today.smarthealthcare.decision.experts;

import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import today.smarthealthcare.decision.BalancingException;

public class KeminiMedian<T extends Comparable<T>> {

	public static class AgentOpinion<T extends Comparable<T>> {
		private List<T> range;

		public List<T> getRange() {
			return ImmutableList.copyOf(range);
		}

		public void setRange(List<T> range) {
			this.range = Preconditions.checkNotNull(range);
		}

		public static <T extends Comparable<T>> AgentOpinion<T> createOpinion(
				List<T> range) {
			AgentOpinion<T> expertOpinion = new AgentOpinion<T>();
			expertOpinion.setRange(range);
			return expertOpinion;
		}

		public int getPosition(T object) {
			return range.indexOf(object);
		}
	}

	private List<AgentOpinion<T>> opinions;
	private Map<AgentOpinion<T>, BinaryComporation<T>> comporationMap;

	public KeminiMedian(List<AgentOpinion<T>> opinions) {
		checkSizeOfAllOpinionsIsSame(opinions);
		this.opinions = ImmutableList.copyOf(opinions);
		FluentIterable<BinaryComporation<T>> comporations = FluentIterable
				.from(opinions).transform(input -> BinaryComporation.create(input));

		comporationMap = Maps.newHashMap();
		for (BinaryComporation<T> binariComporation : comporations) {
			comporationMap.put(binariComporation.getOpinion(),
					binariComporation);
		}
	}

	private void checkSizeOfAllOpinionsIsSame(List<AgentOpinion<T>> opinions) {
		int baseSize = opinions.get(0).range.size();
		for (AgentOpinion<T> expertOpinion : opinions) {
			if (expertOpinion.range.size() != baseSize) {
				throw new BalancingException(
						"Opnions have diffrent number of nodes");
			}
		}
	}

	public static <T extends Comparable<T>> KeminiMedian<T> createMedian(
			List<AgentOpinion<T>> opinions) {
		return new KeminiMedian<T>(opinions);
	}

	public AgentOpinion<T> electBest() {
		int opinionsCount = opinions.size();
		int[][] pareComporationMatrix = new int[opinionsCount][opinionsCount];

		for (int i = 0; i < opinions.size(); i++) {
			AgentOpinion<T> baseOpinion = opinions.get(i);
			BinaryComporation<T> baseOptionComporation = comporationMap
					.get(baseOpinion);
			for (int j = 0; j < opinions.size(); j++) {
				AgentOpinion<T> comparedOpinion = opinions.get(j);
				BinaryComporation<T> comparedOptionComporation = comporationMap
						.get(comparedOpinion);
				int distance = Math.abs(computeDistance(baseOptionComporation, comparedOptionComporation));
				pareComporationMatrix[i][j] = distance;
				pareComporationMatrix[j][i] = distance;
			}
		}

		int bestOptionIndex = -1;
		int minDisagreement = Integer.MAX_VALUE;

		for (int i = 0; i < pareComporationMatrix.length; i++) {
			int[] disagreement = pareComporationMatrix[i];
			int value = summ(disagreement);

			if (minDisagreement > value) {
				minDisagreement = value;
				bestOptionIndex = i;
			}
		}

		return opinions.get(bestOptionIndex);
	}

	private int computeDistance(BinaryComporation<T> baseOptionComporation,
			BinaryComporation<T> comparedOptionComporation) {
		int[][] baseMatrix = baseOptionComporation.getComporationMatrix();
		int[][] matrixToSubtract = comparedOptionComporation.getComporationMatrix();
		int[][] result = subtractMatrix(baseMatrix, matrixToSubtract);

		int summ = 0;
		for (int[] columns : result) {
			summ += summ(columns);
		}
		return summ;
	}

	private int summ(int[] columns) {
		int summ = 0;
		for (int row : columns) {
			summ += Math.abs(row);
		}
		return summ;
	}

	private int[][] subtractMatrix(int[][] baseMatrix, int[][] matrixToSubtract) {

		int[][] result = new int[baseMatrix.length][baseMatrix.length];

		for (int i = 0; i < baseMatrix.length; i++) {
			for (int j = 0; j < baseMatrix.length; j++) {
				result[i][j] = baseMatrix[i][j] - matrixToSubtract[i][j];
			}
		}
		return result;
	}
}
