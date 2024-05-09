package ca.javau9.tripplanner.service;


import ca.javau9.tripplanner.exception.IncorrectUserException;
import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.models.TripDto;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import ca.javau9.tripplanner.repository.TripRepository;
import ca.javau9.tripplanner.repository.UserRepository;
import ca.javau9.tripplanner.utils.EntityMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    private final TripRepository tripRepository;
    ItineraryItemRepository itineraryItemRepository;
    UserRepository userRepository;
    UserService userService;
    private final EntityMapper entityMapper;


    public TripService (TripRepository tripRepository, ItineraryItemRepository itineraryItemRepository,
                        UserRepository userRepository, UserService userService, EntityMapper entityMapper) {
        this.tripRepository = tripRepository;
        this.itineraryItemRepository = itineraryItemRepository;
        this.userRepository = userRepository;
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

    public TripDto getTripDtoById(Long id, String username) {
        Optional<Trip> tripInBox = tripRepository.findById(id);
        if(tripInBox.isPresent()) {
            Trip trip = tripInBox.get();
            UserEntity user = trip.getUserEntity();
            if(user.getUsername().equals(username)) {
                return entityMapper.toTripDto(trip);
            }  else {
                throw new IncorrectUserException("You have no access to trip id" + id);
            }
        } else {
            throw new TripNotFoundException("Trip not found with ID: " + id);
        }
    }

    public TripDto updateTrip(Long id, TripDto tripDto, String username) {
        Optional<Trip> tripInBox = tripRepository.findById(id);
        if (tripInBox.isEmpty()) {
            throw new TripNotFoundException("Trip Not Found");
        }
        Trip trip = tripInBox.get();
        if (trip.getUserEntity().getUsername().equals(username)) {
            trip.setDestination(tripDto.getDestination());
            trip.setStartDate(tripDto.getStartDate());
            trip.setEndDate(tripDto.getEndDate());
            trip.setBudget(tripDto.getBudget());
            Trip tripAfterSave = tripRepository.save(trip);
            return entityMapper.toTripDto(tripAfterSave);
        } else {
            throw new IncorrectUserException("Trip id" + id + "does not belong to this user");
        }
    }
    public boolean deleteTrip(Long id, String username) {
        Optional<Trip> tripInBox = tripRepository.findById(id);
        if(tripInBox.isPresent()){
            Trip trip = tripInBox.get();
            UserEntity user = trip.getUserEntity();
            if (user.getUsername().equals(username)) {
                itineraryItemRepository.deleteByTrip(trip);
                tripRepository.delete(trip);
                return true;
            }
        }
        return false;
    }


    public Trip getTripById(Long tripId) {
        Optional<Trip> box = tripRepository.findById(tripId);
        if(box.isPresent()) {
            return box.get();
        } else {
            throw new TripNotFoundException("Trip not found with ID: " + tripId);
        }
    }

    public List<TripDto> getTripDtosByUsername(String username) {
        UserEntity userEntity = userService.getUserByUsername(username);
        List<Trip> trips = userEntity.getTrips();
        return trips.stream()
                .map(entityMapper::toTripDto)
                .toList();
    }
}
