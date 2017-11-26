package cz.fi.muni.pa165.teamred.service;

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
     * @param user - new user to create
     */
    User createUser(User user);
    /**
     * Updates a User.
     * @param user - user to update
     */
    void editUser(User user);
    /**
     * Deletes a User.
     * @param user - user to delete
     */
    void deleteUser(User user);

    /**
     * Finds a User by id.
     * @param id - users ID
     * @return - found User, null otherwise
     */
    User findUserById(Long id);

    /**
     * Finds all users.
     * @return - all users , empty list if no user found
     */
    Collection<User> findAllUsers();

    /**
     * Finds a User by nickname.
     * @param nickname - Users nickname
     * @return - found User, null otherwise
     */
    User findUserByNickname(String nickname);

    /**
     * Finds all users rides as driver.
     * @return - all users, empty list if no user found
     */
    Collection<Ride> getUserRidesAsDriver(Long userId);

    /**
     * Finds all users rides as passenger.
     * @return - all users, empty list if no user found
     */
    Collection<Ride> getUserRidesAsPassenger(Long userId);
}
