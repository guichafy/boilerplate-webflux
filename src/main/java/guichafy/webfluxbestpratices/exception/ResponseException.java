package guichafy.webfluxbestpratices.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ResponseException extends RuntimeException{

    /**
     * HTTP status to generate the error response.
     */
    private final HttpStatus status;

    /**
     * Error identifier.
     */
    private final String error;

    /**
     * Error description.
     */
    private final String reason;

    /**
     * Error description.
     */
    private Map<String, Object> detailMap;

    /**
     * Headers for the error response.
     */
    private MultiValueMap<String, String> headers;

    /**
     * Constructor with status.
     *
     * @param status
     */
    public ResponseException(HttpStatus status) {
        this(status, null, null);
    }

    /**
     * Constructor without exception.
     *
     * @param status
     * @param error
     * @param reason
     */
    public ResponseException(HttpStatus status, String error, String reason) {
        this(status, error, reason, null);
    }

    /**
     * Constructor with exception.
     *
     * @param status
     * @param error
     * @param reason
     * @param t
     */
    public ResponseException(HttpStatus status, String error, String reason, Throwable t) {
        super(reason, t);
        this.status = status;
        this.error = error;
        this.reason = reason;
    }

    /**
     * Add an HTTP header for the error response.
     *
     * @param headerName
     * @param headerValue
     * @return {@link ResponseException}
     */
    public ResponseException addHeader(String headerName, String headerValue) {
        if (headers == null) {
            headers = new LinkedMultiValueMap<String, String>();
        }
        headers.set(headerName, headerValue);
        return this;
    }

    /**
     * Add an error detail.
     *
     * @param key
     * @param value
     * @return {@link ResponseException}
     */
    public ResponseException addDetail(String key, Object value) {
        if (detailMap == null) {
            detailMap = new HashMap<String, Object>();
        }
        detailMap.put(key, value);
        return this;
    }
}
