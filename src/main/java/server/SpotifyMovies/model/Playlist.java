package server.SpotifyMovies.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "Playlists")
@AllArgsConstructor
@Getter
@Setter
public class Playlist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "playlist")
    @JsonIgnore
    private List<PlaylistMovie> playlistMovies;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastTimeEdit;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timeCreated;

    @Column
    private boolean privatePlaylist;

    @Column
    private String imagePath;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Playlist(){}

    public Playlist(String name, String description, LocalDateTime lastTimeEdit, LocalDateTime timeCreated, boolean privatePlaylist, User user, String imagePath) {
        this.name = name;
        this.description = description;
        this.lastTimeEdit = lastTimeEdit;
        this.privatePlaylist = privatePlaylist;
        this.timeCreated = timeCreated;
        this.user = user;
        this.imagePath = imagePath;
    }

    public Playlist(Long id, String name, String description, LocalDateTime lastTimeEdit, LocalDateTime timeCreated, boolean privatePlaylist, User user, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lastTimeEdit = lastTimeEdit;
        this.timeCreated = timeCreated;
        this.privatePlaylist = privatePlaylist;
        this.user = user;
        this.imagePath = imagePath;
    }

    public boolean getPrivatePlaylist(){
        return this.privatePlaylist;
    }
}
