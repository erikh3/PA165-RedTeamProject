package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.User;

import java.util.List;

/**
 * User DAO interface to manage User class operations with database
 *
 * Created by Jozef Cibík on 25.10.2017.
 */
public interface UserDao {
    /**
     * Adds a user to database
     *
     * @param user to be added to db
     **/
    public void create(User user);
    /**
    * Finds a user with id in database
    *
    * @param id of the user to be found
    * @return User if found, null otherwise
    */
    public User findById(Long id);
    /**
    * Finds a user with nickname in database
    *
    * @param nickname of the user to be found
    * @return User if found, null otherwise
    */
    public User findByNickname(String nickname);
    /**
    * Finds all users in database
    *
    * @return List<User> if found, empty list otherwise
    */
    public List<User> findAll();
    /**
    * Deletes a user from database
    *
    * @param user to be deleted
    */
    public void delete(User user);
}
