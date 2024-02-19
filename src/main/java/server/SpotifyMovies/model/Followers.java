package server.SpotifyMovies.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import server.SpotifyMovies.model.id.FollowersId;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Followers")
public class Followers implements Serializable {
    @EmbeddedId
    private FollowersId id;

    public Followers() {
    }

}
