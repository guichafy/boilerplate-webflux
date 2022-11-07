package guichafy.webfluxbestpratices.filter;


import guichafy.webfluxbestpratices.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sample")
public class SampleError {


    @GetMapping
    public Mono<ResponseEntity<?>> sampleError(){

        return Mono.deferContextual(Mono::just)
                .map(contextView -> {
                    var userAgent = contextView.get(Constants.Headers.USER_AGENT);
                    if(userAgent != null){
                        throw new RuntimeException("Erro forçado");
                    }
                    return userAgent;
                })
                .map(userAgent -> ResponseEntity.ok(userAgent) );
    }
}
