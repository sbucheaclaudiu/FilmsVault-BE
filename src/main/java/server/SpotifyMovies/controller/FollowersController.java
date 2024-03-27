package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.follow.FollowedUserDTO;
import server.SpotifyMovies.dto.follow.PostFollowersDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.service.interfaces.FollowersServiceInterface;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/moviesVault/followers")
@CrossOrigin
public class FollowersController {
    @Autowired
    private FollowersServiceInterface followersService;

    @GetMapping("/getFollowersByUser")
    public ResponseEntity<List<FollowedUserDTO>> getFollowersByUser(@RequestParam Long userId) {
        try{
            List<FollowedUserDTO> lstFollowers = followersService.getFollowersByUser(userId);
            return ResponseEntity.ok(lstFollowers);
        } catch (Exception exception){
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PostMapping("/addFollowUser")
    public ResponseEntity<String> addfollowUser(@RequestBody PostFollowersDTO followUser){
        try{
            followersService.addFollowUser(followUser);

            return ResponseEntity.ok("Start following.");
        }
        catch (CustomException exception){
            return ResponseEntity.ok(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.ok("Failed to follow.");
        }
    }

    @DeleteMapping("/deleteFollowUser")
    public ResponseEntity<String> deletefollowUser(@RequestBody PostFollowersDTO followUser){
        try{
            followersService.deleteFollowUser(followUser);

            return ResponseEntity.ok("Follower deleted successfully.");
        }
        catch (CustomException exception){
            return ResponseEntity.ok(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.ok("Failed to delete.");
        }
    }
}
