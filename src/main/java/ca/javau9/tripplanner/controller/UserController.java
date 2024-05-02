package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.dto.UserRegOrUpdRequest;
import ca.javau9.tripplanner.exception.EmailAlreadyRegisteredException;
import ca.javau9.tripplanner.exception.UserNotFoundException;
import ca.javau9.tripplanner.exception.UsernameAlreadyExistsException;
import ca.javau9.tripplanner.models.UserDto;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("api/users")
public class UserController {
    UserService userService;


    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegOrUpdRequest userRequest) {
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

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long userId, /*@Valid*/
                                               @RequestBody UserRegOrUpdRequest userRegOrUpdRequest) {
        try {
            UserEntity updatedUserEntity = userService.updateUserProfile(userId, userRegOrUpdRequest);
            return ResponseEntity.ok(updatedUserEntity);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user profile.");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserAccount(@PathVariable Long userId) {
        try {
            userService.deleteUserAccount(userId);
            return ResponseEntity.ok("User account deleted successfully.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user account.");
        }
    }



    @PostMapping("/")
    public ResponseEntity<UserDto>  createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        Optional<UserDto> userInBox = userService.getUserByIdDto(id);
        return userInBox
                .map( ResponseEntity::ok )
                .orElseGet( () -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto){
        Optional<UserDto> userInBox = userService.updateUser(id, userDto);

        return userInBox
                .map( ResponseEntity::ok )
                .orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }






}
