package sandrakorpi.animalshelterapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sandrakorpi.animalshelterapi.Models.Animals;

@Repository
public interface AnimalsRepository extends JpaRepository<Animals, Long> {
}
