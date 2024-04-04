package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.dto.UserRegistrationRequest;
import ca.javau9.tripplanner.exception.EmailAlreadyRegisteredException;
import ca.javau9.tripplanner.exception.UsernameAlreadyExistsException;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    //private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserRegistrationRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already taken!");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new EmailAlreadyRegisteredException("Email is already registered!");
        }

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setUsername(userRequest.getUsername());
        newUserEntity.setEmail(userRequest.getEmail());
        newUserEntity.setPassword(userRequest.getPassword());

        userRepository.save(newUserEntity);
    }



}
