package sandrakorpi.animalshelterapi.Services;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import sandrakorpi.animalshelterapi.Dtos.UserDto;
import sandrakorpi.animalshelterapi.Enums.Role;
import sandrakorpi.animalshelterapi.Models.User;
import sandrakorpi.animalshelterapi.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private User mockUser;
    private UserDto mockUserDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock användare och DTO
        mockUserDto = new UserDto();
        mockUserDto.setUserId(1L);
        mockUserDto.setUserName("Sandrak");
        mockUserDto.setEmail("saandra92@hotmail.com");
        mockUserDto.setPassword("password123");  // Okrypterat lösenord
        mockUserDto.setRoles(Arrays.asList(Role.ROLE_USER));


        mockUser = new User();
        mockUser.setUserId(mockUserDto.getUserId());
        mockUser.setUserName(mockUserDto.getUserName());
        mockUser.setEmail(mockUserDto.getEmail());
        mockUser.setPassword("password123"); //okrypterat lösenord, krypteras i metoderna.
        mockUser.setRoles(mockUserDto.getRoles());
    }
    @Test
    void SaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        User userResult = userService.saveUser(mockUserDto);
        assertNotNull(userResult);
        assertEquals(mockUserDto.getUserName(), userResult.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void findAllUsers() {
        // Mocka repository
        when(userRepository.findAll()).thenReturn(Arrays.asList(mockUser));
        // Testa metoden
        List<UserDto> userDtoList = userService.findAllUsers();

        //Kontrollerar att vi får fram användaren som finns i listan.
        assertEquals(1, userDtoList.size());
        assertEquals(mockUserDto.getUserName(), userDtoList.get(0).getUserName());

    }

    @Test
    void updateUser() {
        // Mocka repository för att hitta användaren
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Mocka kryptering av det nya lösenordet
        when(passwordEncoder.encode("newPassword123")).thenReturn("encodedNewPassword123");

        // Mocka repository för att spara den uppdaterade användaren
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Skapa en UserDto med uppdaterad information
        UserDto updatedDto = new UserDto();
        updatedDto.setUserName("UpdatedName");
        updatedDto.setEmail("updatedemail@example.com");
        //Nytt okrypterat lösenord
        updatedDto.setPassword("newPassword123");
        //sätta rollen
        updatedDto.setRoles(Arrays.asList(Role.ROLE_USER));

        // Kör uppdateringsmetoden
        UserDto result = userService.updateUser(1L, updatedDto);

        // Kontrollera att uppdateringen fungerat genom att jämföra resultaten.
        assertEquals("UpdatedName", result.getUserName());
        assertEquals("updatedemail@example.com", result.getEmail());
        assertEquals("encodedNewPassword123", result.getPassword()); // Kontrollera krypterat nytt lösenord
    }



@Test
    void deleteUser() {
        when(userRepository.findById(mockUser.getUserId())).thenReturn(Optional.of(mockUser));

        userService.deleteUser(mockUser.getUserId());
//Kontrollera så att metoden körts.
        verify(userRepository).delete(mockUser);
    }

    @Test
    void convertToUser() {
        // Testar konvertering från UserDto till User
        User user = userService.convertToUser(mockUserDto);
        assertNotNull(user);
        assertEquals(mockUserDto.getUserName(), user.getUsername());
        assertEquals(mockUser.getEmail(), user.getEmail());
        assertEquals(mockUser.getPassword(), user.getPassword());
        assertEquals(mockUser.getRoles(), user.getRoles());
    }

    @Test
    void convertToUserDto() {

        //Kör metoden, använder objekten i setUp.
        UserDto userDto = userService.convertToUserDto(mockUser);
//jämför attributen.
        assertNotNull(userDto);
        assertEquals(mockUser.getUserId(), userDto.getUserId());
        assertEquals(mockUser.getUsername(), userDto.getUserName());
        assertEquals(mockUser.getEmail(), userDto.getEmail());
        assertEquals(mockUser.getRoles(), userDto.getRoles());
    }


    @Test
    void getUserOrFail() {
        //Använder mockuser från setUp-metoden.
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserOrFail(1L);

        //Kontroll av resultatet.
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
    }

    @Test
    void loadByUserName(){
        when(userRepository.findByUserName(mockUser.getUsername())).thenReturn(mockUser);
        // Anropa metoden
        UserDetails userDetails = userService.loadUserByUsername(mockUser.getUsername());
        //Kontroll så Userdetails inte är null.
        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), "Sandrak");
    }
}


