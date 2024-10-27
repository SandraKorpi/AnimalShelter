package sandrakorpi.animalshelterapi.Models;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String address;
    private String phone;
    private String email;
    private String availableBeds;


    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Animals> animals = new ArrayList<>();


    @Override
    public String toString() {     //egen metod för att skriva ut objektet istället för @ToString annotation.
        return "Shelters{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", availableBeds='" + availableBeds + '\'' +
                ", animals=" + animals +
                '}';
    }
}
