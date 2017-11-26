package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.CommentDao;
import cz.fi.muni.pa165.teamred.entity.Comment;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author Erik Horváth
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Inject
    private CommentDao commentDao;

    @Inject
    private TimeService timeService;

    @Override
    public Comment createComment(Comment comment) throws IllegalArgumentException {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null.");
        }

        Date now = timeService.getCurrentTime();
        comment.setCreated(now);
        commentDao.create(comment);
        return comment;
    }

    @Override
    public void updateComment(Comment comment)throws IllegalArgumentException {
        commentDao.update(comment);
    }

    @Override
    public void deleteComment(Comment comment) throws IllegalArgumentException {
        commentDao.delete(comment);
    }

    @Override
    public Comment findById(Long id) throws IllegalArgumentException {
        return commentDao.findById(id);
    }

    @Override
    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    @Override
    public List<Comment> findAllWithRideId(Long id) throws IllegalArgumentException {
        return commentDao.getCommentsWithRideId(id);
    }

    @Override
    public List<Comment> findAllWithAuthorId(Long id) throws IllegalArgumentException {
        return commentDao.getCommentsWithUserId(id);
    }
}
