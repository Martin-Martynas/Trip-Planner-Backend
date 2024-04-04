package ca.javau9.tripplanner.repository;

import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Transactional
    void deleteByUser(UserEntity userEntity);

}
