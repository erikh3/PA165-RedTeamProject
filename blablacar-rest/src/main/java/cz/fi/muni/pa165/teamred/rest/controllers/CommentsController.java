package cz.fi.muni.pa165.teamred.rest.controllers;

import cz.fi.muni.pa165.teamred.dto.ChangeCommentTextDTO;
import cz.fi.muni.pa165.teamred.dto.CommentCreateDTO;
import cz.fi.muni.pa165.teamred.dto.CommentDTO;
import cz.fi.muni.pa165.teamred.facade.CommentFacade;
import cz.fi.muni.pa165.teamred.rest.ApiUris;
import cz.fi.muni.pa165.teamred.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Erik Horváth
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_COMMENTS)
public class CommentsController {
    final static Logger logger = LoggerFactory.getLogger(CommentsController.class);

    @Inject
    private CommentFacade commentFacade;

    /**
     * Returns all comments
     * @return list of comments
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<CommentDTO> getComments() {
        logger.debug("rest getComments()");

        return commentFacade.getAllComments();
    }

    /**
     * Returns comment according to id
     * @param id
     * @return comment with id
     * @throws ResourceNotFoundException when comment with id was not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CommentDTO getComment(@PathVariable long id) throws ResourceNotFoundException {
        logger.debug("rest getComment({})", id);

        CommentDTO commentDTO = commentFacade.getCommentWithId(id);

        if (commentDTO == null) {
            throw new ResourceNotFoundException();
        }

        return commentDTO;
    }

    /**
     * Returns comments with author
     * @param id
     * @return list of comments
     */
    @RequestMapping(value = "/by-author/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<CommentDTO> getCommentsByAuthor(@PathVariable long id) {
        logger.debug("rest getCommentsByAuthor({})", id);

        return commentFacade.getCommentsWithAuthor(id);
    }

    /**
     * Returns comments for ride
     * @param id
     * @return list of comments
     */
    @RequestMapping(value = "/by-ride/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<CommentDTO> getCommentsByRide(@PathVariable long id) {
        logger.debug("rest getCommentsByRide({})", id);

        return commentFacade.getCommentsWithRide(id);
    }

    /**
     * Deletes comment with id
     * @param id
     * @throws ResourceNotFoundException when comment with id was not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteComment(@PathVariable long id) throws ResourceNotFoundException {
        logger.debug("rest deleteComment({id})", id);

        try {
            commentFacade.deleteComment(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Creates new comment
     * @param commentCreateDTO
     * @return created comment
     * @throws ResourceNotFoundException when comment could not be created
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CommentDTO createComment(@RequestBody CommentCreateDTO commentCreateDTO)
            throws ResourceNotFoundException {
        logger.debug("rest createComment()");

        try {
            Long id = commentFacade.createComment(commentCreateDTO);
            return commentFacade.getCommentWithId(id);
        } catch(Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Changes comment text
     * @param changeCommentTextDTO
     * @throws ResourceNotFoundException when comment with id was not found
     * @return
     */
    @RequestMapping(value = "/change-text", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CommentDTO changeCommentText(@RequestBody ChangeCommentTextDTO changeCommentTextDTO) {
        logger.debug("rest changeCommentText({}, {})",
                changeCommentTextDTO.getCommentId(),
                changeCommentTextDTO.getText());

        try {
            commentFacade.changeText(changeCommentTextDTO.getCommentId(), changeCommentTextDTO.getText());
            return commentFacade.getCommentWithId(changeCommentTextDTO.getCommentId());
        } catch(Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
