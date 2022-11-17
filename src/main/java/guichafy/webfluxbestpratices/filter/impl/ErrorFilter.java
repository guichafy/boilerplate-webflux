package guichafy.webfluxbestpratices.filter.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import guichafy.webfluxbestpratices.context.ContextField;
import guichafy.webfluxbestpratices.exception.ResponseException;
import guichafy.webfluxbestpratices.exception.ServerUnexpectedException;
import guichafy.webfluxbestpratices.logger.LogCode;
import guichafy.webfluxbestpratices.logger.ReactiveLogger;
import lombok.CustomLog;
import org.slf4j.MDC;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@CustomLog
public class ErrorFilter implements WebFilter {

    private final ObjectMapper objectMapper;

    public ErrorFilter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(t -> buildResponse(exchange, t));
    }

    private Mono<Void> buildResponse(ServerWebExchange exchange, Throwable t) {
        ResponseException e = getResponseException(t);
        HttpStatus status = e.getStatus();
        MultiValueMap<String, String> headers = null;
        byte[] bodyBytes = null;
        try {
            bodyBytes = marshalErrorResponseBody(e);
            headers = e.getHeaders();
        } catch (JsonProcessingException e1) {
            log.doLog("Error marshalling exception", LogCode.E_10000, e1);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return Mono.empty()
                .doOnEach(ReactiveLogger.logOnComplete(() -> logError(e)))
                .then(renderErrorResponse(exchange, status, bodyBytes, headers));
    }

    protected ResponseException getResponseException(final Throwable t) {
        if (t instanceof ResponseException) {
            return (ResponseException) t;
        }
        if (t instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException)t;
            return new ResponseException(responseStatusException.getStatus());
        }
        return new ServerUnexpectedException(t);
    }

    protected void logError(ResponseException e) {
        MDC.put(ContextField.ERROR, e.getError());
        MDC.put(ContextField.REASON, e.getReason());
        log.doLog("Error", LogCode.E_10000, e);
//        if (e instanceof ServerUnexpectedException) {
//
//            log.error("Error", e.getCause());
//        } else {
//            log.info("Error");
//        }
    }

    /**
     * Serialize the error response body into a byte array.
     *
     * @param e
     * @return byte array of the error response
     * @throws JsonProcessingException
     */
    protected byte[] marshalErrorResponseBody(ResponseException e) throws JsonProcessingException {
        if (e.getError() == null) {
            return null;
        }
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("error", e.getError());
        if (e.getReason() != null) {
            node.put("error_description", e.getReason());
        }
        if (e.getDetailMap() != null) {
            node.set("error_details", objectMapper.valueToTree(e.getDetailMap()));
        }
        return objectMapper.writer().writeValueAsBytes(node);
    }

    /**
     * Render a body response with status, body (as byte array) and headers.
     *
     * @param exchange
     * @param status
     * @param bodyBytes
     * @param headers
     * @return a completed {@link Mono}
     */
    protected Mono<Void> renderErrorResponse(
            ServerWebExchange exchange, HttpStatus status, byte[] bodyBytes, MultiValueMap<String, String> headers) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        if (headers != null) {
            response.getHeaders().addAll(headers);
        }
        if (bodyBytes == null) {
            return Mono.empty();
        }
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        DataBuffer buffer = response.bufferFactory().wrap(bodyBytes);
        return response.writeWith(Mono.just(buffer));
    }

}
