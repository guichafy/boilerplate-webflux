package guichafy.webfluxbestpratices;

import guichafy.webfluxbestpratices.movie.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest
public class MovieRepositoryTest {

    @Autowired
    DatabaseClient client;

    @Autowired
    MovieRepository movieRepository;

    @Test
    public void testDatabaseClientExisted() {
        assertNotNull(client);
    }

    @Test
    public void testInsertAndQuery() {
        this.client
                .sql("INSERT INTO movies (name) VALUES('Um filme qualquer');")
                .then().block(Duration.ofSeconds(5));

        this.movieRepository.findAll()
                .collectList()
                .as(StepVerifier::create)

                .consumeNextWith( (movies) -> {
                     var result = movies.stream().filter(m -> "Um filme qualquer".equals(m.getName()))
                             .collect(Collectors.toList());

                     assertEquals(1, result.size());
                })
                .verifyComplete();

    }


}
