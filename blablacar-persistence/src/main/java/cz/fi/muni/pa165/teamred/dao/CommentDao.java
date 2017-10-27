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
    * @param c {@link cz.fi.muni.pa165.teamred.entity.Comment} to be saved to DB
    */
    public void create(Comment c);
    
    /**
    * Deletes {@link cz.fi.muni.pa165.teamred.entity.Comment} object in database
    * @param c {@link cz.fi.muni.pa165.teamred.entity.Comment} to be deleted 
    * from DB
    */
    public void delete(Comment c);
    
    /**
    * Finds {@link cz.fi.muni.pa165.teamred.entity.Comment} object in database
    * @param id unique id of desired object
    * @return {@link cz.fi.muni.pa165.teamred.entity.Comment} object with 
    * specified id, or null if not found
    */
    public Comment findById(Long id);
    
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
    */
    public List<Comment> getCommentsWithRideId(Long id);
    
    /**
    * Finds {@link cz.fi.muni.pa165.teamred.entity.Comment} objects in database
    * which belongs to specific {@link cz.fi.muni.pa165.teamred.entity.User}
    * @param id unique id of {@link cz.fi.muni.pa165.teamred.entity.User}
    * @return {@link cz.fi.muni.pa165.teamred.entity.Comment} objects belonging
    * to specified {@link cz.fi.muni.pa165.teamred.entity.User}
    */
    public List<Comment> getCommentsWithUserId(Long id);
}
