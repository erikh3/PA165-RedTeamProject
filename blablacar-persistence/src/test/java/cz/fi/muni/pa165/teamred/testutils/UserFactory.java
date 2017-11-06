package cz.fi.muni.pa165.teamred.testutils;

import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;

import java.util.Set;

/**
 * @author Erik Horváth
 */
@SuppressWarnings("WeakerAccess")
public class UserFactory {
    public static User createUser(String name, String surName, String nickName) {
        User user = new User();
        user.setName(name);
        user.setSurname(surName);
        user.setNickname(nickName);
        return user;
    }

    public static User createUser(String name, String surName, String nickName, Set<Ride> ridesAsDriver) {
        User user = createUser(name, surName, nickName);

        if (ridesAsDriver != null) {
            for (Ride ride : ridesAsDriver) {
                user.addRideAsDriver(ride);
                ride.setDriver(user);
            }
        }

        return user;
    }

    public static User createUser(String name,
                                  String surName,
                                  String nickName,
                                  Set<Ride> ridesAsDriver,
                                  Set<Ride> ridesAsPassenger) {
        User user = createUser(name, surName, nickName, ridesAsDriver);

        if (ridesAsPassenger != null) {
            for (Ride ride : ridesAsPassenger) {
                user.addRideAsPassenger(ride);
                ride.addPassenger(user);
            }
        }

        return user;
    }

    public static User createUser(String name,
                                  String surName,
                                  String nickName,
                                  Set<Ride> ridesAsDriver,
                                  Set<Ride> ridesAsPassenger,
                                  Set<Comment> comments) {
        User user = createUser(name, surName, nickName, ridesAsDriver, ridesAsPassenger);

        if (comments != null) {
            for (Comment comment : comments) {
                user.addComment(comment);
                comment.setAuthor(user);
            }
        }

        return user;
    }

    public static User createAdam() {
        return createUser("Adam", "Adamecký", "YO_boss");
    }

    public static User createBob() {
        return createUser("Bob", "Ross", "paintIsLife");
    }
}
