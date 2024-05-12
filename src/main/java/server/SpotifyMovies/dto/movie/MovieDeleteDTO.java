package server.SpotifyMovies.dto.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDeleteDTO {
    private Long movieId;
    private Long playlistId;
}
