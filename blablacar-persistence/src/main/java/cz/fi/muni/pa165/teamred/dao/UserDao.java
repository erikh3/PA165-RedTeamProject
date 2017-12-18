package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.User;

import java.util.List;

/**
 * User DAO interface to manage User class operations with database
 *
 * @author Jozef Cibík
 */
public interface UserDao {
    /**
     * Adds a user to database
     *
     * @param user to be added to db
     * @exception IllegalArgumentException if user argument is null
     **/
    void create(User user) throws IllegalArgumentException;

    /**
     * Updates a user in database
     *
     * @param user to update
     * @exception IllegalArgumentException if user argument is null
     */
    void update(User user) throws IllegalArgumentException;

    /**
    * Finds a user with loginId in database
    *
    * @param loginId of the user (provided byt authorization third party)
    * @return User if found, null otherwise
    * @exception IllegalArgumentException if loginId argument is null
    */
    User findByLoginId(String loginId) throws IllegalArgumentException;

    /**
     * Finds a user with id in database
     *
     * @param id of the user to be found
     * @return User if found, null otherwise
     * @exception IllegalArgumentException if id argument is null
     */
    User findById(Long id) throws IllegalArgumentException;

    /**
    * Finds a user with nickname in database
    *
    * @param nickname of the user to be found
    * @return User if found, null otherwise
    * @exception IllegalArgumentException if nickname argument is null
    */
    User findByNickname(String nickname) throws IllegalArgumentException;

    /**
    * Finds all users in database
    *
    * @return {@link List<User>} if found, empty list otherwise
    */
    List<User> findAll();

    /**
    * Deletes a user from database
    *
    * @param user to be deleted
    * @exception IllegalArgumentException if user argument is null
    */
    void delete(User user) throws IllegalArgumentException;
    
}
