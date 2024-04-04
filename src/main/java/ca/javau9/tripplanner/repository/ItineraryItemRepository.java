package ca.javau9.tripplanner.repository;

import ca.javau9.tripplanner.models.ItineraryItem;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ItineraryItemRepository extends JpaRepository<ItineraryItem, Long> {
    @Transactional
    void deleteByTripUser(UserEntity userEntity);

    @Transactional
    void deleteByTrip(Trip trip);
}
