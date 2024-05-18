package server.SpotifyMovies.mapper;

import server.SpotifyMovies.dto.follow.FollowedUserDTO;
import server.SpotifyMovies.dto.login.UserDTO;
import server.SpotifyMovies.dto.login.UserDetailsDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModelToDTO implements ModelToDTOInterface{
    private final Utils utils = new Utils();

    @Override
    public PlaylistDTO playlistToDTO(Playlist playlist) throws ParseException, IOException {
        return new PlaylistDTO(playlist.getId(), playlist.getName(), playlist.getUser().getName(), utils.getImageAsBase64(playlist.getImagePath()), utils.transformDateToString(playlist.getLastTimeEdit().toString()), utils.transformDateToString(playlist.getTimeCreated().toString()), playlist.getPrivatePlaylist(), playlist.getDescription());
    }

    @Override
    public List<PlaylistDTO> playlistListToDTOList(List<Playlist> lstPlaylist) throws ParseException, IOException {
        List<PlaylistDTO> lstPlaylistDTO = new ArrayList<>();

        Collections.sort(lstPlaylist, Comparator.comparing(Playlist::getTimeCreated));

        for(Playlist playlist : lstPlaylist){
            lstPlaylistDTO.add(playlistToDTO(playlist));
        }

        return lstPlaylistDTO;
    }

    @Override
    public FollowedUserDTO followedUserToDTO(Followers followers) throws IOException {
        if(!Objects.equals(followers.getId().getFollowedUser().getProfile_url(), "")){
            return new FollowedUserDTO(followers.getId().getFollowedUser().getId(), followers.getId().getFollowedUser().getName(), utils.getImageAsBase64(followers.getId().getFollowedUser().getProfile_url()));

        }
        else{
            return new FollowedUserDTO(followers.getId().getFollowedUser().getId(), followers.getId().getFollowedUser().getName(), followers.getId().getFollowedUser().getProfile_url());
        }

    }

    @Override
    public List<FollowedUserDTO> followedListToDTOList(List<Followers> lstFollowers) throws IOException {
        List<FollowedUserDTO> lstfollowedUserDTO = new ArrayList<>();

        for(Followers followers : lstFollowers){
            lstfollowedUserDTO.add(followedUserToDTO(followers));
        }

        return lstfollowedUserDTO;
    }

    @Override
    public UserDetailsDTO userDetailsToDTO(User user) throws IOException {

        if(!Objects.equals(user.getProfile_url(), "")){
            return new UserDetailsDTO(user.getId(), user.getUsernameReal(), utils.getImageAsBase64(user.getProfile_url()), user.getName(), user.getEmail());

        }
        else{
            return new UserDetailsDTO(user.getId(), user.getUsernameReal(), user.getProfile_url(), user.getName(), user.getEmail());
        }
    }

    @Override
    public UserDTO userToDTO(User user) throws IOException {

        if(!Objects.equals(user.getProfile_url(), "")){
            return new UserDTO(user.getId(), user.getUsernameReal(), utils.getImageAsBase64(user.getProfile_url()), user.getName(), user.getEmail());

        }
        else{
            return new UserDTO(user.getId(), user.getUsernameReal(), user.getProfile_url(), user.getName(), user.getEmail());
        }
    }

    @Override
    public List<UserDTO> userListToDTOList(List<User> lstUsers) throws IOException {
        List<UserDTO> lstUsersDTO = new ArrayList<>();

        for(User user : lstUsers){
            lstUsersDTO.add(userToDTO(user));
        }

        return lstUsersDTO;
    }


}
