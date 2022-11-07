package guichafy.webfluxbestpratices.movie.client;

import guichafy.webfluxbestpratices.movie.Movie;

import java.util.Arrays;
import java.util.List;

public class MovieClientOther implements MovieClient{
    @Override
    public List<Movie> getMovieResults() {
        return Arrays.asList(new Movie(1L, "Teste Outros"), new Movie(2L, "Teste outros 2"));
    }
}
