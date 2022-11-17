package guichafy.webfluxbestpratices.exception;

import org.springframework.http.HttpStatus;

public class UserAgentInvalidException extends ResponseException {

    public static final String  ERROR_MESSAGE  = "invalid_request";
    public static final HttpStatus status = HttpStatus.BAD_REQUEST;

    public UserAgentInvalidException() {
        super(HttpStatus.BAD_REQUEST, null, null);
    }

    public UserAgentInvalidException(String reason) {
        super(status, ERROR_MESSAGE, reason);
    }

    public UserAgentInvalidException(String reason, Throwable t) {
        super(status, ERROR_MESSAGE, reason, t);
    }
}
