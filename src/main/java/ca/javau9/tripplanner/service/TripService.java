package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.controller.TripController;
import ca.javau9.tripplanner.dto.TripRequest;
import ca.javau9.tripplanner.exception.IncorrectUserException;
import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.exception.UserNotFoundException;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.models.TripDto;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import ca.javau9.tripplanner.repository.TripRepository;
import ca.javau9.tripplanner.repository.UserRepository;
import ca.javau9.tripplanner.utils.EntityMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger logger = LoggerFactory.getLogger(TripService.class);



    public TripService (TripRepository tripRepository, ItineraryItemRepository itineraryItemRepository,
                        UserRepository userRepository, UserService userService, EntityMapper entityMapper) {
        this.tripRepository = tripRepository;
        this.itineraryItemRepository = itineraryItemRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.entityMapper = entityMapper;
    }

    public TripDto createTrip(TripDto tripDto, String username) {

        logger.info("tripDto : {}", tripDto);

        Trip tripBeforeSave = entityMapper.toTrip(tripDto);

        logger.info("tripBeforeSave : {}", tripBeforeSave);

        UserEntity user = userService.getUserByUsername(username);

        logger.info("user : {}", user);

        tripBeforeSave.setUserEntity(user);

        logger.info("tripBeforeSave : {}", tripBeforeSave);

        Trip tripAfterSave = tripRepository.save(tripBeforeSave);

        logger.info("tripAfterSave : {}", tripAfterSave);

        TripDto dto = entityMapper.toTripDto(tripAfterSave);

        logger.info("dto : {}", dto);

        return dto;

    }

    public TripDto getTripDtoById(Long tripId) {
        Optional<Trip> box = tripRepository.findById(tripId);
        if(box.isPresent()) {
            return entityMapper.toTripDto(box.get());
        } else {
            throw new TripNotFoundException("Trip not found with ID: " + tripId);
        }
    }

    public TripDto updateTrip(Long id, TripDto tripDto, String username) {
        Optional<Trip> tripInBox = tripRepository.findById(id);
        if (tripInBox.isEmpty()) {
            throw new TripNotFoundException("Trip Not Found");
        }
        Trip trip = tripInBox.get();
        logger.info("trip out of box: {}", trip);
        if (trip.getUserEntity().getUsername().equals(username)) {
            trip.setDestination(tripDto.getDestination());
            trip.setStartDate(tripDto.getStartDate());
            trip.setEndDate(tripDto.getEndDate());
            trip.setBudget(tripDto.getBudget());

            logger.info("trip after change: {}", trip);
            Trip tripAfterSave = tripRepository.save(trip);
            logger.info("tripAfterSave : {}", tripAfterSave);

            TripDto tripDtoForReturn = entityMapper.toTripDto(tripAfterSave);
            logger.info("tripDtoForReturn : {}", tripDtoForReturn);
            return tripDtoForReturn;
        } else {
            throw new IncorrectUserException("Trip id" + id + "does not belong to this user");
        }
    }
    public boolean deleteTrip(Long id, String username) {
        Optional<Trip> tripInBox = tripRepository.findById(id);
        logger.info("tripInBox : {}", tripInBox);

        if(tripInBox.isPresent()){
            Trip trip = tripInBox.get();

            logger.info("trip out of box : {}", trip);
            UserEntity user = trip.getUserEntity();

            if (user.getUsername().equals(username)) {
                logger.info("Deleting itinerary items for trip: {}", username);
                logger.info("Deleting itinerary items for trip: {}", trip.getUserEntity());
                logger.info("Deleting itinerary items for trip: {}", trip.getUserEntity().getUsername());
                logger.info("Deleting itinerary items for trip: {}", trip.getId());

                /*List<Trip> trips = user.getTrips();
                trips.remove(trip);
                user.setTrips(trips);

                trip.setUserEntity();*/

                /*userRepository.save(user);*/

                itineraryItemRepository.deleteByTrip(trip);

                logger.info("Deleting trip: {}", id);

                tripRepository.delete(trip);



                logger.info("Deleting trip: {}", id);


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



    /*public Trip updateTripDetails(Long tripId, TripRequest tripRequest) {
        Trip existingTrip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with ID: " + tripId));
        existingTrip.setDestination(tripRequest.getDestination());
        existingTrip.setStartDate(tripRequest.getStartDate());
        existingTrip.setEndDate(tripRequest.getEndDate());
        existingTrip.setBudget(tripRequest.getBudget());
        return tripRepository.save(existingTrip);
    }*/



    public List<TripDto> getTripDtosByUsername(String username) {
        UserEntity userEntity = userService.getUserByUsername(username);
        List<Trip> trips = userEntity.getTrips();
        return trips.stream()
                .map(entityMapper::toTripDto)
                .toList();
    }
}
