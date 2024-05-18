package server.SpotifyMovies.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailsDTO {
    private Long id;
    private String username;
    private String profileUrl;
    private String name;
    private String email;
    private int noPublicPlaylists;
    private int noFollowers;
    private int noFollowing;

    public UserDetailsDTO(Long id, String username, String profileUrl, String name, String email) {
        this.id = id;
        this.username = username;
        this.profileUrl = profileUrl;
        this.name = name;
        this.email = email;
    }
}

