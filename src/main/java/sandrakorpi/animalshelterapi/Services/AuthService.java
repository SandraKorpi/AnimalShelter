package sandrakorpi.animalshelterapi.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sandrakorpi.animalshelterapi.Dtos.LoginDto;
import sandrakorpi.animalshelterapi.Dtos.RegisterUserDto;
import sandrakorpi.animalshelterapi.Dtos.UserDto;
import sandrakorpi.animalshelterapi.Models.User;
import sandrakorpi.animalshelterapi.exceptions.UserAlreadyExistsException;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            UserService userService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public void signup(RegisterUserDto registerUserDto) {
        UserDto user = new UserDto();
        user.setUserName(registerUserDto.getUserName());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(registerUserDto.getPassword());

        userService.saveUser(user);
    }

    public User authenticate(LoginDto loginDto) {

        User user = userService.loadUserByUsername(loginDto.getUserName());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUserName(),
                        loginDto.getPassword()
                )
        );

        return user;
    }
}
