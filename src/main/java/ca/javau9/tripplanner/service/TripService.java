package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.dto.TripRequest;
import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.exception.UserNotFoundException;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

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

    public Trip getTripById(Long tripId) {
        Optional<Trip> box = tripRepository.findById(tripId);
        if(box.isPresent()) {
            return box.get();
        } else {
            throw new TripNotFoundException("Trip not found with ID: " + tripId);
        }
    }

    public Trip updateTripDetails(Long tripId, TripRequest tripRequest) {
        Trip existingTrip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with ID: " + tripId));
        existingTrip.setDestination(tripRequest.getDestination());
        existingTrip.setStartDate(tripRequest.getStartDate());
        existingTrip.setEndDate(tripRequest.getEndDate());
        existingTrip.setBudget(tripRequest.getBudget());
        return tripRepository.save(existingTrip);
    }
}
