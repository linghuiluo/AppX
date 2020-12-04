package appx.utils.exceptions;

public class DetectionException extends RuntimeException {
    private final String id;
    private final String message;

    public DetectionException(String id, String msg, Throwable e) {
        super(msg, e);
        this.id = id;
        this.message = msg;
    }

    public DetectionException(String id, String msg) {
        this.id = id;
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDetectorId() {

        return id;
    }

}
