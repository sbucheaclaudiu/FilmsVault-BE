package server.SpotifyMovies.security;

import server.SpotifyMovies.dto.JwtAuthenticationResponse;
import server.SpotifyMovies.dto.LoginDTO;
import server.SpotifyMovies.dto.SignupDTO;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignupDTO request);

    JwtAuthenticationResponse login(LoginDTO request);
}
