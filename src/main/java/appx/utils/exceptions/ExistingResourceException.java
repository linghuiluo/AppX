package appx.utils.exceptions;

public class ExistingResourceException extends RuntimeException {

    private final String message;

    public ExistingResourceException(String msg, Throwable e) {
        super(msg, e);
        this.message = msg;
    }

    public ExistingResourceException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
