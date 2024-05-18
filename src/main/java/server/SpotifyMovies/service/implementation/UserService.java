package server.SpotifyMovies.service.implementation;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.SpotifyMovies.dto.login.UserDTO;
import server.SpotifyMovies.dto.login.UserDetailsDTO;
import server.SpotifyMovies.mapper.ModelToDTO;
import server.SpotifyMovies.mapper.ModelToDTOInterface;
import server.SpotifyMovies.model.Playlist;
import server.SpotifyMovies.model.User;
import server.SpotifyMovies.dto.login.JwtAuthenticationResponse;
import server.SpotifyMovies.dto.login.SignupDTO;
import server.SpotifyMovies.repository.FollowersRepoInterface;
import server.SpotifyMovies.repository.UserRepoInterface;
import server.SpotifyMovies.service.interfaces.UserServiceInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService implements UserServiceInterface {

    private final UserRepoInterface userRepo;

    private final FollowersRepoInterface followersRepo;

    private final ModelToDTOInterface modelToDTOMapper = new ModelToDTO();

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepo.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public JwtAuthenticationResponse verifySignup(SignupDTO signupDTO) {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setValidData(true);

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(signupDTO.getEmail());
        if(!matcher.matches()){
            response.setValidEmail("Invalid Email");
            response.setValidData(false);
        }

        Optional<User> user = userRepo.findByEmail(signupDTO.getEmail());
        if(!user.isEmpty() && user.get().getActiveAccount() == true){
            response.setValidEmail("Email already used!");
            response.setValidData(false);
        }

        user = userRepo.findByUsername(signupDTO.getUsername());
        if(!user.isEmpty() && user.get().getActiveAccount() == true){
            response.setValidUsername("Username already used!");
            response.setValidData(false);
        }

        return response;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public UserDetailsDTO getUserDTOById(Long id) throws IOException {
        UserDetailsDTO user =  modelToDTOMapper.userDetailsToDTO(userRepo.findById(id).orElse(null));

        int noPublicPlaylists = userRepo.countPublicPlaylistsByUserId(id);
        user.setNoPublicPlaylists(noPublicPlaylists);

        int noFollowers = followersRepo.countFollowersByUserId(id);
        user.setNoFollowers(noFollowers);

        int noFollowing = followersRepo.countFollowingByUserId(id);
        user.setNoFollowing(noFollowing);

        return user;
    }

    @Override
    public void updateUser(UserDTO user) throws Exception {
        Optional<User> userOptional = userRepo.findById(user.getId());

        if(userOptional.isPresent()){
            User oldUser = userOptional.get();

            String base64Image = user.getProfileUrl();

            if (!StringUtils.isEmpty(base64Image)) {
                try {
                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                    String uploadsDir = "/Users/sbucheaclaudiu/IdeaProjects/SpotifyMovies/src/main/resources/Images";

                    String filename = "image_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000) + ".jpg";

                    File file = new File(uploadsDir, filename);
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(imageBytes);
                    }

                    String savedFilePath = file.getAbsolutePath();
                    System.out.println("Imaginea a fost salvată la: " + savedFilePath);

                    User newUser = new User(oldUser.getId(), user.getName(), oldUser.getUsernameReal(), user.getEmail(), oldUser.getPassword(), savedFilePath, oldUser.getRole(), true);

                    String regex = "^(.+)@(.+)$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(newUser.getEmail());
                    if(!matcher.matches()){

                        throw new Exception("Invalid Email!");
                    }

                    Optional<User> userFind = userRepo.findByEmail(newUser.getEmail());

                    if(!userFind.isEmpty() && userFind.get().getActiveAccount() == true && userFind.get().getId() != newUser.getId()){

                        throw new Exception("Email already used!");
                    }

                    userRepo.save(newUser);

                } catch (IOException e) {
                    throw new Exception("Eroare la încărcarea imaginii: " + e.getMessage());
                }
            } else {
                User newUser = new User(oldUser.getId(), user.getName(), oldUser.getUsernameReal(), user.getEmail(), oldUser.getPassword(), "", oldUser.getRole(), true);

                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(newUser.getEmail());
                if(!matcher.matches()){

                    throw new Exception("Invalid Email!");
                }

                Optional<User> userFind = userRepo.findByEmail(newUser.getEmail());

                if(!userFind.isEmpty() && userFind.get().getActiveAccount() == true && userFind.get().getId() != newUser.getId()){

                    throw new Exception("Email already used!");
                }

                userRepo.save(newUser);
            }

        }
        else{
            throw new Exception("User with id: " + user.getId() + " not found.");
        }
    }

    @Override
    public void deleteUser(Long userId) throws Exception {
        //inactive user

        Optional<User> userOptional = userRepo.findById(userId);

        if(userOptional.isPresent()) {
            User user = userOptional.get();

            String newEmail = "email_" + user.getEmail() + "_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000) + ".jpg";
            String newUsername = "username_" + user.getUsernameReal() + "_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000) + ".jpg";

            user.setEmail(newEmail);
            user.setUsername(newUsername);
            user.setActiveAccount(false);

            userRepo.save(user);
        }
        else{
            throw new Exception("User with id: " + userId + " not found.");
        }
    }

    @Override
    public List<UserDTO> searchUsersByUsername(String username) throws IOException {
        List<User> lstUsers = userRepo.findByUsernameContainingAndActiveAccountIsTrue(username);

        return modelToDTOMapper.userListToDTOList(lstUsers);
    }


}
