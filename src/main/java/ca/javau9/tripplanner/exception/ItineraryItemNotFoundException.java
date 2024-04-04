package ca.javau9.tripplanner.exception;

public class ItineraryItemNotFoundException extends RuntimeException{
    public ItineraryItemNotFoundException(String message) {
        super(message);
    }
}
