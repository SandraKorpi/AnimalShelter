package sandrakorpi.animalshelterapi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sandrakorpi.animalshelterapi.Dtos.LoginDto;
import sandrakorpi.animalshelterapi.Dtos.LoginResponse;
import sandrakorpi.animalshelterapi.Dtos.RegisterUserDto;
import sandrakorpi.animalshelterapi.Models.User;
import sandrakorpi.animalshelterapi.Security.JwtTokenProvider;
import sandrakorpi.animalshelterapi.Services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @Autowired
    public AuthController(
            JwtTokenProvider jwtTokenProvider,
            AuthService authenticationService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            authService.signup(registerUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginDto) {
        try {
            User authenticatedUser = authService.authenticate(loginDto);

            String jwtToken = jwtTokenProvider.generateToken(authenticatedUser);

            LoginResponse loginResponse = LoginResponse.builder()
                    .token(jwtToken)
                    .expiresIn(jwtTokenProvider.getExpirationTime())
                    .build();

            return ResponseEntity.ok(loginResponse);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}