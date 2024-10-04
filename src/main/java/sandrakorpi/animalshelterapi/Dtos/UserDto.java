package sandrakorpi.animalshelterapi.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sandrakorpi.animalshelterapi.Enums.Role;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

private long userId;
    private String email;
    private String userName;
    private String password;
    private List<Role> roles;
}
