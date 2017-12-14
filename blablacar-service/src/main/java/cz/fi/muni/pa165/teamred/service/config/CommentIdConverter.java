package cz.fi.muni.pa165.teamred.service.config;

import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.service.CommentService;
import org.dozer.DozerConverter;

import javax.inject.Inject;

/**
 * @author Erik Horváth
 */
public class CommentIdConverter extends DozerConverter<Comment, Long> {
    @Inject
    private CommentService commentService;

    public CommentIdConverter() {
        super(Comment.class, Long.class);
    }

    @Override
    public Long convertTo(Comment comment, Long id) {
        return comment == null ? null : comment.getId();
    }

    @Override
    public Comment convertFrom(Long id, Comment comment) {
        return id == null ? null : commentService.findById(id);
    }
}
