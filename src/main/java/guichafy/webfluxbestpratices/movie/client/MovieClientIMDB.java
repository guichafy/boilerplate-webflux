package guichafy.webfluxbestpratices.movie.client;

import guichafy.webfluxbestpratices.movie.Movie;

import java.util.Arrays;
import java.util.List;

public class MovieClientIMDB implements MovieClient{

    @Override
    public List<Movie> getMovieResults() {
        return Arrays.asList(new Movie(1L, "Movie A - IMDb"));
    }
}
