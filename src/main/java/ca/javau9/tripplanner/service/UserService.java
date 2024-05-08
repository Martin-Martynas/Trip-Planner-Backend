package ca.javau9.tripplanner.service;


import ca.javau9.tripplanner.exception.IncorrectUserException;
import ca.javau9.tripplanner.exception.UserNotFoundException;
import ca.javau9.tripplanner.models.UserDto;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import ca.javau9.tripplanner.repository.TripRepository;
import ca.javau9.tripplanner.repository.UserRepository;
import ca.javau9.tripplanner.utils.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;
    TripRepository tripRepository;
    ItineraryItemRepository itineraryItemRepository;

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

}
