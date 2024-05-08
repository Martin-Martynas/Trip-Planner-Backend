package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.models.TripDto;
import ca.javau9.tripplanner.security.JwtUtils;
import ca.javau9.tripplanner.service.TripService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/trips")
public class TripController {

    private final TripService tripService;
    JwtUtils jwtUtils;

    public TripController (TripService tripService, JwtUtils jwtUtils) {
        this.tripService = tripService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTrip(@RequestBody TripDto tripDto, HttpServletRequest request) {
        try {
            String username = jwtUtils.extractUsernameFromToken(request);
            TripDto createdTrip = tripService.createTrip(tripDto, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create trip.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrip(@PathVariable Long id, HttpServletRequest request) {
        try {
            String username = jwtUtils.extractUsernameFromToken(request);
            TripDto trip = tripService.getTripDtoById(id);

            if (!trip.getCreatedBy().equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You do not have permission to access this trip.");
            }
            return ResponseEntity.ok(trip);
        } catch (TripNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve trip details.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrip(@PathVariable Long id,
                                               @RequestBody TripDto tripDto, HttpServletRequest request) {
        try {
            String username = jwtUtils.extractUsernameFromToken(request);
            TripDto updatedTrip = tripService.updateTrip(id, tripDto, username);
            return ResponseEntity.ok(updatedTrip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable Long id, HttpServletRequest request ) {
        String username = jwtUtils.extractUsernameFromToken(request);
        return tripService.deleteTrip(id, username) ? ResponseEntity.ok().build() :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/my-trips")
    public ResponseEntity<List<TripDto>> getMyTrips(HttpServletRequest request){
        String username = jwtUtils.extractUsernameFromToken(request);
        if (username != null) {
            List<TripDto> tripDtos = tripService.getTripDtosByUsername(username);
            return ResponseEntity.ok(tripDtos);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
