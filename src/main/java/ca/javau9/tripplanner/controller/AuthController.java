package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.payload.requests.LoginRequest;
import ca.javau9.tripplanner.payload.requests.SignupRequest;
import ca.javau9.tripplanner.payload.responses.JwtResponse;
import ca.javau9.tripplanner.payload.responses.MessageResponse;
import ca.javau9.tripplanner.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser (@RequestBody LoginRequest loginRequest){
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {


        try {
            MessageResponse response = authService.registerUser(signupRequest);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {

            return ResponseEntity.status(e.getStatusCode())
                    .body(new MessageResponse(e.getReason()));
        }
    }

}
