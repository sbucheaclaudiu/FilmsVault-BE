package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.CreatePlaylistDTO;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.service.interfaces.PlaylistServiceInterface;
import server.SpotifyMovies.service.interfaces.UserServiceInterface;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/moviesVault/playlist")
@CrossOrigin
public class PlaylistController {
    @Autowired
    private PlaylistServiceInterface playlistService;

    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/getPlaylists")
    public ResponseEntity<List<Playlist>> getPlaylists(@RequestParam Long userId) {
        try{
            List<Playlist> lstUserPlaylist = playlistService.getPlaylistByUser(userId);
            return ResponseEntity.ok(lstUserPlaylist);
        } catch (Exception exception){
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PostMapping("/createPlaylist")
    public ResponseEntity<String> createPlaylist(@RequestBody CreatePlaylistDTO playlist){
        try{
            User user = userService.getUserById(playlist.getUserId());
            playlistService.createPlaylist(playlist, user);

            return ResponseEntity.ok("Playlist created.");
        } catch (Exception exception){
            return ResponseEntity.ok("Failed to create playlist.");
        }
    }
}
