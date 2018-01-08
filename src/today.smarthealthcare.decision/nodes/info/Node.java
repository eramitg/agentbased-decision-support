package today.smarthealthcare.decision.nodes.info;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class Node implements Comparable<Node> {

	private final String nodeId;

	private final List<NodeParametr> parametrs;

	public Node(String nodeId, List<NodeParametr> parametrs) {
		this.nodeId = Preconditions.checkNotNull(nodeId);
		this.parametrs = ImmutableList.copyOf(parametrs);
	}

	public String getNodeId() {
		return nodeId;
	}

	public List<NodeParametr> getParametrs() {
		return parametrs;
	}

	@Override
	public String toString() {
		return "Node [nodeId=" + nodeId + "]";
	}

	@Override
	public int compareTo(Node o) {
		return nodeId.compareTo(o.nodeId);
	}
}
