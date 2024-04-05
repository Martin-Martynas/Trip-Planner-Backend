package ca.javau9.tripplanner;

import ca.javau9.tripplanner.models.ItineraryItem;
import ca.javau9.tripplanner.models.Trip;
import ca.javau9.tripplanner.models.UserEntity;
import ca.javau9.tripplanner.repository.ItineraryItemRepository;
import ca.javau9.tripplanner.repository.TripRepository;
import ca.javau9.tripplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyStarter implements CommandLineRunner {
    UserRepository userRepository;
    TripRepository tripRepository;
    ItineraryItemRepository itineraryItemRepository;

    @Autowired
    public MyStarter(UserRepository userRepository, TripRepository tripRepository,
                     ItineraryItemRepository itineraryItemRepository) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.itineraryItemRepository = itineraryItemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //seedDummyData();
    }

    private void seedDummyData() {
        List<UserEntity> users = new ArrayList<>();
        UserEntity userEntity1 = new UserEntity("John", "john@gmail.com", "nhoj");
        UserEntity userEntity2 = new UserEntity("Alice", "alice@mail.com", "ecila");
        users.add(userEntity1);
        users.add(userEntity2);


        List<Trip> trips1 = new ArrayList<>();
        Trip trip1 = new Trip("Klaipeda", LocalDate.now(), LocalDate.now().plusDays(2), 100D);
        Trip trip2 = new Trip("Telsiai", LocalDate.now(), LocalDate.now().plusDays(1),50D);
        trips1.add(trip1);
        trips1.add(trip2);

        List<Trip> trips2 = new ArrayList<>();
        Trip trip3 = new Trip("Vilnius", LocalDate.now(), LocalDate.now().plusDays(3),200D);
        Trip trip4 = new Trip("Riga", LocalDate.now(), LocalDate.now().plusDays(3),300D);
        trips2.add(trip3);
        trips2.add(trip4);

        List<ItineraryItem> itineraryItems1 = new ArrayList<>();
        ItineraryItem itineraryItem1 = new ItineraryItem(LocalDate.now(), LocalTime.now(), "Ekskursija", "po Senamiesti");
        ItineraryItem itineraryItem2 = new ItineraryItem(LocalDate.now(), LocalTime.now(), "Vakariene", "restorane");
        itineraryItems1.add(itineraryItem1);
        itineraryItems1.add(itineraryItem2);

        List<ItineraryItem> itineraryItems2 = new ArrayList<>();
        ItineraryItem itineraryItem3 = new ItineraryItem(LocalDate.now(), LocalTime.now(), "Pasivazinejimas1", "Autobusu1");
        ItineraryItem itineraryItem4 = new ItineraryItem(LocalDate.now(), LocalTime.now(), "Aqua parkas1", "2 valandos");
        itineraryItems2.add(itineraryItem3);
        itineraryItems2.add(itineraryItem4);

        List<ItineraryItem> itineraryItems3 = new ArrayList<>();
        ItineraryItem itineraryItem5 = new ItineraryItem(LocalDate.now(), LocalTime.now(), "Pasivazinejimas2", "Autobusu2");
        ItineraryItem itineraryItem6 = new ItineraryItem(LocalDate.now(), LocalTime.now(), "Aqua parkas2", "3 valandos");
        itineraryItems3.add(itineraryItem5);
        itineraryItems3.add(itineraryItem6);

        List<ItineraryItem> itineraryItems4 = new ArrayList<>();
        ItineraryItem itineraryItem7 = new ItineraryItem(LocalDate.now(), LocalTime.now(), "Pasivazinejimas3", "Autobusu3");
        ItineraryItem itineraryItem8 = new ItineraryItem(LocalDate.now(), LocalTime.now(), "Aqua parkas3", "4 valandos");
        itineraryItems4.add(itineraryItem7);
        itineraryItems4.add(itineraryItem8);


        userEntity1.setTrips(trips1);
        userEntity2.setTrips(trips2);

        trip1.setUserEntity(userEntity1);
        trip2.setUserEntity(userEntity1);

        trip3.setUserEntity(userEntity2);
        trip4.setUserEntity(userEntity2);

        trip1.setItineraryItem(itineraryItems1);
        trip2.setItineraryItem(itineraryItems2);

        trip3.setItineraryItem(itineraryItems3);
        trip4.setItineraryItem(itineraryItems4);


        itineraryItem1.setTrip(trip1);
        itineraryItem2.setTrip(trip1);
        itineraryItem3.setTrip(trip2);
        itineraryItem4.setTrip(trip2);
        itineraryItem5.setTrip(trip3);
        itineraryItem6.setTrip(trip3);
        itineraryItem7.setTrip(trip4);
        itineraryItem8.setTrip(trip4);

        userRepository.saveAll(users);

    }

}
