package sandrakorpi.animalshelterapi.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sandrakorpi.animalshelterapi.Dtos.LoginDto;
import sandrakorpi.animalshelterapi.Dtos.RegisterUserDto;
import sandrakorpi.animalshelterapi.Dtos.UserDto;
import sandrakorpi.animalshelterapi.Models.User;
import sandrakorpi.animalshelterapi.exceptions.UserAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void testSignupValidUser() {
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "email", "password");
        when(userService.saveUser(any(UserDto.class))).thenReturn(new User());

        authService.signup(registerUserDto);

        verify(userService, times(1)).saveUser(any(UserDto.class));
    }

    @Test
    void testSignupUserAlreadyExists() {
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "email", "password");

        doThrow(new UserAlreadyExistsException("Användaren existerar redan"))
                .when(userService).saveUser(any(UserDto.class));

        assertThrows(UserAlreadyExistsException.class, () -> authService.signup(registerUserDto));

        verify(userService, times(1)).saveUser(any(UserDto.class));
    }

    @Test
    void testAuthenticateValidUser() {
        LoginDto loginDto = new LoginDto("username", "password");
        User mockUser = new User();
        when(userService.loadUserByUsername(loginDto.getUserName())).thenReturn(mockUser);

        User authenticatedUser = authService.authenticate(loginDto);

        assertNotNull(authenticatedUser);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService, times(1)).loadUserByUsername(loginDto.getUserName());
    }

    @Test
    void testAuthenticateInvalidUser() {
        LoginDto loginDto = new LoginDto("username", "password");

        when(userService.loadUserByUsername(loginDto.getUserName())).thenThrow(new UsernameNotFoundException("Användaren hittades inte"));

        assertThrows(UsernameNotFoundException.class, () -> authService.authenticate(loginDto));
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService, times(1)).loadUserByUsername(loginDto.getUserName());
    }

}
