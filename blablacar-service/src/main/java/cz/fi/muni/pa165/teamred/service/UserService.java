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
    User createUser(User user) throws IllegalArgumentException;
    void editUser(User user) throws IllegalArgumentException;
    void deleteUser(User user) throws IllegalArgumentException;

    void addUserRideAsPassenger(User user, Ride ride) throws IllegalArgumentException;
    void addUserRideAsDriver(User user, Ride ride) throws IllegalArgumentException;
    void addUserComment(User user, Comment comment) throws IllegalArgumentException;

    void removeUserRideAsPassenger(User user, Ride ride) throws IllegalArgumentException;
    void removeUserRideAsDriver(User user, Ride ride) throws IllegalArgumentException;
    void removeUserComment(User user, Comment comment) throws IllegalArgumentException;

    User findUserById(Long id)  throws IllegalArgumentException;
    Collection<User> findAllUsers();
    User findUserByNickname(String name) throws IllegalArgumentException;
}
