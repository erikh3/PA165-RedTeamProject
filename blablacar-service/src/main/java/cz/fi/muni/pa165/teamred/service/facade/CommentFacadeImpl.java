package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.CommentCreateDTO;
import cz.fi.muni.pa165.teamred.dto.CommentDTO;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.facade.CommentFacade;
import cz.fi.muni.pa165.teamred.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Erik Horváth
 */
@Service
@Transactional
public class CommentFacadeImpl implements CommentFacade {
    final static Logger log = LoggerFactory.getLogger(CommentFacadeImpl.class);

    @Inject
    private CommentService commentService;

    @Inject
    private RideService rideService;

    @Inject
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createComment(CommentCreateDTO commentCreateDTO) {
        Comment mappedComment = beanMappingService.mapTo(commentCreateDTO, Comment.class);
        Ride ride = rideService.findById(commentCreateDTO.getRideId());
        mappedComment.setRide(ride);
        ride.addComment(mappedComment);

        User user = userService.findUserById(commentCreateDTO.getAuthorId());
        mappedComment.setAuthor(user);
        user.addComment(mappedComment);

        Comment comment = commentService.createComment(mappedComment);

        log.debug("Comment with id(" + comment.getId() + ") has been created");

        return comment.getId();
    }

    @Override
    public void changeText(Long commentId, String newText) {
        Comment comment = commentService.findById(commentId);
        comment.setText(newText);
        commentService.updateComment(comment);

        log.debug("Comment text has been updated for comment id(" + comment.getId() + ")");
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentService.findById(commentId);

        commentService.deleteComment(comment);

        User author = comment.getAuthor();
        author.removeComment(comment);

        Ride ride = comment.getRide();
        ride.removeComment(comment);

        log.debug("Comment with id(" + comment.getId() + ") has been deleted");
    }

    @Override
    public CommentDTO getCommentWithId(Long commentId) {
        Comment comment = commentService.findById(commentId);

        if (comment == null) {
            log.debug("Comment with id(" + commentId + ") has not been found");
            return null;
        }

        log.debug("Comment with id(" + commentId + ") has been retrieved");
        return beanMappingService.mapTo(comment, CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentService.findAll();
        log.debug("All comment have been retrieved, amount of comments: " + comments.size());
        return beanMappingService.mapTo(comments, CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getCommentsWithRide(Long rideId) {
        List<Comment> comments = commentService.findAllWithRideId(rideId);
        log.debug("Comments with ride id(" + rideId + ") has been retrieved, amount of comments: " + comments.size());
        return beanMappingService.mapTo(comments, CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getCommentsWithAuthor(Long userId) {
        List<Comment> comments = commentService.findAllWithAuthorId(userId);
        log.debug("Comments with author id(" + userId + ") has been retrieved, amount of comments: " + comments.size());
        return beanMappingService.mapTo(comments, CommentDTO.class);
    }
}
