package server.SpotifyMovies.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.repository.FollowersRepoInterface;
import server.SpotifyMovies.service.interfaces.FollowersServiceInterface;

import java.util.List;

@Service
public class FollowersService implements FollowersServiceInterface {
    @Autowired
    private FollowersRepoInterface followersRepoInterface;

    @Override
    public List<Followers> getAllFollowers() {
        return followersRepoInterface.findAll();
    }
}
