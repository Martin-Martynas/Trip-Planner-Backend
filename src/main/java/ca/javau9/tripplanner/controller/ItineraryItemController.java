package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.dto.ItineraryItemRequest;
import ca.javau9.tripplanner.models.ItineraryItem;
import ca.javau9.tripplanner.service.ItineraryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/itinerary-items")
public class ItineraryItemController {
    ItineraryItemService itineraryItemService;

    @Autowired
    public ItineraryItemController(ItineraryItemService itineraryItemService) {
        this.itineraryItemService = itineraryItemService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createItineraryItem(@RequestBody ItineraryItemRequest itineraryItemRequest) {
        try {
            ItineraryItem createdItem = itineraryItemService.createItineraryItem(itineraryItemRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create itinerary item.");
        }
    }
}
