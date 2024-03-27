package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import server.SpotifyMovies.dto.movie.MovieShortDTO;
import server.SpotifyMovies.dto.person.PersonDetailsDTO;
import server.SpotifyMovies.dto.person.PersonCastDTO;
import server.SpotifyMovies.service.interfaces.PersonTMDBServiceInterface;

import java.util.List;

@Controller
@RequestMapping("/moviesVault/details")
@CrossOrigin
public class PersonTMDBController {
    @Autowired
    private PersonTMDBServiceInterface personTMDBService;

    @GetMapping("/getPersonDetails")
    public ResponseEntity<PersonDetailsDTO> getPersonDetails(@RequestParam String id){
        try{
            PersonDetailsDTO personDetailsDTO = personTMDBService.getPersonDetails(id);

            return ResponseEntity.ok(personDetailsDTO);
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/getCast")
    public ResponseEntity<List<PersonCastDTO>> getCast(@RequestParam String id, @RequestParam String type){
        try{
            List<PersonCastDTO> movieDetailsDTO = personTMDBService.getCast(id, type);

            return ResponseEntity.ok(movieDetailsDTO);
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }


    @GetMapping("/getMovieCredits")
    public ResponseEntity<List<MovieShortDTO>> getMovieCredits(@RequestParam String id){
        try{
            List<MovieShortDTO> lstMovieShort = personTMDBService.getMovieCredits(id);

            return ResponseEntity.ok(lstMovieShort);
        } catch (Exception exception){
            return ResponseEntity.ok(null);
        }
    }

}
