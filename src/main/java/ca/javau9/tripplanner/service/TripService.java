package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.dto.TripRequest;
import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import ca.javau9.tripplanner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TripService {
    TripRepository tripRepository;
    ItineraryItemRepository itineraryItemRepository;

    @Autowired
    public TripService (TripRepository tripRepository, ItineraryItemRepository itineraryItemRepository) {
        this.tripRepository = tripRepository;
        this.itineraryItemRepository = itineraryItemRepository;
    }

    public Trip createTrip(TripRequest tripRequest) {
        Trip trip = new Trip();
        trip.setDestination(tripRequest.getDestination());
        trip.setStartDate(tripRequest.getStartDate());
        trip.setEndDate(tripRequest.getEndDate());
        trip.setBudget(tripRequest.getBudget());

        //trip has to be assigned to the user

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

    public void deleteTrip(Long tripId /*, String username*/) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with ID: " + tripId));
       /* if (!trip.getUserEntity().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("You are not authorized to delete this trip.");
        }*/
        itineraryItemRepository.deleteByTrip(trip);

        tripRepository.delete(trip);
    }
}
