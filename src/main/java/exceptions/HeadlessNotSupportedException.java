package exceptions;

public class HeadlessNotSupportedException extends IllegalStateException {

    private static final long serialVersionUID = 1L;

	public HeadlessNotSupportedException(String browser) {
        super(String.format("Headless not supported for %s browser", browser));
    }
}
