package su.baron.wax.waxnftdemo.metalwar;

public class MetalwarException extends RuntimeException {
    public MetalwarException() {
        super();
    }

    public MetalwarException(String message) {
        super(message);
    }

    public MetalwarException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetalwarException(Throwable cause) {
        super(cause);
    }

    protected MetalwarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
