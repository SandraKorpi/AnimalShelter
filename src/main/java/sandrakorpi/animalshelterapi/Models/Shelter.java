package sandrakorpi.animalshelterapi.Models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shelters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;
    private String phone;
    private String email;
    private String availableBeds;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animals> animals;


    @Override
    public String toString() {              //egen metod för att skriva ut objektet istället för @ToString annotation.
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
