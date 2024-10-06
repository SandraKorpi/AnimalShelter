package sandrakorpi.animalshelterapi.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sandrakorpi.animalshelterapi.Dtos.RegisterUserDto;
import sandrakorpi.animalshelterapi.Dtos.UserDto;
import sandrakorpi.animalshelterapi.Models.User;
import sandrakorpi.animalshelterapi.Services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/users")
//Ska endast komma åt om du är admin, behöver justeras i securityconfig.
@PreAuthorize("hasRole('ROLE_ADMIN')")
//För swagger
@Tag(name = "USERS", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    //Operations för swagger.
    @Operation(summary = "Get all Users", description = "Get a list of all users")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        List<UserDto> userList = userService.findAllUsers();
return ResponseEntity.ok(userList);
    }

    @DeleteMapping({"/{id}"})
    @Operation(summary = "Delete user", description = "Delete a user by its id.")
    public ResponseEntity<Void> deleteUser (@PathVariable long id)
    {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping({"/{id}"})
    @Operation(summary = "Update user", description = "Update a user by its id.")
    public ResponseEntity<UserDto> updateUser (@PathVariable long id, @RequestBody UserDto userDto)
    {
        UserDto updatedUserDto = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUserDto);

    }

    @GetMapping({"/{id}"})
    @Operation(summary = "Get user by id", description = "Get a user by its id.")
    public ResponseEntity<UserDto> getUserById (@PathVariable long id)
    {
UserDto searchUser = userService.getUserById(id);
        return ResponseEntity.ok(searchUser);
    }
}



