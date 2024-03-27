package server.SpotifyMovies.security;

import server.SpotifyMovies.dto.login.JwtAuthenticationResponse;
import server.SpotifyMovies.dto.login.LoginDTO;
import server.SpotifyMovies.dto.login.SignupDTO;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignupDTO request);

    JwtAuthenticationResponse login(LoginDTO request);
}
