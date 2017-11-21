package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.UserCreateDTO;
import cz.fi.muni.pa165.teamred.dto.UserDTO;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import cz.fi.muni.pa165.teamred.service.BeanMappingService;
import cz.fi.muni.pa165.teamred.service.CommentService;
import cz.fi.muni.pa165.teamred.service.RideService;
import cz.fi.muni.pa165.teamred.service.UserService;
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
    final static Logger log = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Inject
    private UserService userService;

    @Inject
    private CommentService commentService;

    @Inject
    private RideService rideService;

    @Autowired
    private BeanMappingService beanMappingService;

    public Long createUser(UserCreateDTO user){
        User mappedUser = beanMappingService.mapTo(user, User.class);
        User newUser = userService.createUser(mappedUser);
        log.info("Created new User: " + newUser.toString());
        return newUser.getId();
    }

    @Override
    public void editUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setNickname(userDTO.getNickname());
        userService.editUser(user);
    }

    @Override
    public void removeUser(Long userId) {
        //redundant object creation ?
        User userToDelete = new User();
        userToDelete.setId(userId);
        userService.deleteUser(userToDelete);
    }


    @Override
    public void addRideAsPassenger(Long userId, Long rideId) {
        User user = userService.findUserById(userId);
        if (user == null){
            log.info("No user found with id: " + userId);
            return;
        }
        Ride ride = rideService.findById(rideId);
        if (ride == null){
            log.info("No ride found with id: " + rideId);
            return;
        }

        userService.addUserRideAsPassenger(user, ride);
        rideService.addPassenger(ride,user);
        log.info("Added user: " + user +
        " as a passenger in ride: " + ride);
    }

    @Override
    public void removeComment(Long userId, Long commentId) {
        User user = userService.findUserById(userId);
        if (user == null){
            log.info("No user found with id: " + userId);
            return;
        }
        Comment comment = commentService.findById(commentId);
        if (comment == null){
            log.info("No comment found with id: " + commentId);
            return;
        }

        //deleting both sides of references
        userService.removeUserComment(user,comment);
        log.info("Removed Comment: " + comment.toString() +
        " and comment reference from User: " + user.toString());

        commentService.deleteComment(comment);
    }

    @Override
    public void removeRideAsDriver(Long userId, Long rideId) {
        User user = userService.findUserById(userId);
        if (user == null){
            log.info("No user found with id: " + userId);
            return;
        }
        Ride ride = rideService.findById(rideId);
        if (ride == null){
            log.info("No ride found with id: " + rideId);
            return;
        }
        //deleting both sides of references

        userService.removeUserRideAsDriver(user, ride);
        log.info("Removed ride:" + ride.toString() +
        " and ride reference from user( as a Driver ):" + user.toString());

        //method should remove passengers reference to this ride
        rideService.deleteRide(ride);
    }

    @Override
    public void removeRideAsPassenger(Long userId, Long rideId) {
        User user = userService.findUserById(userId);
        if (user == null){
            log.info("No user found with id: " + userId);
            return;
        }
        Ride ride = rideService.findById(rideId);
        if (ride == null){
            log.info("No ride found with id: " + rideId);
            return;
        }

        //deleting both sides of references

        userService.removeUserRideAsPassenger(user, ride);
        log.info("Removed ride reference:" + ride.toString() +
                " from user( as a Passenger ):" + user.toString());

        rideService.removePassenger(ride, user);
    }

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userService.findUserById(userId);
        log.info("Found User in " + UserFacadeImpl.class + "with paramenters" + user.toString());
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserByNickname(String nick) {
        User user = userService.findUserByNickname(nick);
        log.info("Found User in " + UserFacadeImpl.class + "with paramenters" + user.toString());
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        ArrayList<User> users = (ArrayList<User>) userService.findAllUsers();
        log.info("Found " + users.size() + " Users in " + UserFacadeImpl.class);
        return (users.size() == 0) ? new ArrayList<>() : beanMappingService.mapTo(users, UserDTO.class);
    }

}
