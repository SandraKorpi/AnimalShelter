package sandrakorpi.animalshelterapi.Services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import sandrakorpi.animalshelterapi.Dtos.UserDto;
import sandrakorpi.animalshelterapi.Enums.Role;
import sandrakorpi.animalshelterapi.Models.User;
import sandrakorpi.animalshelterapi.Repositories.UserRepository;
import static org.mockito.ArgumentMatchers.any;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDto mockUserDto;
    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Skapa en mock av UserDto
        mockUserDto = new UserDto();
        mockUserDto.setUserId(1L);
        mockUserDto.setUserName("Sandrak");
        mockUserDto.setEmail("saandra92@hotmail.com");
        mockUserDto.setPassword("password123");
        mockUserDto.setRoles(Arrays.asList(Role.ROLE_USER));

        // Skapa en mock av User
        mockUser = new User();
        mockUser.setUserId(mockUserDto.getUserId());
        mockUser.setUserName(mockUserDto.getUserName());
        mockUser.setEmail(mockUserDto.getEmail());
        mockUser.setPassword(mockUserDto.getPassword());
        mockUser.setRoles(mockUserDto.getRoles());
    }

    @Test
    void getUserById() {
        // Mocka repository för att returnera den mockade användaren, testa metoden.
        when(userRepository.findById(mockUserDto.getUserId())).thenReturn(Optional.of(mockUser));
        UserDto result = userService.getUserById(mockUserDto.getUserId());

        // Assert
        assertNotNull(result);
        assertEquals("Sandrak", result.getUserName());
        assertEquals("saandra92@hotmail.com", result.getEmail());
    }

    @Test
    void findAllUsers() {
        //Skapar en lista av users att testa med.
        User mockUser1 = new User();
        mockUser1.setUserId(1L);
        mockUser1.setUserName("userName1");
        mockUser1.setEmail("1@hotmail.com");
        mockUser1.setPassword("Password1");
        mockUser1.setRoles(Arrays.asList(Role.ROLE_USER));

        User mockUser2 = new User();
        mockUser2.setUserId(2L);
        mockUser2.setUserName("userName2");
        mockUser2.setEmail("2@hotmail.com");
        mockUser2.setPassword("Password2");
        mockUser2.setRoles(Arrays.asList(Role.ROLE_ADMIN));

        List<User> mockList = Arrays.asList(mockUser1, mockUser2);
        when(userRepository.findAll()).thenReturn(mockList);
        //Testa metoden
        List<UserDto> result = userService.findAllUsers();

        //Kontroll av resultatet
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("userName1", result.get(0).getUserName());
        assertEquals("userName2", result.get(1).getUserName());
    }

    @Test
    void updateUser() {
        //Dto med uppdaterade värden
        UserDto updatedDto = new UserDto();
        updatedDto.setUserId(mockUserDto.getUserId());
        updatedDto.setUserName("KarinG");
        updatedDto.setEmail("Karing@hotmail.com");

        // Mocka findById metoden för att returnera mockUser
        when(userRepository.findById(mockUserDto.getUserId())).thenReturn(Optional.of(mockUser));

        // Spara den uppdaterade användaren.
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        UserDto result = userService.updateUser(mockUserDto.getUserId(), updatedDto);
        //Kontroll av resultatet.
        assertNotNull(result);
        assertEquals("KarinG", result.getUserName());
        assertEquals("Karing@hotmail.com", result.getEmail());
    }

    @Test
    void deleteUser() {

        when(userRepository.findById(mockUser.getUserId())).thenReturn(Optional.of(mockUser));

        userService.deleteUser(mockUser.getUserId());
//Kontrollera så att metoden körts.
        verify(userRepository).delete(mockUser);

        // Kontrollera att användaren är borttagen
        Optional<User> deletedUser = userRepository.findById(mockUser.getUserId());
        assertTrue(deletedUser.isEmpty());
    }

    @Test
    void convertToUser() {
        // Testa konvertering från UserDto till User
        User user = userService.convertToUser(mockUserDto);
        assertNotNull(user);
        assertEquals("Sandrak", user.getUsername());
    }

    @Test
    void convertToUserDto() {
        // Testa konvertering från User till UserDto
        User user = new User();
        user.setUserId(1L);
        user.setUserName("Sandrak");
        user.setEmail("saandra92@hotmail.com");
        user.setPassword("password123");
        user.setRoles(Arrays.asList(Role.ROLE_USER));

        UserDto userDto = userService.convertToUserDto(user);
        assertNotNull(userDto);
        assertEquals("Sandrak", userDto.getUserName());
    }

    @Test
    void getUserOrFail() {
        User mockUser = new User();
        mockUser.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserOrFail(1L);

        //Kontroll av resultatet.
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
    }
}
