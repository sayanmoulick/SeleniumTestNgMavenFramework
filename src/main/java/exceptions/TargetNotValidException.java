package exceptions;

public class TargetNotValidException extends IllegalStateException {

    private static final long serialVersionUID = 1L;

	public TargetNotValidException(String target) {
        super(String.format("Target %s not supported. Use either local or gird", target));
    }

}
