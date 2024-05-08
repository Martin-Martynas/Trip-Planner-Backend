package ca.javau9.tripplanner.controller;

import ca.javau9.tripplanner.models.UserDto;
import ca.javau9.tripplanner.payload.requests.UpdateRequest;
import ca.javau9.tripplanner.security.JwtUtils;
import ca.javau9.tripplanner.service.AuthService;
import ca.javau9.tripplanner.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    JwtUtils jwtUtils;
    AuthService authService;

    public UserController (UserService userService, JwtUtils jwtUtils, AuthService authService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authService = authService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, HttpServletRequest request){
        String username = jwtUtils.extractUsernameFromToken(request);
        try{
            UserDto userDto = userService.getUserById(id, username);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser( @PathVariable Long id, @RequestBody UpdateRequest updateRequest,
                                         HttpServletRequest request){
        try{
            String username = jwtUtils.extractUsernameFromToken(request);
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

}
