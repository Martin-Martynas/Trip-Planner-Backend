package ca.javau9.tripplanner.repository;

import ca.javau9.tripplanner.models.ItineraryItem;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ItineraryItemRepository extends JpaRepository<ItineraryItem, Long> {
    @Transactional
    void deleteByTripUserEntity(UserEntity userEntity);

    @Transactional
    void deleteByTrip(Trip trip);


}
