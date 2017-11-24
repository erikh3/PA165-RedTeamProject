package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.dto.UserCreateDTO;
import cz.fi.muni.pa165.teamred.dto.UserDTO;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import cz.fi.muni.pa165.teamred.service.*;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jozef Cibík
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    private final static Logger log = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Inject
    private UserService userService;

    @Inject
    private PassengerService passengerService;

    @Autowired
    private BeanMappingService beanMappingService;

    public Long createUser(UserCreateDTO user){
        User mappedUser = beanMappingService.mapTo(user, User.class);
        User newUser = userService.createUser(mappedUser);

        log.debug("Created new User: " + newUser.toString());

        return newUser.getId();
    }

    @Override
    public void editUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setNickname(userDTO.getNickname());

        log.debug("Edited user: " + user.toString());

        userService.editUser(user);
    }

    @Override
    public void removeUser(Long userId) {
        User userToDelete = new User();
        userToDelete.setId(userId);
        userService.deleteUser(userToDelete);
    }

    @Override
    public void addRideAsPassenger(Long userId, Long rideId) {
        passengerService.addPassengerToRide(userId, rideId);
    }

    @Override
    public void removeRideFromPassenger(Long userId, Long rideId) {
        passengerService.removePassengerFromRide(userId, rideId);
    }

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userService.findUserById(userId);

        if (user == null){
            log.debug("User not found with id: " + userId);
            return null;
        }

        log.debug("Found User in " + UserFacadeImpl.class + "with paramenters" + user.toString());

        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserByNickname(String nick) {
        User user = userService.findUserByNickname(nick);

        if (user == null){
            log.debug("User not found with nickname: " + nick);
            return null;
        }

        log.debug("Found User in " + UserFacadeImpl.class + "with paramenters" + user.toString());

        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        ArrayList<User> users = (ArrayList<User>) userService.findAllUsers();

        log.debug("Found " + users.size() + " Users in " + UserFacadeImpl.class);

        return beanMappingService.mapTo(users, UserDTO.class);
    }

    @Override
    public Collection<RideDTO> getUserRidesAsDriver(Long userId) {
        Collection<Ride> rides = userService.getUserRidesAsDriver(userId);
        return beanMappingService.mapTo(rides, RideDTO.class);
    }

    @Override
    public Collection<RideDTO> getUserRidesAsPassenger(Long userId) {
        Collection<Ride> rides = userService.getUserRidesAsPassenger(userId);
        return beanMappingService.mapTo(rides, RideDTO.class);
    }

}
