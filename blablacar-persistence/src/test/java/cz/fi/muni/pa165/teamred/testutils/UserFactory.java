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
    public static User createUser(String name, String surName, String nickName, String loginId) {
        User user = new User();
        user.setName(name);
        user.setSurname(surName);
        user.setNickname(nickName);
        user.setLoginId(loginId);
        return user;
    }

    public static User createUser(String name, String surName, String nickName, String loginId, Set<Ride> ridesAsDriver) {
        User user = createUser(name, surName, nickName, loginId);

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
                                  String loginId,
                                  Set<Ride> ridesAsDriver,
                                  Set<Ride> ridesAsPassenger) {
        User user = createUser(name, surName, nickName, loginId, ridesAsDriver);

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
                                  String loginId,
                                  Set<Ride> ridesAsDriver,
                                  Set<Ride> ridesAsPassenger,
                                  Set<Comment> comments) {
        User user = createUser(name, surName, nickName, loginId, ridesAsDriver, ridesAsPassenger);

        if (comments != null) {
            for (Comment comment : comments) {
                user.addComment(comment);
                comment.setAuthor(user);
            }
        }

        return user;
    }

    public static User createAdam() {
        return createUser("Adam", "Adamecký", "YO_boss", "99998");
    }

    public static User createBob() {
        return createUser("Bob", "Ross", "paintIsLife", "99999");
    }
}
