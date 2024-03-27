package server.SpotifyMovies.dto.login;

import lombok.*;
import server.SpotifyMovies.dto.playlist.CreatePlaylistDTO;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;

    private UserDTO user;

    private String validEmail;

    private String validUsername;

    private boolean validData;
}
