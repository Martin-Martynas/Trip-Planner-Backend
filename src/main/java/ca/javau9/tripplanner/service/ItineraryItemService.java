package ca.javau9.tripplanner.service;


import ca.javau9.tripplanner.exception.IncorrectUserException;
import ca.javau9.tripplanner.exception.ItineraryItemNotFoundException;
import ca.javau9.tripplanner.models.*;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import ca.javau9.tripplanner.utils.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItineraryItemService {
    private final ItineraryItemRepository itineraryItemRepository;
    TripService tripService;
    EntityMapper entityMapper;
    UserService userService;

    @Autowired
    public ItineraryItemService(ItineraryItemRepository itineraryItemRepository, TripService tripService,
                                EntityMapper entityMapper, UserService userService){
        this.itineraryItemRepository = itineraryItemRepository;
        this.tripService = tripService;
        this.userService = userService;
        this.entityMapper = entityMapper;
    }

    public ItineraryItemDto createItineraryItem(ItineraryItemDto itineraryItemDto, String username) {
        ItineraryItem itemBeforeSave = entityMapper.toItineraryItem(itineraryItemDto);
        Trip trip = tripService.getTripById(itineraryItemDto.getTripId());
        itemBeforeSave.setTrip(trip);
        ItineraryItem itemAfterSave = itineraryItemRepository.save(itemBeforeSave);
        return entityMapper.toItineraryItemDto(itemAfterSave);
    }

    public List<ItineraryItem> getItineraryItemsForTrip(Long tripId) {
        Trip trip = tripService.getTripById(tripId);
        return trip.getItineraryItems();
    }

    public ItineraryItemDto getItineraryItemById(Long id, String username) {
        Optional<ItineraryItem> itemInBox = itineraryItemRepository.findById(id);
        if(itemInBox.isPresent()) {
            ItineraryItem item = itemInBox.get();
            UserEntity user = item.getTrip().getUserEntity();
            if(user.getUsername().equals(username)) {
                return entityMapper.toItineraryItemDto(item);
            } else {
                throw new IncorrectUserException("Itinerary item" + id + "does not belong to this user");
            }
        } else {
            throw new ItineraryItemNotFoundException("Itinerary item not found with ID: " + id);
        }
    }

    public ItineraryItemDto updateItineraryItem(Long id, ItineraryItemDto itemDto, String username) {
        Optional<ItineraryItem> itemInBox = itineraryItemRepository.findById(id);
        if (itemInBox.isEmpty()) {
            throw new ItineraryItemNotFoundException("Itinerary item not found");
        }
        ItineraryItem item = itemInBox.get();
        UserEntity user = item.getTrip().getUserEntity();
        if(user.getUsername().equals(username)) {
            item.setItineraryDate(itemDto.getItineraryDate());
            item.setActivityTime(itemDto.getActivityTime());
            item.setActivity(itemDto.getActivity());
            item.setCost(itemDto.getCost());
            item.setNotes(itemDto.getNotes());
            ItineraryItem itemAfterSave = itineraryItemRepository.save(item);
            return entityMapper.toItineraryItemDto(itemAfterSave);
        } else {
            throw new IncorrectUserException("Itinerary item" + id + "does not belong to this user");
        }
    }

    public boolean deleteItineraryItem(Long id, String username) {
        Optional <ItineraryItem> itemInBox = itineraryItemRepository.findById(id);
        if(itemInBox.isPresent()) {
            ItineraryItem item = itemInBox.get();
            UserEntity user = item.getTrip().getUserEntity();
            if(user.getUsername().equals(username)) {
                itineraryItemRepository.delete(item);
                return true;
            }
        }
        return false;
    }

}
