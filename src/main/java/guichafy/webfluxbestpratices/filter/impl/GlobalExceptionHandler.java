package guichafy.webfluxbestpratices.filter.impl;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.boot.web.error.ErrorAttributeOptions.defaults;

@Component
@Order(-1)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

//    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties webProperties, ApplicationContext applicationContext) {
//        super(errorAttributes, webProperties.getResources(), applicationContext);
//    }

    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  WebProperties webProperties,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer codecConfigurer){
        super(errorAttributes, webProperties.getResources(), applicationContext);

        setMessageWriters(codecConfigurer.getWriters());

    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest serverRequest) {
        var errorAttributesOptions =  defaults();
        var errorAttributesMap =  getErrorAttributes(serverRequest, errorAttributesOptions);
        int status = (int) Optional.ofNullable(errorAttributesMap.get("status")).orElse(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorAttributesMap));



    }
}
