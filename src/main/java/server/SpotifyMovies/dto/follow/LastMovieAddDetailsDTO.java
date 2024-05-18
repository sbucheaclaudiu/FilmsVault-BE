package server.SpotifyMovies.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LastMovieAddDetailsDTO {
    private String name;
    private Long tmdbId;
    private String type;
}
