package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import server.SpotifyMovies.dto.MoviePostDTO;
import server.SpotifyMovies.dto.ResponseDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.service.interfaces.MovieServiceInterface;

@Controller
@RequestMapping("/moviesVault/movie")
@CrossOrigin
public class MovieController {
    @Autowired
    private MovieServiceInterface movieService;

    @PostMapping("/addMovie")
    public ResponseEntity<ResponseDTO> addMovie(@RequestBody MoviePostDTO movie) {
        try{
            ResponseDTO response = movieService.addMovieToPlaylist(movie);
            return ResponseEntity.ok(response);
        }
        catch (CustomException e){
            return ResponseEntity.ok(new ResponseDTO(false, e.getMessage()));

        }
        catch (Exception e){
            return ResponseEntity.ok(new ResponseDTO(false, "Something went wrong. Try again!"));
        }
    }
}
