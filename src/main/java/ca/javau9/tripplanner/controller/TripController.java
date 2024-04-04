package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.dto.TripRequest;
import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api/trips")
public class TripController {
    TripService tripService;

    @Autowired
    public TripController (TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTrip(@RequestBody TripRequest tripRequest) {
        try {
            Trip createdTrip = tripService.createTrip(tripRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create trip.");
        }
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<?> getTripDetails(@PathVariable Long tripId) {
        try {
            Trip trip = tripService.getTripById(tripId);
            return ResponseEntity.ok(trip);
        } catch (TripNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve trip details.");
        }
    }




}