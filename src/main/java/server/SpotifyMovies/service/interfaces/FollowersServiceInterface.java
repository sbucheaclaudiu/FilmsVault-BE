package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.model.Followers;

import java.util.List;

public interface FollowersServiceInterface {
    List<Followers> getAllFollowers();
}
