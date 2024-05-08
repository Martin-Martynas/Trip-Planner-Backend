package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.dto.TripRequest;
import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.models.TripDto;
import ca.javau9.tripplanner.models.UserDto;
import ca.javau9.tripplanner.security.JwtUtils;
import ca.javau9.tripplanner.service.TripService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Enumeration;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/trips")
public class TripController {

    private static final Logger logger = LoggerFactory.getLogger(TripController.class);
    TripService tripService;
    JwtUtils jwtUtils;

    @Autowired
    public TripController (TripService tripService, JwtUtils jwtUtils) {
        this.tripService = tripService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTrip(@RequestBody TripDto tripDto, HttpServletRequest request) {

        // Log request parameters
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            logger.info("Request parameter - {} : {}", paramName, paramValue);
        }

        // Log request headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            logger.info("Request header - {} : {}", headerName, headerValue);
        }

        try {
            String username = jwtUtils.extractUsernameFromToken(request);
            TripDto createdTrip = tripService.createTrip(tripDto, username);
            logger.info("TripDto : {}", createdTrip);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create trip.");
        }
    }



    /*@PostMapping("/create")
    public ResponseEntity<?> createTrip(@RequestBody TripRequest tripRequest) {
        try {
            Trip createdTrip = tripService.createTrip(tripRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create trip.");
        }
    }*/

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


    /*@GetMapping("/{tripId}")
    public ResponseEntity<?> getTripDetails(@PathVariable Long tripId) {
        try {
            Trip trip = tripService.getTripById(tripId);
            return ResponseEntity.ok(trip);
        } catch (TripNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve trip details.");
        }
    }*/



    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrip(@PathVariable Long id, /*@Valid*/
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

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            logger.info("Request parameter - {} : {}", paramName, paramValue);
        }

        // Log request headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            logger.info("Request header - {} : {}", headerName, headerValue);
        }


        String username = jwtUtils.extractUsernameFromToken(request);

        logger.info("username : {}", username);

        boolean response = tripService.deleteTrip(id, username);

        logger.info("response : {}", response);


        logger.info("username : {}", username);
        return response ? ResponseEntity.ok().build() :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/my-trips")
    public ResponseEntity<List<TripDto>> getMyTrips(HttpServletRequest request){

        // Log request parameters
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            logger.info("Request parameter - {} : {}", paramName, paramValue);
        }

        // Log request headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            logger.info("Request header - {} : {}", headerName, headerValue);
        }

        String username = jwtUtils.extractUsernameFromToken(request);
        if (username != null) {
            List<TripDto> tripDtos = tripService.getTripDtosByUsername(username);
            return ResponseEntity.ok(tripDtos);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
