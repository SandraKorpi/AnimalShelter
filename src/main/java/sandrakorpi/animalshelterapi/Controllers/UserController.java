package sandrakorpi.animalshelterapi.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sandrakorpi.animalshelterapi.Dtos.RegisterUserDto;
import sandrakorpi.animalshelterapi.Dtos.UserDto;
import sandrakorpi.animalshelterapi.Models.User;
import sandrakorpi.animalshelterapi.Services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
//Ska endast komma åt om du är admin, behöver justeras i securityconfig.
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        List<UserDto> userList = userService.findAllUsers();
return ResponseEntity.ok(userList);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteUser (@PathVariable long id)
    {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping({"/{id}}"})
    public ResponseEntity<UserDto> updateUser (@PathVariable long id, UserDto userDto)
    {
        User updatedUser = userService.updateUser(id, userDto);
        UserDto updatedUserDto = userService.convertToUserDto(updatedUser); // Konvertera tillbaka till UserDto om det behövs
        return ResponseEntity.ok(updatedUserDto);

    }

    @GetMapping({"/{id}"})
    public ResponseEntity<UserDto> getUserById (@PathVariable long id)
    {
UserDto searchUser = userService.getUserById(id);
        return ResponseEntity.ok(searchUser);
    }
}



