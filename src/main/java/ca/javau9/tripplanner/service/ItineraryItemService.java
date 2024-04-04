package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.dto.ItineraryItemRequest;
import ca.javau9.tripplanner.models.ItineraryItem;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ItineraryItemService {
    ItineraryItemRepository itineraryItemRepository;
    @Autowired
    public ItineraryItemService(ItineraryItemRepository itineraryItemRepository){
        this.itineraryItemRepository = itineraryItemRepository;
    }


    public ItineraryItem createItineraryItem(ItineraryItemRequest itineraryItemRequest) {
        ItineraryItem itineraryItem = new ItineraryItem();
        itineraryItem.setTripDate(itineraryItemRequest.getTripDate());
        itineraryItem.setActivityTime(itineraryItemRequest.getActivityTime());
        itineraryItem.setActivity(itineraryItemRequest.getActivity());
        itineraryItem.setNotes(itineraryItemRequest.getNotes());
        return itineraryItemRepository.save(itineraryItem);
    }
}
