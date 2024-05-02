package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.models.ERole;
import ca.javau9.tripplanner.models.Role;
import ca.javau9.tripplanner.models.UserDto;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.payload.requests.LoginRequest;
import ca.javau9.tripplanner.payload.requests.SignupRequest;
import ca.javau9.tripplanner.payload.responses.JwtResponse;
import ca.javau9.tripplanner.payload.responses.MessageResponse;
import ca.javau9.tripplanner.repository.RoleRepository;
import ca.javau9.tripplanner.repository.UserRepository;
import ca.javau9.tripplanner.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder encoder;

    JwtUtils jwtUtils;

    public AuthService (AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder encoder,
                        JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDto userDetails = (UserDto) authentication.getPrincipal();
        logger.info("Before: " + userDetails.toString());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    public MessageResponse registerUser(SignupRequest signupRequest) {

        checkUserExists(signupRequest);


        UserEntity user = createNewUser(signupRequest);
        Set<Role> roles = getInitialRoles(signupRequest);

        user.setRoles(roles);

        logger.info("Before: " + user.toString());
        user = userRepository.save(user);
        logger.info("After: " + user.toString());

        return new MessageResponse("User registered successfully!");
    }

    private UserEntity createNewUser(SignupRequest signupRequest) {
        UserEntity user = new UserEntity(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword())
                );
        logger.info(user.toString());
        return user;
    }

    private Set<Role> getInitialRoles(SignupRequest signupRequest) {
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            for (String role : strRoles) {
                Role resolvedRole = roleRepository.findByName(ERole.valueOf("ROLE_" + role.toUpperCase()))
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(resolvedRole);
            }
        }
        return roles;
    }

    private void checkUserExists(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Email is already in use!");
        }
    }





}
