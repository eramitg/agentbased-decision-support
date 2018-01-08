package today.smarthealthcare.decision;

import java.util.List;

import today.smarthealthcare.decision.nodes.info.Node;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

public class DecisionMaker {

	private final IDecisionPolicy policy;

	public DecisionMaker(IDecisionPolicy policy) {
		this.policy = Preconditions.checkNotNull(policy);
	}

	public final Node getBestNodeToInvoke(List<Node> nodes) {
		Node node = doGetBestNode(policy.sort(nodes));
		return node;
	}

	protected Node doGetBestNode(List<Node> orderedNodes) {
		return Iterables.getFirst(orderedNodes, null);
	}

}
