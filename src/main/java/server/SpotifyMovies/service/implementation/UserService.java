package server.SpotifyMovies.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.model.dto.JwtAuthenticationResponse;
import server.SpotifyMovies.model.dto.SignupDTO;
import server.SpotifyMovies.repository.UserRepo;
import server.SpotifyMovies.service.interfaces.UserServiceInterface;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService implements UserServiceInterface {

    private final UserRepo userRepo;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepo.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public JwtAuthenticationResponse verifySignup(SignupDTO signupDTO) {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setValidData(true);

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(signupDTO.getEmail());
        if(!matcher.matches()){
            response.setValidEmail("Invalid Email");
            response.setValidData(false);
        }

        Optional<User> user = userRepo.findByEmail(signupDTO.getEmail());
        if(!user.isEmpty()){
            response.setValidEmail("Email already used!");
            response.setValidData(false);
        }

        user = userRepo.findByUsername(signupDTO.getUsername());
        if(!user.isEmpty()){
            response.setValidUsername("Username already used!");
            response.setValidData(false);
        }

        return response;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }


}
