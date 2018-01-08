package today.smarthealthcare.decision;

public class BalancingException extends RuntimeException {

	private static final long serialVersionUID = -6144716384119584067L;

	public BalancingException() {
		super();
	}

	public BalancingException(String message) {
		super(message);
	}

	public BalancingException(String message, Throwable cause) {
		super(message, cause);
	}

	public BalancingException(Throwable cause) {
		super(cause);
	}
}
