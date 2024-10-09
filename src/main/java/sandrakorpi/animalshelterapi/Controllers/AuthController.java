package sandrakorpi.animalshelterapi.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user by providing username, email, and password. If registration is successful, a `201 Created` response is returned. Otherwise, a `400 Bad Request` is returned.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully registered"),
                    @ApiResponse(responseCode = "400", description = "Bad request or user already exists")
            }
    )
    public ResponseEntity<Void> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            authService.signup(registerUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user with username and password. On success, returns a JWT token and expiration time. On failure, returns a `401 Unauthorized` response.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully authenticated and JWT token returned",
                            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
            }
    )
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