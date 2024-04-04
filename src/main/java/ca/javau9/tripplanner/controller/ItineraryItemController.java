package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.dto.ItineraryItemRequest;
import ca.javau9.tripplanner.exception.ItineraryItemNotFoundException;
import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.models.ItineraryItem;
import ca.javau9.tripplanner.service.ItineraryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getItineraryItemById(@PathVariable Long id) {
        try {
            ItineraryItem itineraryItem = itineraryItemService.getItineraryItemById(id);
            return ResponseEntity.ok(itineraryItem);
        } catch (ItineraryItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve itinerary item details.");
        }
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<?> getItineraryItemsForTrip(@PathVariable Long tripId) {
        try {
            List<ItineraryItem> itineraryItems = itineraryItemService.getItineraryItemsForTrip(tripId);
            return ResponseEntity.ok(itineraryItems);
        } catch (TripNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve itinerary items.");
        }
    }

    @PutMapping("/{itineraryItemId}")
    public ResponseEntity<?> updateItineraryItem(@PathVariable Long itineraryItemId, /*@Valid*/
                                                 @RequestBody ItineraryItemRequest itineraryItemRequest) {
        try {
            ItineraryItem updatedItineraryItem = itineraryItemService
                    .updateItineraryItem(itineraryItemId, itineraryItemRequest);
            return ResponseEntity.ok(updatedItineraryItem);
        } catch (ItineraryItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update itinerary item.");
        }
    }

    @DeleteMapping("/{itineraryItemId}")
    public ResponseEntity<?> deleteItineraryItem(@PathVariable Long itineraryItemId /*, Authentication authentication*/)
    {
        try {
            /*String username = authentication.getName();*/
            itineraryItemService.deleteItineraryItem(itineraryItemId /*, username*/);
            return ResponseEntity.ok("Itinerary item deleted successfully.");
        } catch (ItineraryItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } /*catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }*/ catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete itinerary item.");
        }
    }




}
