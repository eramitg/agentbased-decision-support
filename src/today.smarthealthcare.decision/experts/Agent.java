package today.smarthealthcare.decision.experts;

import java.math.BigDecimal;
import java.util.List;

import today.smarthealthcare.decision.BalancingException;
import today.smarthealthcare.decision.nodes.info.NodeParametr;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Agent {

	private final List<RangesForParametr> parametrsRanges;

	private Agent(List<RangesForParametr> parametrsRanges) {
		this.parametrsRanges = ImmutableList.copyOf(parametrsRanges);
	}

	static class RangesForParametr {
		private String parametrId;
		private List<Range> ranges = Lists.newArrayList();

		public String getParametrId() {
			return parametrId;
		}

		public List<Range> getRanges() {
			return ImmutableList.copyOf(ranges);
		}

		@Override
		public String toString() {
			return "RangesForParametr [parametrId=" + parametrId + "]";
		}
	}

	static class Range {
		private BigDecimal low;
		private BigDecimal high;
		private BigDecimal score;

		public boolean acceptsPoint(BigDecimal point) {
			return point.compareTo(low) >= 0 && point.compareTo(high) < 0;
		}

		public BigDecimal getScore() {
			return score;
		}
	}

	public static AgentBuilder build() {
		return new AgentBuilder();
	}

	public static class AgentBuilder {

		private final List<RangesForParametr> parametrsRanges = Lists
				.newArrayList();

		private AgentBuilder() {
		}

		public ParametrBuilder param(String paramId) {
			return new ParametrBuilder(paramId, this);
		}

		public Agent build() {
			return new Agent(parametrsRanges);
		}
	}

	public static class ParametrBuilder {
		private RangesForParametr rangesForParametr;
		private final AgentBuilder expertBuilder;

		private ParametrBuilder(String paramId, AgentBuilder expertBuilder) {
			this.expertBuilder = expertBuilder;
			rangesForParametr = new RangesForParametr();
			rangesForParametr.parametrId = paramId;
			expertBuilder.parametrsRanges.add(rangesForParametr);
		}

		public ParametrBuilder newRange(BigDecimal low, BigDecimal high,
				BigDecimal score) {
			Range range = new Range();
			range.low = low;
			range.high = high;
			range.score = score;
			rangesForParametr.ranges.add(range);
			return this;
		}

		public ParametrBuilder param(String paramId) {
			return expertBuilder.param(paramId);
		}

		public Agent build() {
			return expertBuilder.build();
		}
	}

	public AgentBuilder builder() {
		return new AgentBuilder();
	}

	public BigDecimal getScoreForParametr(NodeParametr param) {
		for (RangesForParametr rangesForParametr : parametrsRanges) {
			if (rangesForParametr.parametrId.equals(param.getParametrId())) {
				for (Range range : rangesForParametr.getRanges()) {
					if (range.acceptsPoint(param.getValue())) {
						return range.getScore();
					}
				}
			}
		}
		throw new BalancingException("No score for parametr " + param
				+ " avl parametrs are " + parametrsRanges);
	}

}
