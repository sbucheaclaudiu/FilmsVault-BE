package server.SpotifyMovies.security;

import server.SpotifyMovies.model.dto.JwtAuthenticationResponse;
import server.SpotifyMovies.model.dto.LoginDTO;
import server.SpotifyMovies.model.dto.SignupDTO;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignupDTO request);

    JwtAuthenticationResponse login(LoginDTO request);
}
