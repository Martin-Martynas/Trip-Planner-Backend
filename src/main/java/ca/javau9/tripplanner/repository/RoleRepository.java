package ca.javau9.tripplanner.repository;

import ca.javau9.tripplanner.models.ERole;
import ca.javau9.tripplanner.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
