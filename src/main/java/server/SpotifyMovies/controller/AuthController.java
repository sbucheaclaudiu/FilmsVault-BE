package server.SpotifyMovies.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.login.UserDTO;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.dto.login.JwtAuthenticationResponse;
import server.SpotifyMovies.dto.login.LoginDTO;
import server.SpotifyMovies.dto.login.SignupDTO;
import server.SpotifyMovies.security.AuthenticationService;
import server.SpotifyMovies.service.interfaces.PlaylistServiceInterface;
import server.SpotifyMovies.service.interfaces.UserServiceInterface;


@RestController
@RequestMapping("/moviesVault/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    private final UserServiceInterface userService;

    private final PlaylistServiceInterface playlistService;

    private void createDefaultPlaylists(String email){
        User user = userService.getUserByEmail(email);

        playlistService.createDefaultPlaylists(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignupDTO signupDTO) {
        JwtAuthenticationResponse jwtResponse = userService.verifySignup(signupDTO);

        if(!jwtResponse.isValidData()){
            return ResponseEntity.ok(jwtResponse);
        }
        else{
            JwtAuthenticationResponse jwt = authenticationService.signup(signupDTO);
            jwtResponse.setToken(jwt.getToken());

            createDefaultPlaylists(signupDTO.getEmail());

            return ResponseEntity.ok(jwtResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginDTO loginDTO) {
        try{
            JwtAuthenticationResponse jwt = authenticationService.login(loginDTO);
            jwt.setValidData(true);

            return ResponseEntity.ok(jwt);
        }
        catch (Exception e){
            JwtAuthenticationResponse jwt = new JwtAuthenticationResponse();
            jwt.setValidData(false);

            return ResponseEntity.ok(jwt);
        }
    }
}