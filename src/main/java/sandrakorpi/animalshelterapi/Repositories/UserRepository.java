package sandrakorpi.animalshelterapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sandrakorpi.animalshelterapi.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
