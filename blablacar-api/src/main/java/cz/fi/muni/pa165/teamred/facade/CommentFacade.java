package cz.fi.muni.pa165.teamred.facade;

import cz.fi.muni.pa165.teamred.dto.CommentCreateDTO;
import cz.fi.muni.pa165.teamred.dto.CommentDTO;

import java.util.List;

/**
 * @author Erik Horváth
 */
// todo: exception handling
public interface CommentFacade {
    /**
     * Creates comment
     *
     * @param commentCreateDTO parameters will be used in creation of comment
     * @return id of created comment
     */
    Long createComment(CommentCreateDTO commentCreateDTO);

    /**
     * Sets new text for existing comment
     *
     * @param commentId of comment which text will be changed
     * @param newText text to set
     */
    void changeText(Long commentId, String newText);

    /**
     * Deletes comment
     *
     * @param commentId of comment which will be deleted
     */
    void deleteComment(Long commentId);

    /**
     * Retrieves comment
     *
     * @param commentId of comment which will be retrieved
     * @return found comment or null if none found
     */
    CommentDTO getCommentWithId(Long commentId);

    /**
     * Retrieves all comments
     *
     * @return list of comments
     */
    List<CommentDTO> getAllComments();

    /**
     * Retrieves comments for specific ride
     *
     * @param rideId of ride which comments will be retrieved
     * @return list of comments
     */
    List<CommentDTO> getCommentsWithRide(Long rideId);

    /**
     * Retrieved comments for specific user
     *
     * @param userId of author
     * @return list of comments
     */
    List<CommentDTO> getCommentsWithAuthor(Long userId);
}
