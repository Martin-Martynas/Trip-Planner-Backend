package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.dto.UserRegOrUpdRequest;
import ca.javau9.tripplanner.exception.EmailAlreadyRegisteredException;
import ca.javau9.tripplanner.exception.TripNotFoundException;
import ca.javau9.tripplanner.exception.UserNotFoundException;
import ca.javau9.tripplanner.exception.UsernameAlreadyExistsException;
import ca.javau9.tripplanner.models.UserDto;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.payload.requests.LoginRequest;
import ca.javau9.tripplanner.payload.requests.UpdateRequest;
import ca.javau9.tripplanner.security.JwtUtils;
import ca.javau9.tripplanner.service.AuthService;
import ca.javau9.tripplanner.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    UserService userService;
    JwtUtils jwtUtils;
    @Autowired
    AuthService authService;

    public UserController (UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, HttpServletRequest request){
        String username = jwtUtils.extractUsernameFromToken(request);
        try{
            UserDto userDto = userService.getUserById(id, username);

            logger.info("userDto for React - {}", userDto);

            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser( @PathVariable Long id, @RequestBody UpdateRequest updateRequest,
                                         HttpServletRequest request){
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
        String pathVariable = request.getPathInfo();

        logger.info("pathVariable : {}", pathVariable);

        String pathTranslated = request.getPathTranslated();
        logger.info("pathTranslated : {}", pathTranslated);

        String username = jwtUtils.extractUsernameFromToken(request);
        try{
            UserDto updatedUser = authService.updateUser(id, updateRequest, username);
            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request){
        String username = jwtUtils.extractUsernameFromToken(request);
        return userService.deleteUser(id, username) ? ResponseEntity.ok().build() :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // for Admin
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }



    //<editor-fold desc="scrapped code">
    /*@GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username, HttpServletRequest request) {
        try {
            String usernameByJwt = jwtUtils.extractUsernameFromToken(request);
            if(username.equals(usernameByJwt)) {
                UserDto userDto = userService.getUserByUsername(username);
                return ResponseEntity.ok(userDto);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user details.");
        }
    }*/


    /*@PutMapping("/{username}")
    public ResponseEntity<?> updateUserProfile(*//*@Valid*//*@RequestBody UserDto userDto, HttpServletRequest request) {
        try {
            String username = jwtUtils.extractUsernameFromToken(request);
            UserDto updatedUser = userService.updateUserProfile(UserDto, username);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user profile.");
        }
    }*/


    /*@RequestMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegOrUpdRequest userRequest) {
        try{
            userService.registerUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (UsernameAlreadyExistsException | EmailAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/


    /*@GetMapping("/details/{idOrUsername}")
    public ResponseEntity<?> getUserDetails(@PathVariable String idOrUsername) {
        try {
            try {
                Long userEntityId = Long.parseLong(idOrUsername);
                UserEntity userEntity = userService.getUserById_(userEntityId);
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
    }*/

    /*@PutMapping("/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long userId, *//*@Valid*//*
                                               @RequestBody UserRegOrUpdRequest userRegOrUpdRequest) {
        try {
            UserEntity updatedUserEntity = userService.updateUserProfile(userId, userRegOrUpdRequest);
            return ResponseEntity.ok(updatedUserEntity);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user profile.");
        }
    }*/

    /*@DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserAccount(@PathVariable Long userId) {
        try {
            userService.deleteUserAccount(userId);
            return ResponseEntity.ok("User account deleted successfully.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user account.");
        }
    }*/


    /*@PostMapping("/")
    public ResponseEntity<UserDto>  createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }*/



    /*@GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        Optional<UserDto> userInBox = userService.getUserById(id);
        return userInBox
                .map( ResponseEntity::ok )
                .orElseGet( () -> ResponseEntity.notFound().build());
    }*/

    /*@PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto){
        Optional<UserDto> userInBox = userService.updateUser(id, userDto);

        return userInBox
                .map( ResponseEntity::ok )
                .orElseGet( () -> ResponseEntity.notFound().build());
    }*/

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
    //</editor-fold>

}
