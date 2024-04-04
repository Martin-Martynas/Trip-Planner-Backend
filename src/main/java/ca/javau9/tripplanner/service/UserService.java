package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.dto.UserRegOrUpdRequest;
import ca.javau9.tripplanner.exception.EmailAlreadyRegisteredException;
import ca.javau9.tripplanner.exception.UserNotFoundException;
import ca.javau9.tripplanner.exception.UsernameAlreadyExistsException;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import ca.javau9.tripplanner.repository.TripRepository;
import ca.javau9.tripplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    //private PasswordEncoder passwordEncoder;
    TripRepository tripRepository;
    ItineraryItemRepository itineraryItemRepository;

    @Autowired
    public UserService (UserRepository userRepository, TripRepository tripRepository,
                        ItineraryItemRepository itineraryItemRepository) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.itineraryItemRepository = itineraryItemRepository;
    }

    public void registerUser(UserRegOrUpdRequest userRequest) {

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


    public UserEntity getUserById(Long userEntityId) {
        Optional<UserEntity> box = userRepository.findById(userEntityId);
        if(box.isPresent()) {
            return box.get();
        } else {
            throw new UserNotFoundException("User not found with ID: " + userEntityId);
        }
    }

    public UserEntity getUserByUsername(String idOrUsername) {
        Optional<UserEntity> box = userRepository.findUserEntityByUsername(idOrUsername);
        if(box.isPresent()) {
            return box.get();
        } else {
            throw new UserNotFoundException("User not found with Username: " + idOrUsername);
        }

    }

    public UserEntity updateUserProfile(Long userId, UserRegOrUpdRequest userRegOrUpdRequest) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        existingUser.setUsername(userRegOrUpdRequest.getUsername());
        existingUser.setEmail(userRegOrUpdRequest.getEmail());
        existingUser.setPassword(userRegOrUpdRequest.getPassword());
        return userRepository.save(existingUser);
    }

    public void deleteUserAccount(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        tripRepository.deleteByUserEntity(userEntity);
        itineraryItemRepository.deleteByTripUserEntity(userEntity);

        userRepository.delete(userEntity);
    }
}
