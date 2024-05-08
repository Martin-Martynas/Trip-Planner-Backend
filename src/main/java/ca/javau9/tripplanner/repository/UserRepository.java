package ca.javau9.tripplanner.repository;

import ca.javau9.tripplanner.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


    Optional<UserEntity> findByUsername(String username);


}
