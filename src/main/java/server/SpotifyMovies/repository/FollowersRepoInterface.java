package server.SpotifyMovies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.SpotifyMovies.model.Followers;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.model.id.FollowersId;

import java.util.List;

@Repository
public interface FollowersRepoInterface extends JpaRepository<Followers, FollowersId> {
    @Query("SELECT f FROM Followers f WHERE f.id.user.id = :userId AND f.id.user.activeAccount = true")
    List<Followers> findAllActiveByIdUserId(Long userId);
    Followers save(Followers followers);
    boolean existsById_UserAndId_FollowedUser(User user, User followedUser);

    void deleteById(FollowersId id);

    @Query("SELECT COUNT(f) FROM Followers f WHERE f.id.followedUser.id = :userId")
    int countFollowersByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(f) FROM Followers f WHERE f.id.user.id = :userId")
    int countFollowingByUserId(@Param("userId") Long userId);

}
