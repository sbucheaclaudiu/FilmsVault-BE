package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.service.interfaces.UserServiceInterface;

import java.util.List;

@Controller
@RequestMapping("/spotifyMovies/resource")
@CrossOrigin
public class TestController {
    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/get/users")
    public @ResponseBody ResponseEntity<List<User>> getQuizzes() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

}
