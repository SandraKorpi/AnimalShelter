package sandrakorpi.animalshelterapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sandrakorpi.animalshelterapi.Models.Shelter;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Optional<Shelter> findByName(String name);

}
