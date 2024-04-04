package ca.javau9.tripplanner.exception;

public class TripNotFoundException extends RuntimeException{
    public TripNotFoundException(String message) {
        super(message);
    }
}
