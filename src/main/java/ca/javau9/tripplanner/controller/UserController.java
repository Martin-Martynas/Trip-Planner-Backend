package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.dto.UserRegistrationRequest;
import ca.javau9.tripplanner.exception.EmailAlreadyRegisteredException;
import ca.javau9.tripplanner.exception.UsernameAlreadyExistsException;
import ca.javau9.tripplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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




}
