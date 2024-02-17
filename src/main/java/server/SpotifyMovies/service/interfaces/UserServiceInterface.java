package server.SpotifyMovies.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.model.dto.JwtAuthenticationResponse;
import server.SpotifyMovies.model.dto.SignupDTO;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    UserDetailsService userDetailsService();

    List<User> getAllUsers();

    JwtAuthenticationResponse verifySignup(SignupDTO signupDTO);

    User getUserByEmail(String email);
}
