package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.movie.MovieDeleteDTO;
import server.SpotifyMovies.dto.movie.MoviePostDTO;
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
            System.out.println(e.getMessage());
            return ResponseEntity.ok(new ResponseDTO(false, e.getMessage()));

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.ok(new ResponseDTO(false, "Something went wrong. Try again!"));
        }
    }

    @PostMapping("/removeMovie")
    public ResponseEntity<Boolean> removeMovie(@RequestBody MovieDeleteDTO movie) {
        try{
            movieService.removeMovieFromPlaylist(movie);
            return ResponseEntity.ok(true);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.ok(false);

        }
    }
}
