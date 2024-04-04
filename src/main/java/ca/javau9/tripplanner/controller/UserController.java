package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.dto.UserRegistrationRequest;
import ca.javau9.tripplanner.exception.EmailAlreadyRegisteredException;
import ca.javau9.tripplanner.exception.UserNotFoundException;
import ca.javau9.tripplanner.exception.UsernameAlreadyExistsException;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/users")
public class UserController {
    UserService userService;


    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRequest) {
        try{
            userService.registerUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (UsernameAlreadyExistsException | EmailAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{idOrUsername}")
    public ResponseEntity<?> getUserDetails(@PathVariable String idOrUsername) {
        try {
            try {
                Long userEntityId = Long.parseLong(idOrUsername);
                UserEntity userEntity = userService.getUserById(userEntityId);
                return ResponseEntity.ok(userEntity);
            } catch (NumberFormatException e) {

                UserEntity userEntity = userService.getUserByUsername(idOrUsername);
                return ResponseEntity.ok(userEntity);
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user details.");
        }
    }






}
