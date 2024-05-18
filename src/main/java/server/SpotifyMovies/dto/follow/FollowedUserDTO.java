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
    private String name;
    private String profile_url;
    private String lastWatchedName;
    private Long lastWatchedTmdbId;
    private String lastWatchedType;
    private String lastWatchlistName;
    private Long lastWatchlistTmdbId;
    private String lastWatchlistType;

    public FollowedUserDTO(Long id, String name, String profile_url) {
        this.id = id;
        this.name = name;
        this.profile_url = profile_url;
    }
}
