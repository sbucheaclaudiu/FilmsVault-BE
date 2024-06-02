package server.SpotifyMovies.service.implementation;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.playlist.CreatePlaylistDTO;
import server.SpotifyMovies.dto.playlist.PlaylistDTO;
import server.SpotifyMovies.dto.playlist.UpdatePlaylistDTO;
import server.SpotifyMovies.exceptions.CustomException;
import server.SpotifyMovies.mapper.ModelToDTO;
import server.SpotifyMovies.mapper.ModelToDTOInterface;
import server.SpotifyMovies.mapper.Utils;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.repository.PlaylistRepoInterface;
import server.SpotifyMovies.service.interfaces.PlaylistServiceInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService implements PlaylistServiceInterface {
    @Autowired
    private PlaylistRepoInterface playlistRepo;

    private final Utils utils = new Utils();

    private final ModelToDTOInterface modelToDTOMapper = new ModelToDTO();

    @Override
    public List<PlaylistDTO> getPlaylistsByUser(Long userId) throws ParseException, IOException {
        return modelToDTOMapper.playlistListToDTOList(playlistRepo.getPlaylistByUserId(userId));
    }

    @Override
    public List<PlaylistDTO> getPlaylistsByUserPublic(Long userId) throws ParseException, IOException {
        return modelToDTOMapper.playlistListToDTOList(playlistRepo.getPlaylistByUserIdAndPrivatePlaylist(userId, false));
    }

    @Override
    public List<PlaylistDTO> getRandomPlaylists(Long userId) throws ParseException, IOException {
        List<PlaylistDTO> lstPlaylists =  modelToDTOMapper.playlistListToDTOList(playlistRepo.findFilteredPlaylistsByUserId(userId));

        Collections.shuffle(lstPlaylists);

        return lstPlaylists.subList(0, Math.min(lstPlaylists.size(), 8));
    }

    @Override
    public List<PlaylistDTO> getPlaylistByName(String name) throws ParseException, IOException {
        List<PlaylistDTO> lstPlaylists = modelToDTOMapper.playlistListToDTOList(playlistRepo.findPublicPlaylistsByNameContaining(name));

        return lstPlaylists.subList(0, Math.min(lstPlaylists.size(), 8));
    }

    @Override
    public PlaylistDTO getPlaylistById(Long id) throws CustomException, ParseException, IOException {
        Playlist playlist;
        Optional<Playlist> optionalPlaylist = playlistRepo.findById(id);
        if (optionalPlaylist.isPresent()) {
            playlist = optionalPlaylist.get();

        } else {
            throw new CustomException("Playlist not found");
        }

        return modelToDTOMapper.playlistToDTO(playlist);
    }

    @Override
    public void createDefaultPlaylists(User user) {
        Playlist watched = new Playlist("Watched", "Watched Films: My cinematic journey in a nutshell.", LocalDateTime.now(), LocalDateTime.now(), false, user, "/Users/sbucheaclaudiu/IdeaProjects/SpotifyMovies/src/main/resources/Images/Watched.jpg");
        Playlist watchlist = new Playlist("Watchlist", "Film Wishlist: A compilation of movies I aspire to watch, my cinematic to-do list.", LocalDateTime.now(), LocalDateTime.now(), false, user, "/Users/sbucheaclaudiu/IdeaProjects/SpotifyMovies/src/main/resources/Images/Watchlist.jpg");

        playlistRepo.save(watched);
        playlistRepo.save(watchlist);
    }

    @Override
    public void createPlaylist(CreatePlaylistDTO playlistDTO, User user) throws Exception {
        String base64Image = playlistDTO.getImageBase64();

        if (!StringUtils.isEmpty(base64Image)) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                String uploadsDir = "/Users/sbucheaclaudiu/IdeaProjects/SpotifyMovies/src/main/resources/Images";

                String filename = "image_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000) + ".jpg";

                // Creează fișierul și salvează imaginea
                File file = new File(uploadsDir, filename);
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(imageBytes);
                }

                String savedFilePath = file.getAbsolutePath();

                Playlist playlist = new Playlist(playlistDTO.getName(), playlistDTO.getDescription(), LocalDateTime.now(), LocalDateTime.now(), playlistDTO.isPrivatePlaylist(), user, savedFilePath);

                playlistRepo.save(playlist);

            } catch (IOException e) {
                throw new Exception("Eroare la încărcarea imaginii: " + e.getMessage());
            }
        } else {
            throw new Exception("Nu a fost selectată nicio imagine pentru încărcare");
        }

    }

    @Override
    public void updatePlaylist(UpdatePlaylistDTO updatePlaylistDTO, User user) throws Exception {

        String base64Image = updatePlaylistDTO.getImagePath();

        if (!StringUtils.isEmpty(base64Image)) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                String uploadsDir = "/Users/sbucheaclaudiu/IdeaProjects/SpotifyMovies/src/main/resources/Images";

                String filename = "image_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000) + ".jpg";

                // Creează fișierul și salvează imaginea
                File file = new File(uploadsDir, filename);
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(imageBytes);
                }

                String savedFilePath = file.getAbsolutePath();

                Optional<Playlist> playlistOptional = playlistRepo.findById(updatePlaylistDTO.getPlaylistId());

                if (playlistOptional.isPresent()) {
                    Playlist playlistFound = playlistOptional.get();
                    Playlist playlist = new Playlist(updatePlaylistDTO.getPlaylistId(), updatePlaylistDTO.getName(), updatePlaylistDTO.getDescription(), playlistFound.getLastTimeEdit(), playlistFound.getTimeCreated(), updatePlaylistDTO.isPrivatePlaylist(), user, savedFilePath);
                    playlistRepo.save(playlist);
                }
                else{
                    throw new Exception("Playlist not found.");
                }

            } catch (IOException e) {
                throw new Exception("Eroare la încărcarea imaginii: " + e.getMessage());
            }
        } else {
            throw new Exception("Nu a fost selectată nicio imagine pentru încărcare");
        }
    }

    @Override
    public void deletePlaylist(Long playlistId) {

        playlistRepo.deleteById(playlistId);
    }
}
