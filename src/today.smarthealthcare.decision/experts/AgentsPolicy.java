package today.smarthealthcare.decision.experts;

import java.math.BigDecimal;
import java.util.List;

import today.smarthealthcare.decision.IDecisionPolicy;
import today.smarthealthcare.decision.experts.KeminiMedian.AgentOpinion;
import today.smarthealthcare.decision.nodes.info.Node;
import today.smarthealthcare.decision.nodes.info.NodeParametr;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ordering;

public class AgentsPolicy implements IDecisionPolicy {

	private final List<Agent> experts;

	public AgentsPolicy(List<Agent> experts) {
		this.experts = experts;
	}

	@Override
	public List<Node> sort(final List<Node> avlNodes) {
		List<AgentOpinion<Node>> ranges = FluentIterable.from(experts)
				.transform(input -> sortNodesAcordingToExpert(input, avlNodes)).toImmutableList();
		return KeminiMedian.createMedian(ranges).electBest().getRange();
	}

	private AgentOpinion<Node> sortNodesAcordingToExpert(
			final Agent expert, List<Node> avlNodes) {
		List<Node> sortedCopy = Ordering.natural().reverse().onResultOf(new Function<Node, BigDecimal>() {

			@Override
			public BigDecimal apply(Node input) {
				BigDecimal summ = BigDecimal.ZERO;
				for (NodeParametr param : input.getParametrs()) {
					summ = summ.add(expert.getScoreForParametr(param));
				}
				System.err.println(input + " score: " + summ);
				return summ;
			}
		}).sortedCopy(avlNodes);

		return AgentOpinion.createOpinion(sortedCopy);
	}
}
