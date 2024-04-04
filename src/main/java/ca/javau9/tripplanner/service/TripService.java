package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.dto.TripRequest;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TripService {
    TripRepository tripRepository;

    @Autowired
    public TripService (TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip createTrip(TripRequest tripRequest) {
        Trip trip = new Trip();
        trip.setDestination(tripRequest.getDestination());
        trip.setStartDate(tripRequest.getStartDate());
        trip.setEndDate(tripRequest.getEndDate());
        trip.setBudget(tripRequest.getBudget());

        return tripRepository.save(trip);

    }
}
