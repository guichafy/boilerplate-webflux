package guichafy.webfluxbestpratices.filter.impl;

import guichafy.webfluxbestpratices.util.Constants;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class UserAgentFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var request = exchange.getRequest();
        var headers = request.getHeaders();
        var userAgent = headers.get(Constants.Headers.USER_AGENT);

        return chain.filter(exchange)
                .contextWrite(Context.of(Constants.Headers.USER_AGENT, userAgent));

    }
}
