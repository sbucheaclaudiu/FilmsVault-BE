package server.SpotifyMovies.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import server.SpotifyMovies.dto.login.UserDTO;
import server.SpotifyMovies.dto.login.UserDetailsDTO;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.dto.login.JwtAuthenticationResponse;
import server.SpotifyMovies.dto.login.SignupDTO;

import java.io.IOException;
import java.util.List;

public interface UserServiceInterface {
    UserDetailsService userDetailsService();

    List<User> getAllUsers();

    JwtAuthenticationResponse verifySignup(SignupDTO signupDTO);

    User getUserByEmail(String email);

    User getUserById(Long id);

    UserDetailsDTO getUserDTOById(Long id) throws IOException;

    void updateUser(UserDTO user) throws Exception;

    void deleteUser(Long userId) throws Exception;

    List<UserDTO> searchUsersByUsername(String username) throws IOException;
}
