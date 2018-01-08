package today.smarthealthcare.decision.experts;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;

import today.smarthealthcare.decision.experts.KeminiMedian.AgentOpinion;

public class BinaryComporation<T extends Comparable<T>> {

	private final AgentOpinion<T> opinion;
	private final int[][] comporationMatrix;

	public BinaryComporation(AgentOpinion<T> opinion) {
		this.opinion = Preconditions.checkNotNull(opinion);
		List<T> range = opinion.getRange();
		int optionsCount = range.size();
		List<T> sortedOptions = Ordering.natural().sortedCopy(range);
		comporationMatrix = new int[optionsCount][optionsCount];
		for (int i = 0; i < optionsCount; i++) {
			T baseElement = sortedOptions.get(i);
			int basePosition = opinion.getPosition(baseElement);
			for (int j = 0; j < optionsCount; j++) {
				T comparedElement = sortedOptions.get(j);
				int comparedPosition = opinion.getPosition(comparedElement);
				if (basePosition <= comparedPosition) {
					comporationMatrix[i][j] = 1;
				} else {
					comporationMatrix[i][j] = 0;
				}
			}
		}
	}

	public static <T extends Comparable<T>> BinaryComporation<T> create(
			AgentOpinion<T> opinion) {
		return new BinaryComporation<T>(opinion);
	}

	public AgentOpinion<T> getOpinion() {
		return opinion;
	}

	public int[][] getComporationMatrix() {
		return comporationMatrix;
	}
}
