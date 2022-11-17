package guichafy.webfluxbestpratices;


import guichafy.webfluxbestpratices.movie.MovieVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "10000")
@ActiveProfiles("h2")
public class MovieControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36";
    private static final ParameterizedTypeReference TYPE_REFERENCE =  new ParameterizedTypeReference<CollectionModel<MovieVO>>() {};


    @Test
    public void getAllMovies_invalidUserAgent(){

        EntityExchangeResult<String> result = webTestClient.get().uri("/movies")
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .returnResult();

    }


    @Test
    public void getAllMovies_success(){

        EntityExchangeResult<CollectionModel<MovieVO>> result = webTestClient.get().uri("/movies")
                .header("User-Agent", USER_AGENT)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(TYPE_REFERENCE)
                .returnResult();


        var body  = result.getResponseBody();
        var content = body.getContent();
        Optional<Link> moviesLink = body.getLink("movies");


        Assert.assertEquals(3, content.size());
        Assert.assertEquals(Boolean.TRUE,moviesLink.isPresent());

    }
}
