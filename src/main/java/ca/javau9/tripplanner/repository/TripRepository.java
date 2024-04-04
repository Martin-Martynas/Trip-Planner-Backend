package ca.javau9.tripplanner.repository;

import ca.javau9.tripplanner.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {

}
