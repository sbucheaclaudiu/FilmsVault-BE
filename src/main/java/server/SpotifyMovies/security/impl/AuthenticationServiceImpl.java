package server.SpotifyMovies.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.UserDTO;
import server.SpotifyMovies.model.Role;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.dto.JwtAuthenticationResponse;
import server.SpotifyMovies.dto.LoginDTO;
import server.SpotifyMovies.dto.SignupDTO;
import server.SpotifyMovies.repository.UserRepo;
import server.SpotifyMovies.security.AuthenticationService;
import server.SpotifyMovies.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignupDTO request) {
        var user = User.builder().name(request.getName()).username(request.getUsername())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();

        userRepository.save(user);
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsernameReal(), user.getProfile_url());

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).user(userDTO).build();
    }

    @Override
    public JwtAuthenticationResponse login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        UserDTO userDTO = new UserDTO(user.getId(), user.getUsernameReal(), user.getProfile_url());

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).user(userDTO).build();
    }
}
