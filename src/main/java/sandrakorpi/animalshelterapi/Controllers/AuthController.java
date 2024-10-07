package sandrakorpi.animalshelterapi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sandrakorpi.animalshelterapi.Dtos.AuthDto;
import sandrakorpi.animalshelterapi.Dtos.LoginResponse;
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
    public ResponseEntity<Void> register(@RequestBody AuthDto authDto) {
        User registeredUser = authService.signup(authDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody AuthDto authDto) {
        User authenticatedUser = authService.authenticate(authDto);

        String jwtToken = jwtTokenProvider.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtTokenProvider.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }

}