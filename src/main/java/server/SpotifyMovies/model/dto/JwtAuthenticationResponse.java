package server.SpotifyMovies.model.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private String validEmail;
    private String validUsername;
    private boolean validData;
    private String username;
    private String profile_url;
}
