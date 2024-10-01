package sandrakorpi.animalshelterapi.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sandrakorpi.animalshelterapi.Dtos.RegisterUserDto;
import sandrakorpi.animalshelterapi.Dtos.UserDto;
import sandrakorpi.animalshelterapi.Models.User;
import sandrakorpi.animalshelterapi.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterUserDto registerUserDto) {
        // Kontrollera om användaren redan är reggad.
        if (userRepository.existsByEmail(registerUserDto.getEmail())) {
          throw new UserAlreadyExistsException("Det finns redan ett konto med denna email.");
        }

        User user = new User();
        user.setUserName(registerUserDto.getUserName());
        user.setEmail(registerUserDto.getEmail());
        //behöver passwordencoder i securityconfig.
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        return userRepository.save(user);
    }
    public UserDto getUserById(long id) {

        return convertToUserDto(getUserOrFail(id));
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
    public User updateUser(long id, UserDto userDto) {

        User existingUser = getUserOrFail(id);
        // Konvertera UserDto till User och uppdatera
        User updatedUser = convertToUser(userDto);

        // Behåll ID och uppdatera den existerande användaren
        updatedUser.setUserId(existingUser.getUserId());

        return userRepository.save(updatedUser);
    }
    public void deleteUser(long id) {

        User userToDelete = getUserOrFail(id);
        userRepository.delete(userToDelete);
    }

    public User convertToUser(UserDto userDto) {

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(userDto.getRoles());
        return user;
    }

    public UserDto convertToUserDto (User user)
    {
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(passwordEncoder.encode(user.getPassword()));
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    public User getUserOrFail(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Det finns ingen användare med id:  " + id));
    }
}
