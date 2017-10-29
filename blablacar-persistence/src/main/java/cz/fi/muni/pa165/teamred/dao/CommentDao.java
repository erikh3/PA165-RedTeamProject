package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Comment;
import java.util.List;

/**
 * Interface for {@link cz.fi.muni.pa165.teamred.entity.Comment} DataAccessObject
 * 
 * @author miroslav.laco@gmail.com
 */
public interface CommentDao {
    
    /**
    * Creates {@link cz.fi.muni.pa165.teamred.entity.Comment} object in database
    * @param comment {@link cz.fi.muni.pa165.teamred.entity.Comment} to be saved to DB
    * @exception IllegalArgumentException if comment argument is null
    */
    public void create(Comment comment) throws IllegalArgumentException;
    
    /**
    * Deletes {@link cz.fi.muni.pa165.teamred.entity.Comment} object in database
    * @param comment {@link cz.fi.muni.pa165.teamred.entity.Comment} to be deleted 
    * from DB
    * @exception IllegalArgumentException if comment argument is null
    */
    public void delete(Comment comment) throws IllegalArgumentException;
    
    /**
    * Finds {@link cz.fi.muni.pa165.teamred.entity.Comment} object in database
    * @param id unique id of desired object
    * @return {@link cz.fi.muni.pa165.teamred.entity.Comment} object with 
    * specified id, or null if not found
    * @exception IllegalArgumentException if id argument is null
    */
    public Comment findById(Long id) throws IllegalArgumentException;
    
    /**
    * Finds all {@link cz.fi.muni.pa165.teamred.entity.Comment} objects in 
    * database
    * @return All {@link cz.fi.muni.pa165.teamred.entity.Comment} objects in DB
    */
    public List<Comment> findAll();
    
    /**
    * Finds {@link cz.fi.muni.pa165.teamred.entity.Comment} objects in database
    * which belongs to specific {@link cz.fi.muni.pa165.teamred.entity.Ride}
    * @param id unique id of {@link cz.fi.muni.pa165.teamred.entity.Ride}
    * @return {@link cz.fi.muni.pa165.teamred.entity.Comment} objects belonging
    * to specified {@link cz.fi.muni.pa165.teamred.entity.Ride}
    * @exception IllegalArgumentException if id argument is null
    */
    public List<Comment> getCommentsWithRideId(Long id) throws IllegalArgumentException;
    
    /**
    * Finds {@link cz.fi.muni.pa165.teamred.entity.Comment} objects in database
    * which belongs to specific {@link cz.fi.muni.pa165.teamred.entity.User}
    * @param id unique id of {@link cz.fi.muni.pa165.teamred.entity.User}
    * @return {@link cz.fi.muni.pa165.teamred.entity.Comment} objects belonging
    * to specified {@link cz.fi.muni.pa165.teamred.entity.User}
    * @exception IllegalArgumentException if id argument is null
    */
    public List<Comment> getCommentsWithUserId(Long id) throws IllegalArgumentException;
}
