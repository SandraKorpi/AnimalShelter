package sandrakorpi.animalshelterapi.Services;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sandrakorpi.animalshelterapi.Dtos.RegisterUserDto;
import sandrakorpi.animalshelterapi.Dtos.UserDto;
import sandrakorpi.animalshelterapi.Enums.Role;
import sandrakorpi.animalshelterapi.Models.User;
import sandrakorpi.animalshelterapi.Repositories.UserRepository;
import sandrakorpi.animalshelterapi.exceptions.ResourceNotFoundException;
import sandrakorpi.animalshelterapi.exceptions.UserAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(UserDto userDto) {

        if (userRepository.findByUserName(userDto.getUserName()) != null) {
            throw new UserAlreadyExistsException("Användaren existerar redan");
        }

        User user = new User();

        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        // Tilldela roller
        if (userRepository.count() == 0) {
            user.addRole(Role.ROLE_ADMIN); // Första användaren får ADMIN-roll
        } else {
            user.addRole(Role.ROLE_USER); // Andra användare får USER-roll
        }

        return userRepository.save(user);
    }

    public List<UserDto> findAllUsers() {

        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        //konvertera varje User till UserDto
        for (User user : userList) {
            userDtoList.add(convertToUserDto(user));
        }
        return userDtoList;
    }
    public UserDto updateUser(Long userId, UserDto updatedDto) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Användaren hittades inte"));

        userToUpdate.setUserName(updatedDto.getUserName());
        userToUpdate.setEmail(updatedDto.getEmail());
        userToUpdate.setRoles(updatedDto.getRoles());

        // Kolla om ett nytt lösenord har skickats med i DTO:n och kryptera det i så fall
        if (updatedDto.getPassword() != null && !updatedDto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updatedDto.getPassword());
            userToUpdate.setPassword(encodedPassword);
        }

        User savedUser = userRepository.save(userToUpdate);
        //konvertera tillbaka till Dto.
        return convertToUserDto(savedUser);
    }


    public void deleteUser(long id) {

        User userToDelete = getUserOrFail(id);
        userRepository.delete(userToDelete);
    }

    public User convertToUser(UserDto userDto) {

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        return user;
    }

    public UserDto convertToUserDto (User user)
    {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserName(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());
        return userDto;
    }


    public UserDto getUserById (long id){
        User user = getUserOrFail(id);
        return convertToUserDto(user);
    }


    public User getUserOrFail(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Det finns ingen användare med id:  " + id));
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Användaren hittades inte");
        }
        return user;
    }
}
