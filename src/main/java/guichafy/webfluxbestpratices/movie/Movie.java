package guichafy.webfluxbestpratices.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("movies")
public class Movie {

    private Long id;

    private String name;

}
