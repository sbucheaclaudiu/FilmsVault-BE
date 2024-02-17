package server.SpotifyMovies.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.model.ListDetails;
import server.SpotifyMovies.repository.ListDetailsRepoInterface;
import server.SpotifyMovies.service.interfaces.ListDetailsServiceInterface;

import java.util.List;

@Service
public class ListDetailsService implements ListDetailsServiceInterface {
    @Autowired
    private ListDetailsRepoInterface listDetailsRepoInterface;

    @Override
    public List<ListDetails> getAllListDetails() {
        return listDetailsRepoInterface.findAll();
    }
}
