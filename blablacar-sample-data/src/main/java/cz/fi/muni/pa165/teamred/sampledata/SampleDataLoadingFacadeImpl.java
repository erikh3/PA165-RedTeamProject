package cz.fi.muni.pa165.teamred.sampledata;

import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.service.CommentService;
import cz.fi.muni.pa165.teamred.service.PlaceService;
import cz.fi.muni.pa165.teamred.service.RideService;
import cz.fi.muni.pa165.teamred.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

/**
 * Loads some sample data to populate the database
 *
 * @author Erik Horváth
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    final static Logger logger = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Inject
    private CommentService commentService;
    @Inject
    private UserService userService;
    @Inject
    private PlaceService placeService;
    @Inject
    private RideService rideService;

    @Override
    public void loadData() throws IOException {
        // Sample places
        Place brno = createBrno();
        Place praha = createPraha();
        Place ostrava = createOstrava();
        Place bratislava = createBratislava();
        Place kosice = createKosice();

        savePlaces(brno, praha, ostrava, bratislava, kosice);

        // Sample users
        User alice = createAlice();
        User adam = createAdam();
        User bob = createBob();
        User john = createJohn();

        saveUsers(alice, adam, bob, john);

        // Sample rides
        Ride rideNow = createRideWithDateNow();
        rideNow.setSourcePlace(brno);
        brno.addOriginatingRide(rideNow);
        rideNow.setDestinationPlace(praha);
        praha.addDestinationRide(rideNow);
        rideNow.setDriver(alice);
        alice.addRideAsDriver(rideNow);
        rideNow.addPassenger(adam);
        adam.addRideAsPassenger(rideNow);
        rideNow.addPassenger(bob);
        bob.addRideAsPassenger(rideNow);

        Ride ride2015 = createRideIn2015();
        ride2015.setSourcePlace(praha);
        praha.addOriginatingRide(ride2015);
        ride2015.setDestinationPlace(ostrava);
        ostrava.addDestinationRide(ride2015);
        ride2015.setDriver(alice);
        alice.addRideAsDriver(ride2015);
        ride2015.addPassenger(john);
        john.addRideAsPassenger(ride2015);

        Ride ride2010 = createRideIn2010();
        ride2010.setSourcePlace(kosice);
        kosice.addOriginatingRide(ride2010);
        ride2010.setDestinationPlace(brno);
        brno.addDestinationRide(ride2010);
        ride2010.setDriver(john);
        john.addRideAsDriver(ride2010);
        ride2010.addPassenger(adam);
        adam.addRideAsPassenger(ride2010);

        Ride ride2015Full = createRideIn2015Full();
        ride2015Full.setSourcePlace(bratislava);
        bratislava.addOriginatingRide(ride2015Full);
        ride2015Full.setDestinationPlace(praha);
        praha.addDestinationRide(ride2015Full);
        ride2015Full.setDriver(alice);
        alice.addRideAsDriver(ride2015Full);
        ride2015Full.addPassenger(adam);
        adam.addRideAsPassenger(ride2015Full);
        ride2015Full.addPassenger(john);
        john.addRideAsPassenger(ride2015Full);
        ride2015Full.addPassenger(bob);
        bob.addRideAsPassenger(ride2015Full);

        saveRides(rideNow, ride2015, ride2010, ride2015Full);

        // Sample comments
        Comment commentNow = createSampleCommentNow();
        commentNow.setRide(rideNow);
        rideNow.addComment(commentNow);
        commentNow.setAuthor(adam);
        adam.addComment(commentNow);

        Comment commentIn2012 = createPositiveCommentIn2012();
        commentIn2012.setRide(ride2010);
        ride2010.addComment(commentIn2012);
        commentIn2012.setAuthor(adam);
        adam.addComment(commentIn2012);

        Comment commentIn2015 = createNegativeCommentIn2015();
        commentIn2015.setRide(ride2015Full);
        ride2015Full.addComment(commentIn2015);
        commentIn2015.setAuthor(bob);
        bob.addComment(commentIn2015);

        Comment anotherCommentIn2015 = new Comment();
        Calendar time = Calendar.getInstance();
        time.set(2015, Calendar.DECEMBER, 1, 11, 0, 0);
        anotherCommentIn2015.setCreated(time.getTime());
        anotherCommentIn2015.setText("We are packed like sardines :(");
        anotherCommentIn2015.setRide(ride2015Full);
        ride2015Full.addComment(anotherCommentIn2015);
        anotherCommentIn2015.setAuthor(john);
        john.addComment(anotherCommentIn2015);

        saveComments(commentNow, commentIn2012, commentIn2015, anotherCommentIn2015);

        logger.info("Loaded sample data.");
    }

    private void savePlaces(Place... places) {
        for (Place place : places) {
            placeService.createPlace(place);
        }
    }

    private void saveUsers(User... users) {
        for (User user : users) {
            userService.createUser(user);
        }
    }

    private void saveRides(Ride... rides) {
        for (Ride ride : rides) {
            rideService.createRide(ride);
        }
    }

    private void saveComments(Comment... comments) {
        for (Comment comment : comments) {
            commentService.createComment(comment);
        }
    }


    private static Place createBrno() {
        Place brno = new Place();
        brno.setName("Brno");
        return brno;
    }

    private static Place createPraha() {
        Place praha = new Place();
        praha.setName("Praha");
        return praha;
    }

    private static Place createOstrava() {
        Place ostrava = new Place();
        ostrava.setName("Ostrava");
        return ostrava;
    }

    private static Place createBratislava() {
        Place bratislava = new Place();
        bratislava.setName("Bratislava");
        return bratislava;
    }

    private static Place createKosice() {
        Place kosice = new Place();
        kosice.setName("Košice");
        return kosice;
    }

    private static User createAlice() {
        User alice = new User();
        alice.setName("Alice");
        alice.setNickname("ALI42");
        alice.setSurname("Lialam");
        alice.setLoginId("9999");
        return alice;
    }

    private static User createAdam() {
        User adam = new User();
        adam.setName("Adam");
        adam.setSurname("Moloh");
        adam.setNickname("XxX_<>_XxX");
        adam.setLoginId("9998");
        return adam;
    }

    private static User createBob() {
        User bob = new User();
        bob.setName("Bob");
        bob.setSurname("Josh");
        bob.setNickname("transp0nder_45");
        bob.setLoginId("9997");
        return bob;
    }

    private static User createJohn() {
        User john = new User();
        john.setName("John");
        john.setSurname("Doe");
        john.setNickname("NotReallyAnnoN");
        john.setLoginId("9996");
        return john;
    }

    private static Ride createRideWithDateNow() {
        Ride ride = new Ride();
        ride.setSeatPrice(457.2);
        ride.setAvailableSeats(2);
        ride.setDeparture(new Date());
        return ride;
    }

    private static Ride createRideIn2015() {
        Calendar date = Calendar.getInstance();
        date.set(2015, Calendar.MARCH, 15, 11, 45, 14);
        Ride ride = new Ride();
        ride.setSeatPrice(78.4);
        ride.setAvailableSeats(1);
        ride.setDeparture(date.getTime());
        return ride;
    }

    private static Ride createRideIn2010() {
        Calendar date = Calendar.getInstance();
        date.set(2010, Calendar.DECEMBER, 1, 16, 30, 0);
        Ride ride = new Ride();
        ride.setSeatPrice(120.0);
        ride.setAvailableSeats(3);
        ride.setDeparture(date.getTime());
        return ride;
    }

    private static Ride createRideIn2015Full() {
        Calendar date = Calendar.getInstance();
        date.set(2015, Calendar.JULY, 24, 22, 0, 0);
        Ride ride = new Ride();
        ride.setSeatPrice(250.0);
        ride.setAvailableSeats(0);
        ride.setDeparture(date.getTime());
        return ride;
    }

    private static Comment createSampleCommentNow() {
        Comment comment = new Comment();

        comment.setText("Sample comment");
        comment.setCreated(new Date());

        return comment;
    }

    private static Comment createPositiveCommentIn2012() {
        Calendar date = Calendar.getInstance();
        date.set(2012, Calendar.JANUARY, 8, 8, 32, 24);
        Comment comment = new Comment();
        comment.setText("Great ride, can only recommend!");
        comment.setCreated(date.getTime());
        return comment;
    }

    private static Comment createNegativeCommentIn2015() {
        Calendar date = Calendar.getInstance();
        date.set(2015, Calendar.NOVEMBER, 12, 13, 4, 47);
        Comment comment = new Comment();
        comment.setText("Never again... Just don't do it.");
        comment.setCreated(date.getTime());
        return comment;
    }
}
