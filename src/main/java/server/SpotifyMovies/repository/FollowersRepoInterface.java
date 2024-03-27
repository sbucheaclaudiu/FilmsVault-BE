package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.model.id.FollowersId;

import java.util.List;

@Repository
public interface FollowersRepoInterface extends JpaRepository<Followers, FollowersId> {
    List<Followers> findAllById_User_Id(Long userId);
    Followers save(Followers followers);
    boolean existsById_UserAndId_FollowedUser(User user, User followedUser);
    void deleteById(FollowersId id);

}
