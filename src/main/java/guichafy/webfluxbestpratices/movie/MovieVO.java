package guichafy.webfluxbestpratices.movie;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class MovieVO extends RepresentationModel<MovieVO> {


    public MovieVO(String name) {
        this.name = name;
    }

    private String name;
}
