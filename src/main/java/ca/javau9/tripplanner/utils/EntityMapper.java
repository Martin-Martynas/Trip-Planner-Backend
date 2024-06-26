package ca.javau9.tripplanner.utils;

import ca.javau9.tripplanner.models.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public UserDto toUserDto(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRoles()
        );
    }

    public TripDto toTripDto(Trip trip) {
        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getId());
        tripDto.setDestination(trip.getDestination());
        tripDto.setStartDate(trip.getStartDate());
        tripDto.setEndDate(trip.getEndDate());
        tripDto.setBudget(trip.getBudget());
        tripDto.setCreatedBy(trip.getUserEntity().getUsername());

        List<ItineraryItem> itineraryItems = trip.getItineraryItems();

        if(itineraryItems!=null){
            tripDto.setItineraryItemDtos(toItineraryItemDtos(itineraryItems));
        }
        return tripDto;
    }

    public Trip toTrip(TripDto tripDto) {
        Trip trip = new Trip();
        trip.setDestination(tripDto.getDestination());
        trip.setStartDate(tripDto.getStartDate());
        trip.setEndDate(tripDto.getEndDate());
        trip.setBudget(tripDto.getBudget());
        return trip;
    }



    public ItineraryItem toItineraryItem(ItineraryItemDto itineraryItemDto) {
        ItineraryItem itineraryItem = new ItineraryItem();
        itineraryItem.setItineraryDate(itineraryItemDto.getItineraryDate());
        itineraryItem.setActivityTime(itineraryItemDto.getActivityTime());
        itineraryItem.setActivity(itineraryItemDto.getActivity());
        itineraryItem.setNotes(itineraryItemDto.getNotes());
        itineraryItem.setCost(itineraryItemDto.getCost());
        return itineraryItem;
    }


    public ItineraryItemDto toItineraryItemDto(ItineraryItem itineraryItem) {
        return new ItineraryItemDto(
                itineraryItem.getId(),
                itineraryItem.getItineraryDate(),
                itineraryItem.getActivityTime(),
                itineraryItem.getActivity(),
                itineraryItem.getCost(),
                itineraryItem.getNotes(),
                itineraryItem.getTrip().getId()
        );
    }

    public List<ItineraryItemDto> toItineraryItemDtos(List<ItineraryItem> itineraryItems) {
        List <ItineraryItemDto> itineraryItemDtos = new ArrayList<>();
        for (ItineraryItem itineraryItem: itineraryItems) {
            ItineraryItemDto itineraryItemDto = toItineraryItemDto(itineraryItem);
            itineraryItemDtos.add(itineraryItemDto);
        }
        return itineraryItemDtos;
    }



}
