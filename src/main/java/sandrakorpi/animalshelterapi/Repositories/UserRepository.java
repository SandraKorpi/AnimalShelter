package sandrakorpi.animalshelterapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sandrakorpi.animalshelterapi.Enums.Role;
import sandrakorpi.animalshelterapi.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByUserName(String userName);
}
