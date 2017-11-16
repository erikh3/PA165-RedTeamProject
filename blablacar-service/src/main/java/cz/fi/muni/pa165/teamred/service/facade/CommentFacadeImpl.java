package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.CommentCreateDTO;
import cz.fi.muni.pa165.teamred.dto.CommentDTO;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.facade.CommentFacade;
import cz.fi.muni.pa165.teamred.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        mappedComment.setRide(rideService.findById(commentCreateDTO.getRideId()));
        mappedComment.setAuthor(userService.findUserById(commentCreateDTO.getAuthorId()));
        Comment comment = commentService.createComment(mappedComment);
        return comment.getId();
    }

    @Override
    public void changeText(Long commentId, String newText) {
        Comment comment = commentService.findById(commentId);
        comment.setText(newText);
        commentService.updateComment(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = new Comment();
        comment.setId(commentId);
        commentService.deleteComment(comment);
    }

    @Override
    public CommentDTO getCommentWithId(Long commentId) {
        Comment comment = commentService.findById(commentId);
        return (comment == null) ? null : beanMappingService.mapTo(comment, CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        return beanMappingService.mapTo(commentService.findAll(), CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getCommentsWithRide(Long rideId) {
        return beanMappingService.mapTo(commentService.findAllWithRideId(rideId), CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getCommentsWithAuthor(Long userId) {
        return beanMappingService.mapTo(commentService.findAllWithAuthorId(userId), CommentDTO.class);
    }
}
