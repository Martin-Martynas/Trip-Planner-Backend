package ca.javau9.tripplanner.exception;

public class JwtAuthenticationException extends Throwable {
    public JwtAuthenticationException(String message, Exception e) {
        super(message, e);
    }
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
