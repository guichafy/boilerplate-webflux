package guichafy.webfluxbestpratices.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;


    @GetMapping
    public Mono<CollectionModel<MovieVO>> getMovies(){
        return movieRepository.findAll()
                .map(this::convert)
                .map(m -> m.add(Link.of("/" + m.getName(), "_self")))
                .collectList()
                .map(movies -> CollectionModel.of(movies, Link.of("/movies", "movies")));

    }

    private MovieVO convert(Movie movie){
        return MovieVO.builder()
                .name(movie.getName())
                .build();
    }
}
