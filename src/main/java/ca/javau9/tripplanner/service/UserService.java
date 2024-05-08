package ca.javau9.tripplanner.service;

import ca.javau9.tripplanner.dto.UserRegOrUpdRequest;
import ca.javau9.tripplanner.exception.EmailAlreadyRegisteredException;
import ca.javau9.tripplanner.exception.IncorrectUserException;
import ca.javau9.tripplanner.exception.UserNotFoundException;
import ca.javau9.tripplanner.exception.UsernameAlreadyExistsException;
import ca.javau9.tripplanner.models.UserDto;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.payload.requests.UpdateRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    //CRUD - Read, Update, Delete

    public UserDto getUserById(Long id, String username) {
        Optional<UserEntity> userInBox = userRepository.findById(id);
        if(userInBox.isPresent()) {
            UserEntity user = userInBox.get();
            if(user.getUsername().equals(username)) {
                return entityMapper.toUserDto(user);
            } else {
                throw new IncorrectUserException("User id" + id + "does not belong to" + username);
            }
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    /*public UserDto updateUser(Long id, UpdateRequest updateRequest, String username ){

        logger.info("Id before repository: {}", id);
        logger.info("UserDto before repository : {}", updateRequest);
        logger.info("username before repository: {}", username);

        Optional<UserEntity> userInBox = userRepository.findById(id);

        logger.info("userInBox: {}", userInBox);

        if(userInBox.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        UserEntity user = userInBox.get();

        logger.info("user out of box: {}", user);

        if(user.getUsername().equals(username)) {
            user.setEmail(updateRequest.getEmail());


            logger.info("user after change: {}", user);



            UserEntity userEntityAfterSave = userRepository.save(user);
            logger.info("userEntityAfterSave : {}", userEntityAfterSave);

            UserDto userDtoForReturn = entityMapper.toUserDto(userEntityAfterSave);
            logger.info("userDtoForReturn : {}", userDtoForReturn);
            return userDtoForReturn;

        } else {
            throw new IncorrectUserException("User id" + id + "does not belong to" + username);
        }
    }*/

    public boolean deleteUser(Long id, String username) {
        Optional<UserEntity> userInBox = userRepository.findById(id);
        if(userInBox.isPresent()){
            UserEntity user = userInBox.get();
            if(user.getUsername().equals(username)) {
                tripRepository.deleteByUserEntity(user);
                itineraryItemRepository.deleteByTripUserEntity(user);
                userRepository.delete(user);

                return true;
            }
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        logger.info("Loaded :"+user.toString());
        return entityMapper.toUserDto(user);
    }

    //only for Admins
    public List<UserDto> getAllUsers(){
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(entityMapper::toUserDto)
                .toList();
    }

    public UserEntity getUserByUsername(String username) {
        Optional<UserEntity> userInBox = userRepository.findByUsername(username);
        if(userInBox.isPresent()) {
            return userInBox.get();
        } else {
            throw new UserNotFoundException("User not found with Username: " + username);
        }
    }


    //<editor-fold desc="scraped code">

    /*public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already taken!");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyRegisteredException("Email is already registered!");
        }
        UserEntity userEntityBeforeSave = entityMapper.toUserEntity(userDto);
        UserEntity userEntityAfterSave = userRepository.save(userEntityBeforeSave);
        return entityMapper.toUserDto(userEntityAfterSave);
    }*/


 /*   public Optional<UserDto> getUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);

        return user.map(entityMapper::toUserDto);
    }
*/


    /*public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }*/



    /*public void registerUser(UserRegOrUpdRequest userRequest) {

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
    }*/


    /*public UserEntity getUserById_(Long userEntityId) {
        Optional<UserEntity> box = userRepository.findById(userEntityId);
        if(box.isPresent()) {
            return box.get();
        } else {
            throw new UserNotFoundException("User not found with ID: " + userEntityId);
        }
    }*/

    /*public UserEntity getUserByUsername(String idOrUsername) {
        Optional<UserEntity> box = userRepository.findUserEntityByUsername(idOrUsername);
        if(box.isPresent()) {
            return box.get();
        } else {
            throw new UserNotFoundException("User not found with Username: " + idOrUsername);
        }

    }*/

    /*public UserEntity updateUserProfile(Long userId, UserRegOrUpdRequest userRegOrUpdRequest) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        existingUser.setUsername(userRegOrUpdRequest.getUsername());
        existingUser.setEmail(userRegOrUpdRequest.getEmail());
        existingUser.setPassword(userRegOrUpdRequest.getPassword());
        return userRepository.save(existingUser);
    }*/

    /*public void deleteUserAccount(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        tripRepository.deleteByUserEntity(userEntity);
        itineraryItemRepository.deleteByTripUserEntity(userEntity);

        userRepository.delete(userEntity);
    }*/
    //</editor-fold>

}
