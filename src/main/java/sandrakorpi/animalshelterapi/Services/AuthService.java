package sandrakorpi.animalshelterapi.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sandrakorpi.animalshelterapi.Dtos.AuthDto;
import sandrakorpi.animalshelterapi.Models.User;

@Service
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            UserService userService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(AuthDto authDto) {
        User user = User.builder()
                .username(authDto.getUsername())
                .password(passwordEncoder.encode(authDto.getPassword()))
                .build();

        return userService.saveUser(user);
    }

    public User authenticate(AuthDto authDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDto.getUsername(),
                        authDto.getPassword()
                )
        );

        return userService.getUserByUsername(authDto.getUsername());
    }
}
