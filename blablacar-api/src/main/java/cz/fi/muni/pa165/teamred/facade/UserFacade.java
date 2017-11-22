package cz.fi.muni.pa165.teamred.facade;

import cz.fi.muni.pa165.teamred.dto.UserCreateDTO;
import cz.fi.muni.pa165.teamred.dto.UserDTO;

import java.util.Collection;

/**
 * @author Jozef Cibík
 */
public interface UserFacade {

    public Long createUser(UserCreateDTO userDTO);
    public void editUser(UserDTO userDTO);
    public void removeUser(Long userId);

    public void addComment(Long userId, Long commentId);
    public void addRideAsDriver(Long userId, Long rideId);
    public void addRideAsPassenger(Long userId, Long rideId);

    public void removeComment(Long userId, Long commentId);
    public void removeRideAsDriver(Long userId, Long rideId);
    public void removeRideAsPassenger(Long userId, Long rideId);

    Collection<UserDTO> getAllUsers();
    UserDTO findUserById(Long userId);
    UserDTO findUserByNickname(String name);
}
