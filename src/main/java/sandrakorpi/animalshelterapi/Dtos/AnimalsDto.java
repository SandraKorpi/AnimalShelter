package sandrakorpi.animalshelterapi.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalsDto {
    private Long id;
    private String name;
    private String animalType;
    private String breed;
    private int age;

}
