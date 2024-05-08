package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.dto.ItineraryItemRequest;
import ca.javau9.tripplanner.exception.ItineraryItemNotFoundException;
import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.models.ItineraryItem;
import ca.javau9.tripplanner.models.ItineraryItemDto;
import ca.javau9.tripplanner.security.JwtUtils;
import ca.javau9.tripplanner.service.ItineraryItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/itinerary-items")
public class ItineraryItemController {
    ItineraryItemService itineraryItemService;
    JwtUtils jwtUtils;

    @Autowired
    public ItineraryItemController(ItineraryItemService itineraryItemService, JwtUtils jwtUtils) {
        this.itineraryItemService = itineraryItemService;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createItineraryItem(@RequestBody ItineraryItemDto itineraryItemDto,
                                                 HttpServletRequest request) {
        try {
            String username = jwtUtils.extractUsernameFromToken(request);
            ItineraryItemDto createdItem = itineraryItemService.createItineraryItem(itineraryItemDto, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create itinerary item.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItineraryItemById(@PathVariable Long id, HttpServletRequest request) {
        try {
            String username = jwtUtils.extractUsernameFromToken(request);
            ItineraryItemDto itemDto = itineraryItemService.getItineraryItemById(id, username);
            return ResponseEntity.ok(itemDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*@GetMapping("/trip/{tripId}")
    public ResponseEntity<?> getItineraryItemsForTrip(@PathVariable Long tripId) {
        try {
            List<ItineraryItem> itineraryItems = itineraryItemService.getItineraryItemsForTrip(tripId);
            return ResponseEntity.ok(itineraryItems);
        } catch (TripNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve itinerary items.");
        }
    }*/

    @PutMapping("/{itineraryItemId}")
    public ResponseEntity<?> updateItineraryItem(@PathVariable Long id, /*@Valid*/
                                                 @RequestBody ItineraryItemDto itemDto, HttpServletRequest request) {
        try {
            String username = jwtUtils.extractUsernameFromToken(request);
            ItineraryItemDto updatedItem = itineraryItemService
                    .updateItineraryItem(id, itemDto, username);
            return ResponseEntity.ok(updatedItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItineraryItem(@PathVariable Long id, HttpServletRequest request) {
        String username = jwtUtils.extractUsernameFromToken(request);
        return  itineraryItemService.deleteItineraryItem(id, username) ? ResponseEntity.ok().build() :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
