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
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private UserDao userDao;

    //___________________________________________________________________________________________________________Create
    @Override
    public User createUser(User user) throws IllegalArgumentException {
        if (!isValidUser(user)) {
            throw new IllegalArgumentException("User argument is not valid.");
        }

        userDao.create(user);
        log.debug("Service: " + UserServiceImpl.class + "created User: " + user.toString());
        return user;
    }

    //____________________________________________________________________________________________________________Update
    @Override
    public void editUser(User user) throws IllegalArgumentException {
        if (!isValidUser(user)) {
            throw new IllegalArgumentException("User argument is not valid.");
        }

        userDao.update(user);
        log.debug("Service: " + UserServiceImpl.class + "updated User: " + user.toString());
    }

    //____________________________________________________________________________________________________________Delete
    @Override
    public void deleteUser(User user) throws IllegalArgumentException {
            if (user == null || userDao.findById(user.getId()) == null){
                throw new IllegalArgumentException("User null or not found, User: " + user);
            }
            userDao.delete(user);
            log.debug("Service: " + UserServiceImpl.class + "removed User: " + user.toString());
    }

    //_____________________________________________________________________________________________________________Finds
    @Override
    public User findUserById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Passed id paramenter is null");
        }
        User found = userDao.findById(id);
        log.debug("Service: " + UserServiceImpl.class + "found User: " +
                found.toString());
        return found;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> found = userDao.findAll();
        log.debug("Service: " +
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
        log.debug("Service: " + UserServiceImpl.class + "found User: " +
                found.toString());
        return found;
    }

    @Override
    public Collection<Ride> getUserRidesAsDriver(Long userId) {
        return this.findUserById(userId).getRidesAsDriver();
    }

    @Override
    public Collection<Ride> getUserRidesAsPassenger(Long userId) {
        return this.findUserById(userId).getRidesAsPassenger();
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

        /*Checking birthdate with actual time check maybe?*/
        return true;
    }
}
