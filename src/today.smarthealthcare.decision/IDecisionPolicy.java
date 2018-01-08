package today.smarthealthcare.decision;

import java.util.List;

import today.smarthealthcare.decision.nodes.info.Node;

public interface IDecisionPolicy {

	/**
	 * Sorts nodes according they health.
	 */
	List<Node> sort(List<Node> avlNodes);
}
