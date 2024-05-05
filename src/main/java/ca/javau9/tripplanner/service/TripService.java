package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.dto.TripRequest;
import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.models.TripDto;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import ca.javau9.tripplanner.repository.TripRepository;
import ca.javau9.tripplanner.utils.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    private final TripRepository tripRepository;
    ItineraryItemRepository itineraryItemRepository;
    UserService userService;
    private final EntityMapper entityMapper;



    @Autowired
    public TripService (TripRepository tripRepository, ItineraryItemRepository itineraryItemRepository,
                        UserService userService, EntityMapper entityMapper) {
        this.tripRepository = tripRepository;
        this.itineraryItemRepository = itineraryItemRepository;
        this.userService = userService;
        this.entityMapper = entityMapper;
    }

    public TripDto createTrip(TripDto tripDto, String username) {

        Trip tripBeforeSave = entityMapper.toTrip(tripDto);

        UserEntity user = userService.getUserByUsername(username);

        tripBeforeSave.setUserEntity(user);

        Trip tripAfterSave = tripRepository.save(tripBeforeSave);

        return entityMapper.toTripDto(tripAfterSave);

    }

    public TripDto getTripDtoById(Long tripId) {
        Optional<Trip> box = tripRepository.findById(tripId);
        if(box.isPresent()) {
            return entityMapper.toTripDto(box.get());
        } else {
            throw new TripNotFoundException("Trip not found with ID: " + tripId);
        }
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

    public List<TripDto> getTripDtosByUsername(String username) {
        UserEntity userEntity = userService.getUserByUsername(username);
        List<Trip> trips = userEntity.getTrips();
        return trips.stream()
                .map(entityMapper::toTripDto)
                .toList();
    }
}
