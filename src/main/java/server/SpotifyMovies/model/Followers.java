package server.SpotifyMovies.model;

import jakarta.persistence.*;
import lombok.Data;
import server.SpotifyMovies.model.id.FollowersId;

import java.io.Serializable;

@Entity
@Data
@Table(name = "followers")
public class Followers implements Serializable {
    @EmbeddedId
    private FollowersId followersId;


    public Followers() {
    }

    public FollowersId getFollowersId() {
        return followersId;
    }

    public void setFollowersId(FollowersId followersId) {
        this.followersId = followersId;
    }
}
