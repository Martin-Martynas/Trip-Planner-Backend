package ca.javau9.tripplanner.utils;

import ca.javau9.tripplanner.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntityMapper {

    private static final Logger logger = LoggerFactory.getLogger(EntityMapper.class);
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

    //Long id, String destination, LocalDate startDate, LocalDate endDate, Double budget, Strin createdBy

    public TripDto toTripDto(Trip trip) {
        TripDto tripDto = new TripDto();
        logger.info("tripDto : {}", tripDto);
        tripDto.setId(trip.getId());
        logger.info("setId : {}", tripDto);
        tripDto.setDestination(trip.getDestination());
        logger.info("setDestination : {}", tripDto);
        tripDto.setStartDate(trip.getStartDate());
        logger.info("setStartDate : {}", tripDto);
        tripDto.setEndDate(trip.getEndDate());
        logger.info("setEndDate : {}", tripDto);
        tripDto.setBudget(trip.getBudget());
        logger.info("setBudget : {}", tripDto);
        tripDto.setCreatedBy(trip.getUserEntity().getUsername());
        logger.info("setCreatedBy : {}", tripDto);

        List<ItineraryItem> itineraryItems = trip.getItineraryItems();
        logger.info("itineraryItems : {}", itineraryItems);

        if(itineraryItems!=null){
            tripDto.setItineraryItemDtos(toItineraryItemDtos(itineraryItems));
        }

        /*List<ItineraryItemDto> toItineraryItemDtos = toItineraryItemDtos(itineraryItems);
        logger.info("toItineraryItemDtos : {}", toItineraryItemDtos);
        tripDto.setItineraryItemDtos(toItineraryItemDtos);*/
        logger.info("tripDto : {}", tripDto);
        return tripDto;
    }

    public Trip toTrip(TripDto tripDto) {
        Trip trip = new Trip();
        /*trip.setId(tripDto.getId());*/
        trip.setDestination(tripDto.getDestination());
        trip.setStartDate(tripDto.getStartDate());
        trip.setEndDate(tripDto.getEndDate());
        trip.setBudget(tripDto.getBudget());
        return trip;
    }

    //Long id, LocalDate itineraryDate, LocalTime activityTime, String activity, String notes,
    //                         Integer cost, LocalDateTime createdAt, LocalDateTime updatedAt, Trip trip



    public ItineraryItem toItineraryItem(ItineraryItemDto itineraryItemDto) {
        ItineraryItem itineraryItem = new ItineraryItem();
        itineraryItem.setItineraryDate(itineraryItemDto.getItineraryDate());
        itineraryItem.setActivityTime(itineraryItemDto.getActivityTime());
        itineraryItem.setActivity(itineraryItemDto.getActivity());
        itineraryItem.setNotes(itineraryItemDto.getNotes());
        itineraryItem.setCost(itineraryItemDto.getCost());
        return itineraryItem;
    }

    //Long id, LocalDate itineraryDate, LocalTime activityTime, String activity, Integer cost,
    //                            String notes, Long tripId


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
        logger.info("metodas_itineraryItems : {}", itineraryItems);
        List <ItineraryItemDto> itineraryItemDtos = new ArrayList<>();
        for (ItineraryItem itineraryItem: itineraryItems) {
            ItineraryItemDto itineraryItemDto = toItineraryItemDto(itineraryItem);
            itineraryItemDtos.add(itineraryItemDto);
        }
        return itineraryItemDtos;
    }



}
