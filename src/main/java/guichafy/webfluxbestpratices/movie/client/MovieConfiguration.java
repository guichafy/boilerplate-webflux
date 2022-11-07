package guichafy.webfluxbestpratices.movie.client;


import guichafy.webfluxbestpratices.movie.client.MovieClient;
import guichafy.webfluxbestpratices.movie.client.MovieClientIMDB;
import guichafy.webfluxbestpratices.movie.client.MovieClientOther;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MovieConfiguration {

    @Bean
    @Primary
    public MovieClient movieClientIMDB(){
        return new MovieClientIMDB();
    }

    @Bean
    public MovieClient movieClientOther(){
        return new MovieClientOther();
    }
}
