package server.SpotifyMovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.SpotifyMovies.dto.movie.MovieShortDTO;
import server.SpotifyMovies.dto.person.PersonShortDTO;
import server.SpotifyMovies.service.interfaces.MovieTMDBServiceInterface;
import server.SpotifyMovies.service.interfaces.PersonTMDBServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller

@RequestMapping("/moviesVault/getRandomMovies")
@CrossOrigin
public class RandomMoviesController {
    @Autowired
    private MovieTMDBServiceInterface movieTMDBService;

    @Autowired
    private PersonTMDBServiceInterface personTMDBService;

    private Random random = new Random();

    @GetMapping("/searchMulti")
    public ResponseEntity<List<MovieShortDTO>> searchMulti(@RequestParam String name) {
        try {
            List<MovieShortDTO> lstPopularMovies = movieTMDBService.searchMultiByName(name);

            return ResponseEntity.ok(lstPopularMovies.subList(0, Math.min(lstPopularMovies.size(), 12)));
        }
        catch (Exception exception){
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/popularMovies")
    public ResponseEntity<List<MovieShortDTO>> popularMovies() {
        try {
            int noPage = random.nextInt(5) + 1;
            List<MovieShortDTO> lstPopularMovies = movieTMDBService.getMovies("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=" + noPage);

            return ResponseEntity.ok(lstPopularMovies);
        }
        catch (Exception exception){
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/trendingAll")
    public ResponseEntity<List<MovieShortDTO>> trendingAll() {
        try {
            List<MovieShortDTO> lstPopularMovies = movieTMDBService.getMovies("https://api.themoviedb.org/3/trending/all/day?language=en-US");

            return ResponseEntity.ok(lstPopularMovies);
        }
        catch (Exception exception) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/trendingMovies")
    public ResponseEntity<List<MovieShortDTO>> trendingMovies() {
        try {
            List<MovieShortDTO> lstTrendingMovies = movieTMDBService.getMovies("https://api.themoviedb.org/3/trending/movie/day?language=en-US");

            return ResponseEntity.ok(lstTrendingMovies);
        }
        catch (Exception exception) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/trendingTvShows")
    public ResponseEntity<List<MovieShortDTO>> trendingTvShows() {
        try {
            List<MovieShortDTO> trendingTvShows = movieTMDBService.getMovies("https://api.themoviedb.org/3/trending/tv/day?language=en-US");


            return ResponseEntity.ok(trendingTvShows);
        }
        catch (Exception exception) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MovieShortDTO>> upcoming() {
        try {
            int noPage = random.nextInt(5) + 1;
            List<MovieShortDTO> trendingTvShows = movieTMDBService.getMovies("https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=" + noPage);

            return ResponseEntity.ok(trendingTvShows);
        }
        catch (Exception exception) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/topRatedMovies")
    public ResponseEntity<List<MovieShortDTO>> topRatedMovies() {
        try {
            int noPage = random.nextInt(5) + 1;
            List<MovieShortDTO> trendingTvShows = movieTMDBService.getMovies("https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=" + noPage);

            return ResponseEntity.ok(trendingTvShows);
        }
        catch (Exception exception) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/topRatedTvShows")
    public ResponseEntity<List<MovieShortDTO>> topRatedTvShows() {
        try {
            int noPage = random.nextInt(5) + 1;

            List<MovieShortDTO> trendingTvShows = movieTMDBService.getMovies("https://api.themoviedb.org/3/tv/top_rated?language=en-US&page=" + noPage);

            return ResponseEntity.ok(trendingTvShows);
        }
        catch (Exception exception) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/popularPersons")
    public ResponseEntity<List<PersonShortDTO>> popularPersons() {
        try {
            int noPage = random.nextInt(3) + 1;

            List<PersonShortDTO> lstPersonShort = personTMDBService.getPopularPersons("https://api.themoviedb.org/3/person/popular?language=en-US&page=" + noPage);

            return ResponseEntity.ok(lstPersonShort);
        }
        catch (Exception exception) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
}
