package guichafy.webfluxbestpratices.exception;

import org.springframework.http.HttpStatus;

public class ServerUnexpectedException extends ResponseException {

    public ServerUnexpectedException(String reason) {
        this(reason, null);
    }

    public ServerUnexpectedException(Throwable throwable) {
        this(null, throwable);
    }

    public ServerUnexpectedException(String reason, Throwable t) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "server_unexpected", reason, t);
    }
}
