package server.SpotifyMovies.dto;

import lombok.*;
import server.SpotifyMovies.model.User;

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
