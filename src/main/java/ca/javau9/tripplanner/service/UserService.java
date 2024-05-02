package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.dto.UserRegOrUpdRequest;
import ca.javau9.tripplanner.exception.EmailAlreadyRegisteredException;
import ca.javau9.tripplanner.exception.UserNotFoundException;
import ca.javau9.tripplanner.exception.UsernameAlreadyExistsException;
import ca.javau9.tripplanner.models.UserDto;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import ca.javau9.tripplanner.repository.TripRepository;
import ca.javau9.tripplanner.repository.UserRepository;
import ca.javau9.tripplanner.utils.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    //private PasswordEncoder passwordEncoder;
    private final EntityMapper entityMapper;
    TripRepository tripRepository;
    ItineraryItemRepository itineraryItemRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public UserService (UserRepository userRepository, EntityMapper entityMapper, TripRepository tripRepository,
                        ItineraryItemRepository itineraryItemRepository) {
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
        this.tripRepository = tripRepository;
        this.itineraryItemRepository = itineraryItemRepository;
    }

    //CRUD - Create, Read, Update, Delete

    public UserDto createUser(UserDto userDto) {
        UserEntity userEntityBeforeSave = entityMapper.toUserEntity(userDto);

        UserEntity userEntityAfterSave = userRepository.save(userEntityBeforeSave);

        return entityMapper.toUserDto(userEntityAfterSave);
    }

    public List<UserDto> getAllUsers(){
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(entityMapper::toUserDto)
                .toList();
    }
    public Optional<UserDto> getUserByIdDto(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);

        return user.map(entityMapper::toUserDto);
    }

    public Optional<UserDto> updateUser(Long id, UserDto userDto ){

        if( userRepository.existsById(id) ) {
            UserEntity userEntityBeforeSave = entityMapper.toUserEntity(userDto);
            userEntityBeforeSave.setId(id);

            UserEntity userEntityAfterSave = userRepository.save(userEntityBeforeSave);
            return Optional.of( entityMapper.toUserDto(userEntityAfterSave));

        } else {
            return Optional.empty();
        }

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        logger.info("Loaded :"+user.toString());
        return entityMapper.toUserDto(user);
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
