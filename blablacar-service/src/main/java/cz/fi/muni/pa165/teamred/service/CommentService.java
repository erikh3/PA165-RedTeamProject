package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Erik Horváth
 */
@Service
public interface CommentService {
    Comment createComment(Comment comment) throws IllegalArgumentException;
    void updateComment(Comment comment) throws IllegalArgumentException;
    void deleteComment(Comment comment) throws IllegalArgumentException;
    Comment findById(Long id) throws IllegalArgumentException;
    List<Comment> findAll();
    List<Comment> findAllWithRideId(Long id) throws IllegalArgumentException;
    List<Comment> findAllWithAuthorId(Long id) throws IllegalArgumentException;
}
