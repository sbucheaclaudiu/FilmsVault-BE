package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.login.UserDTO;
import server.SpotifyMovies.dto.login.UserDetailsDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.service.interfaces.UserServiceInterface;

import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/moviesVault/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserServiceInterface userService;


    @PostMapping("/updateAccount")
    public ResponseEntity<String> updateAccount(@RequestBody UserDTO user) {
        try {
            userService.updateUser(user);

            return ResponseEntity.ok("Account updated.");
        } catch (Exception e) {
            if (Objects.equals(e.getMessage(), "Invalid Email!")) {
                return ResponseEntity.ok("Invalid Email!");
            } else if (Objects.equals(e.getMessage(), "Email already used!")) {
                return ResponseEntity.ok("Email already used!");
            }

            System.out.println(e.getMessage());
            return ResponseEntity.ok("Failed to update account!");
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserDetailsDTO> getUserById(@RequestParam Long userId) {
        try {
            UserDetailsDTO user = userService.getUserDTOById(userId);
            return ResponseEntity.ok(user);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/searchUsersByUsername")
    public ResponseEntity<List<UserDTO>> searchUsersByUsername(@RequestParam String username) {
        try {
            List<UserDTO> users = userService.searchUsersByUsername(username);
            return ResponseEntity.ok(users);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/deleteUser")
    public ResponseEntity<Boolean> deleteuser(@RequestParam Long userId) {
        try {
            userService.deleteUser(userId);

            return ResponseEntity.ok(true);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(false);
        }
    }
}

