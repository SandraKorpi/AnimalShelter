package sandrakorpi.animalshelterapi.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShelterDto {
    private long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String availableBeds;

}
