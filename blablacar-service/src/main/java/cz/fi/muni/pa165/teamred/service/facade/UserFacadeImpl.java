package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.UserCreateDTO;
import cz.fi.muni.pa165.teamred.dto.UserDTO;
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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        throw new NotImplementedException();
    }

    @Override
    public void removeUser(Long userId) {
        throw new NotImplementedException();
    }

    @Override
    public void addComment(Long userId, Long commentId) {
        userService.addUserComment(userService.findUserById(userId),
                commentService.findById(commentId));
        log.info("Added Comment: " + commentService.findById(commentId).toString() +
                "to User: " + userService.findUserById(userId).toString());
    }

    @Override
    public void addRideAsDriver(Long userId, Long rideId) {
        throw new NotImplementedException();
    }

    @Override
    public void addRideAsPassenger(Long userId, Long rideId) {
        throw new NotImplementedException();
    }

    @Override
    public void removeComment(Long userId, Long commentId) {
        userService.removeUserComment(userService.findUserById(userId),
                commentService.findById(commentId));
        log.info("Removed Comment: " + commentService.findById(commentId).toString() +
        "from User: " + userService.findUserById(userId).toString());
    }

    @Override
    public void removeRideAsDriver(Long userId, Long rideId) {
        throw new NotImplementedException();
    }

    @Override
    public void removeRideAsPassenger(Long userId, Long rideId) {
        throw new NotImplementedException();
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
