package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.dto.ItineraryItemRequest;
import ca.javau9.tripplanner.exception.ItineraryItemNotFoundException;
import ca.javau9.tripplanner.models.ItineraryItem;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ItineraryItemService {
    ItineraryItemRepository itineraryItemRepository;
    TripService tripService;
    @Autowired
    public ItineraryItemService(ItineraryItemRepository itineraryItemRepository, TripService tripService){
        this.itineraryItemRepository = itineraryItemRepository;
        this.tripService = tripService;
    }


    public ItineraryItem createItineraryItem(ItineraryItemRequest itineraryItemRequest) {
        ItineraryItem itineraryItem = new ItineraryItem();
        itineraryItem.setTripDate(itineraryItemRequest.getTripDate());
        itineraryItem.setActivityTime(itineraryItemRequest.getActivityTime());
        itineraryItem.setActivity(itineraryItemRequest.getActivity());
        itineraryItem.setNotes(itineraryItemRequest.getNotes());
        return itineraryItemRepository.save(itineraryItem);
    }

    public List<ItineraryItem> getItineraryItemsForTrip(Long tripId) {
        Trip trip = tripService.getTripById(tripId);
        return trip.getItineraryItems();
    }

    public ItineraryItem getItineraryItemById(Long id) {
        Optional<ItineraryItem> box = itineraryItemRepository.findById(id);
        if(box.isPresent()) {
            return box.get();
        } else {
            throw new ItineraryItemNotFoundException("Itinerary item not found with ID: " + id);
        }
    }
}
