package ca.javau9.tripplanner.repository;

import ca.javau9.tripplanner.models.ItineraryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryItemRepository extends JpaRepository<ItineraryItem, Long> {

}
