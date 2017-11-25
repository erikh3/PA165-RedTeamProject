package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Jozef Cibík
 */
@Service
public interface UserService {
    /**
     * Creates a new User.
     * @param user - new user to create in DB
     */
    User createUser(User user);
    /**
     * Updates a User.
     * @param user - user to update in DB
     */
    void editUser(User user);
    /**
     * Deletes a User.
     * @param user - user to delete in DB
     */
    void deleteUser(User user);

    /**
     * Add a ride as passenger to a user.
     * @param user User to add a ride to
     * @param ride Ride to be added to user
     */
    void addUserRideAsPassenger(User user, Ride ride);

    /**
     * Add a ride as driver to a user
     * @param user User to add a ride to
     * @param ride Ride to be added to user
     */
    void addUserRideAsDriver(User user, Ride ride);

    /**
     * Add a comment to a user.
     * @param user User to add a comment to
     * @param comment Comment to be added to a user
     */
    void addUserComment(User user, Comment comment);

    /**
     * Removes a users's ride as passenger.
     * @param user
     * @param ride
     */
    void removeUserRideAsPassenger(User user, Ride ride);

    /**
     * Removes a users's ride as driver.
     * @param user
     * @param ride
     */
    void removeUserRideAsDriver(User user, Ride ride);

    /**
     * Removes a users's comment.
     * @param user
     * @param comment
     */
    void removeUserComment(User user, Comment comment);

    /**
     * Finds a User by id.
     * @param id users ID in DB
     * @return found User, null otherwise
     */
    User findUserById(Long id);

    /**
     * Finds all users in DB.
     * @return all users in DB, empty list if no user found
     */
    Collection<User> findAllUsers();

    /**
     * Finds a User by nickname.
     * @param nickname Users nickname
     * @return found User, null otherwise
     */
    User findUserByNickname(String nickname);
}
