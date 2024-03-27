package server.SpotifyMovies.service.interfaces;

import server.SpotifyMovies.dto.follow.FollowedUserDTO;
import server.SpotifyMovies.dto.follow.PostFollowersDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.model.Followers;

import java.util.List;

public interface FollowersServiceInterface {
    List<FollowedUserDTO> getFollowersByUser(Long userId);

    Followers addFollowUser(PostFollowersDTO followUserDTO) throws CustomException;
    Followers deleteFollowUser(PostFollowersDTO followUserDTO) throws CustomException;
}
