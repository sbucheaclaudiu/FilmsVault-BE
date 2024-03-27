package server.SpotifyMovies.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.login.UserDTO;
import server.SpotifyMovies.dto.playlist.CreatePlaylistDTO;
import server.SpotifyMovies.model.Role;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.dto.login.JwtAuthenticationResponse;
import server.SpotifyMovies.dto.login.LoginDTO;
import server.SpotifyMovies.dto.login.SignupDTO;
import server.SpotifyMovies.repository.UserRepoInterface;
import server.SpotifyMovies.security.AuthenticationService;
import server.SpotifyMovies.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepoInterface userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignupDTO request) {
        User user = User.builder().name(request.getName()).username(request.getUsername())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).profile_url("").activeAccount(true).build();

        user.setActiveAccount(true);
        userRepository.save(user);
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsernameReal(), user.getProfile_url(), user.getName(), user.getEmail());

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).user(userDTO).build();
    }

    @Override
    public JwtAuthenticationResponse login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!user.getActiveAccount()){
            throw new IllegalArgumentException("Invalid email or password.");
        }

        UserDTO userDTO = new UserDTO(user.getId(), user.getUsernameReal(), user.getProfile_url(),  user.getName(), user.getEmail());

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).user(userDTO).build();
    }
}
