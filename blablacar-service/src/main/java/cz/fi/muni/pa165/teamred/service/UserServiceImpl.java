package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.UserDao;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

// todo instead of illegal argument ex user service ex
@Service
public class UserServiceImpl implements UserService {

    final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private UserDao userDao;

    @Autowired
    private RideService rideService;

    //___________________________________________________________________________________________________________Create
    @Override
    public User createUser(User user) throws IllegalArgumentException {
        if (!isValidUser(user)) {
            throw new IllegalArgumentException("");
        }

        userDao.create(user);
        log.info("Service: " + UserServiceImpl.class + "created User: " + user.toString());
        return user;
    }

    //____________________________________________________________________________________________________________Update
    @Override
    public void editUser(User user) throws IllegalArgumentException {
        if (!isValidUser(user)) {
            throw new IllegalArgumentException("");
        }

        userDao.update(user);
        log.info("Service: " + UserServiceImpl.class + "updated User: " + user.toString());
    }

    //____________________________________________________________________________________________________________Delete
    @Override
    public void deleteUser(User user) throws IllegalArgumentException {
        if (userDao.findById(user.getId()) != null){
            userDao.delete(user);
            log.info("Service: " + UserServiceImpl.class + "removed User: " + user.toString());
        }
        else {
            log.info("Service: " + UserServiceImpl.class + "did not find User: " + user.toString());
        }

    }

    //______________________________________________________________________________________________________________Adds
    @Override
    public void addUserRideAsPassenger(User user, Ride ride) throws IllegalArgumentException {
        if (user.getRidesAsPassenger().contains(ride)) {
            throw new IllegalArgumentException("User: " + user.getNickname() +
                    " is already a passenger in ride:" +
                    ride.toString());
        }
        user.addRideAsPassenger(ride);
        log.info("Service: " + UserServiceImpl.class + "added Ride as Passenger: " +
                ride.toString() +
                " to User: " + user.toString());
    }

    @Override
    public void addUserRideAsDriver(User user, Ride ride) throws IllegalArgumentException {
        if (user.getRidesAsDriver().contains(ride)) {
            throw new IllegalArgumentException("User: " + user.getNickname() +
                    " is already a driver in ride:" +
                    ride.toString());
        }
        user.addRideAsDriver(ride);
        log.info("Service: " + UserServiceImpl.class + "added Ride as Driver: " +
                ride.toString() +
                " to User: " + user.toString());
    }

    @Override
    public void addUserComment(User user, Comment comment) throws IllegalArgumentException {
        if (user.getUserComments().contains(comment)) {
            throw new IllegalArgumentException("User: " + user.getNickname() +
                    " already have this comment:" +
                    comment.toString());
        }
        user.addComment(comment);
        log.info("Service: " + UserServiceImpl.class + "added Comment: " +
                comment.toString() +
                " to User: " + user.toString());
    }

    //___________________________________________________________________________________________________________Removes
    @Override
    public void removeUserRideAsPassenger(User user, Ride ride) throws IllegalArgumentException {
        user.removerRideAsPassenger(ride);
        log.info("Service: " + UserServiceImpl.class + "removed Ride as Passenger: " +
                ride.toString() +
                " from User: " + user.toString());
    }

    @Override
    public void removeUserRideAsDriver(User user, Ride ride) throws IllegalArgumentException {
        user.removerRideAsDriver(ride);
        log.info("Service: " + UserServiceImpl.class + "added Ride as Driver: " +
                ride.toString() +
                " from User: " + user.toString());
    }

    @Override
    public void removeUserComment(User user, Comment comment) throws IllegalArgumentException {
        user.removeComment(comment);
        log.info("Service: " + UserServiceImpl.class + "removed Comment: " +
                comment.toString() +
                " from User: " + user.toString());
    }


    //_____________________________________________________________________________________________________________Finds
    @Override
    public User findUserById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Passed id paramenter is null");
        }
        User found = userDao.findById(id);
        log.info("Service: " + UserServiceImpl.class + "found User: " +
                found.toString());
        return found;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> found = userDao.findAll();
        log.info("Service: " +
                UserServiceImpl.class +
                "found: " +
                found.size() +
                " users.");
        return found;
    }

    @Override
    public User findUserByNickname(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Passed name paramenter is null");
        }
        User found = userDao.findByNickname(name);
        log.info("Service: " + UserServiceImpl.class + "found User: " +
                found.toString());
        return found;
    }

    private boolean isValidUser(User user) {
        if (user == null) {
            return false;
        }
        String nickname = user.getNickname();
        if (nickname == null || nickname.length() == 0) {
            return false;
        }
        String name = user.getName();
        if (name == null || name.length() == 0) {
            return false;
        }
        String surname = user.getSurname();
        if (surname == null || surname.length() == 0) {
            return false;
        }

        /*Checking birthdate with actual time check*/
        return true;
    }
}
