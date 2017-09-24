package iot.insights.ci.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class CIErrorResponse {
    // HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    // Error code
    private final CIErrorCode errorCode;

    private final Date timestamp;

    protected CIErrorResponse(final String message, final CIErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = new java.util.Date();
    }

    public static CIErrorResponse of(final String message, final CIErrorCode errorCode, HttpStatus status) {
        return new CIErrorResponse(message, errorCode, status);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public CIErrorCode getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
