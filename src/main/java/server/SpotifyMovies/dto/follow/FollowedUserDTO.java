package server.SpotifyMovies.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowedUserDTO {
    private Long id;
    private String username;
    private String profile_url;
    private String lastMovieWatched;
    private String lastMovieWatchlist;

    public FollowedUserDTO(Long id, String username, String profile_url) {
        this.id = id;
        this.username = username;
        this.profile_url = profile_url;
    }
}
