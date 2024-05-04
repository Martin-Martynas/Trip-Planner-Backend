package ca.javau9.tripplanner.utils;

import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.models.TripDto;
import ca.javau9.tripplanner.models.UserDto;
import ca.javau9.tripplanner.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    public UserEntity toUserEntity(UserDto dto) {

        UserEntity entity = new UserEntity();
        entity.setId( dto.getId());
        entity.setUsername( dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }
    //Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {

    public UserDto toUserDto(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRoles()
        );
    }

    //Long id, String destination, LocalDate startDate, LocalDate endDate, Double budget

    public TripDto toTripDto(Trip trip) {
        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getId());
        tripDto.setDestination(trip.getDestination());
        tripDto.setStartDate(trip.getStartDate());
        tripDto.setEndDate(trip.getEndDate());
        tripDto.setBudget(trip.getBudget());
        return tripDto;
    }

    public Trip toTrip(TripDto tripDto) {
        Trip trip = new Trip();
        trip.setId(tripDto.getId());
        trip.setDestination(tripDto.getDestination());
        trip.setStartDate(tripDto.getStartDate());
        trip.setEndDate(tripDto.getEndDate());
        trip.setBudget(tripDto.getBudget());
        return trip;
    }
}
