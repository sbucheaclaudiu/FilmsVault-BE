package server.SpotifyMovies.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.dto.JwtAuthenticationResponse;
import server.SpotifyMovies.dto.SignupDTO;

import java.util.List;

public interface UserServiceInterface {
    UserDetailsService userDetailsService();

    List<User> getAllUsers();

    JwtAuthenticationResponse verifySignup(SignupDTO signupDTO);

    User getUserByEmail(String email);

    User getUserById(Long id);
}
