package today.smarthealthcare.decision.nodes.info;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

public class NodeParametr {

	private final String parametrId;
	private final BigDecimal parametrValue;

	public NodeParametr(String parametrId, BigDecimal parametrValue) {
		this.parametrId = Preconditions.checkNotNull(parametrId);
		this.parametrValue = parametrValue;
	}

	public String getParametrId() {
		return parametrId;
	}

	public BigDecimal getValue() {
		return parametrValue;
	}

	@Override
	public String toString() {
		return "NodeParametr [parametrId=" + parametrId + ", parametrValue="
				+ parametrValue + "]";
	}

}
