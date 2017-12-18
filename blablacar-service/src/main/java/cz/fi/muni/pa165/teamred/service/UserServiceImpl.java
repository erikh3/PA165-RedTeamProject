package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.UserDao;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            throw new IllegalArgumentException(UserServiceImpl.class +
                    " - User argument is not valid.");
        }

        userDao.create(user);
        log.debug(UserServiceImpl.class +
                " - Created User: " + user.toString());
        return user;
    }

    //____________________________________________________________________________________________________________Update
    @Override
    public void editUser(User user) throws IllegalArgumentException {
        if (!isValidUser(user)) {
            throw new IllegalArgumentException(UserServiceImpl.class +
                    " - User argument is not valid.");
        }

        userDao.update(user);
        log.debug(UserServiceImpl.class +
                " - Updated User: " + user.toString());
    }

    //____________________________________________________________________________________________________________Delete
    @Override
    public void deleteUser(User user) throws IllegalArgumentException {
        if (user == null){
            throw new IllegalArgumentException(UserServiceImpl.class +
                    " - Null User.");
        }
        if (userDao.findById(user.getId()) == null) {
            throw new IllegalArgumentException(UserServiceImpl.class +
                    " - Not found User: " + user);
        }
        userDao.delete(user);
        log.debug("Service: " + UserServiceImpl.class + "removed User: " + user.toString());
    }

    //_____________________________________________________________________________________________________________Finds
    @Override
    public User findUserById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException(UserServiceImpl.class +
                    " - Passed id paramenter is null");
        }
        User found = userDao.findById(id);
        log.debug(UserServiceImpl.class +
                " - Found User: " + found.toString());

        return found;
    }

    @Override
    public User findUserByLoginId(String loginId) throws IllegalArgumentException {
        if (loginId == null) {
            throw new IllegalArgumentException(UserServiceImpl.class +
                    " - Passed loginId paramenter is null");
        }

        User found = userDao.findByLoginId(loginId);
        log.debug(UserServiceImpl.class +
                " - Found User: " + found.toString());

        return found;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> found = userDao.findAll();
        log.debug(UserServiceImpl.class +
                " - found: " + found.size() +
                " users.");

        return found;
    }

    @Override
    public User findUserByNickname(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException(UserServiceImpl.class +
                    " - Name paramenter is null");
        }
        User found = userDao.findByNickname(name);
        log.debug(UserServiceImpl.class +
                " - found User: " + found.toString());

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

        return true;
    }
}
