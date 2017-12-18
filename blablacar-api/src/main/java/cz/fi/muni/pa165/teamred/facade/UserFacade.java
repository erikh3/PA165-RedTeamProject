package cz.fi.muni.pa165.teamred.facade;

import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.dto.UserCreateDTO;
import cz.fi.muni.pa165.teamred.dto.UserDTO;

import java.util.Collection;

/**
 * @author Jozef Cibík
 */
public interface UserFacade {

    /**
     * Creates a user.
     *
     * @param userDTO userDTO object to be created as User object
     * @return user ID
     */
    Long createUser(UserCreateDTO userDTO);

    /**
     * Updates a user with @param userDTO attributes.
     *
     * @param userDTO 's attributes will update old User attributes.
     */
    void editUser(UserDTO userDTO);

    /**
     * Removes a user.
     *
     * @param userId used to find a User
     */
    void removeUser(Long userId);


    /**
     * Adds a ride as a passenger to a user.
     * This method is responsible for initializing a reference in both sides of relationship.
     *
     * @param userId to find a user to add a ride to
     * @param rideId to find a ride which will be added
     */
    void addRideAsPassenger(Long userId, Long rideId);

    /**
     * Removes specific ride as a passenger from a user
     * This method is responsible for removing user's reference to this ride.
     *
     * @param userId to find a user from which the ride will be removed
     * @param rideId to find a ride which will be removed from a user
     */
    void removeRideFromPassenger(Long userId, Long rideId);

    /**
     * Gets all Users.
     *
     * @return list of Users, empty list otherwise
     */
    Collection<UserDTO> getAllUsers();

    /**
     * Finds user by ID.
     *
     * @param userId user's ID
     * @return found user, null otherwise
     */
    UserDTO findUserById(Long userId);

    /**
     * Finds user by login ID provided by authorization third party.
     *
     * @param loginId user's ID
     * @return found user, null otherwise
     */
    UserDTO findUserByLoginId(String loginId);

    /**
     * Finds user by nickname.
     *
     * @param nickname to find
     * @return found user, null otherwise
     */
    UserDTO findUserByNickname(String nickname);

    /**
     * Gets all User rides as passenger.
     *
     * @return list of Rides, empty list otherwise
     */
    Collection<RideDTO> getUserRidesAsPassenger(Long userId);

    /**
     * Gets all User rides as driver.
     *
     * @return list of Rides, empty list otherwise
     */
    Collection<RideDTO> getUserRidesAsDriver(Long userId);

}
