package server.SpotifyMovies.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.model.dto.JwtAuthenticationResponse;
import server.SpotifyMovies.model.dto.LoginDTO;
import server.SpotifyMovies.model.dto.SignupDTO;
import server.SpotifyMovies.security.AuthenticationService;
import server.SpotifyMovies.service.implementation.UserService;
import server.SpotifyMovies.service.interfaces.UserServiceInterface;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/spotifyMovies/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    private final UserServiceInterface userService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignupDTO signupDTO) {
        JwtAuthenticationResponse jwtResponse = userService.verifySignup(signupDTO);

        if(!jwtResponse.isValidData()){
            return ResponseEntity.ok(jwtResponse);
        }
        else{
            JwtAuthenticationResponse jwt = authenticationService.signup(signupDTO);
            jwtResponse.setToken(jwt.getToken());
            return ResponseEntity.ok(jwtResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginDTO loginDTO) {
        try{
            JwtAuthenticationResponse jwt = authenticationService.login(loginDTO);
            jwt.setValidData(true);

            User user = userService.getUserByEmail(loginDTO.getEmail());

            jwt.setUsername(user.getUsernameReal());
            jwt.setProfile_url(user.getProfile_url());

            return ResponseEntity.ok(jwt);
        }
        catch (Exception e){
            JwtAuthenticationResponse jwt = new JwtAuthenticationResponse();
            jwt.setValidData(false);

            return ResponseEntity.ok(jwt);
        }
    }
}