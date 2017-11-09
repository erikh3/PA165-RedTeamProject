package cz.fi.muni.pa165.teamred.testutils;

import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;

import java.util.*;

/**
 * @author Erik Horváth
 */
@SuppressWarnings("WeakerAccess")
public class RideFactory {
    public static Ride createRide(Place source,
                                  Place destination,
                                  User driver,
                                  Date departure,
                                  int availableSeats,
                                  double seatPrice) {
        Ride ride = new Ride();

        ride.setSourcePlace(source);
        source.addOriginatingRide(ride);

        ride.setDestinationPlace(destination);
        destination.addDestinationRide(ride);

        ride.setDriver(driver);
        driver.addRideAsDriver(ride);

        ride.setDeparture(departure);

        ride.setAvailableSeats(availableSeats);

        ride.setSeatPrice(seatPrice);

        return ride;
    }

    public static Ride createRide(Place source,
                                  Place destination,
                                  User driver,
                                  Date departure,
                                  int availableSeats,
                                  double seatPrice,
                                  Set<User> passengers) {
        Ride ride = createRide(source, destination, driver, departure, availableSeats, seatPrice);

        if (passengers != null) {
            for (User user : passengers) {
                ride.addPassenger(user);
                user.addRideAsPassenger(ride);
            }
        }

        return ride;
    }

    public static Ride createRide(Place source,
                                  Place destination,
                                  User driver,
                                  Date departure,
                                  int availableSeats,
                                  double seatPrice,
                                  Set<User> passengers,
                                  Set<Comment> comments) {
        Ride ride = createRide(source, destination, driver, departure, availableSeats, seatPrice, passengers);

        if (comments != null) {
            for (Comment comment : comments) {
                ride.addComment(comment);
                comment.setRide(ride);
            }
        }

        return ride;
    }
}
