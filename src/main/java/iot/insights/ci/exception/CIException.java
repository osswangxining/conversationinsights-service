package iot.insights.ci.exception;

public class CIException extends Exception {

    private static final long serialVersionUID = 1L;

    private CIErrorCode errorCode;

    public CIException() {
        super();
    }

    public CIException(CIErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CIException(String message, CIErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CIException(String message, Throwable cause, CIErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CIException(Throwable cause, CIErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public CIErrorCode getErrorCode() {
        return errorCode;
    }

}
