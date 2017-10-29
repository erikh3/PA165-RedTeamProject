package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Comment;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of CommentDao interface
 * 
 * @author miroslav.laco@gmail.com
 */
@Repository
public class CommentDaoImpl implements CommentDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    @Transactional
    public void create(Comment comment) {
        em.persist(comment);
    }

    @Override
    @Transactional
    public void delete(Comment comment) {
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }

    @Override
    @Transactional
    public List<Comment> findAll() {
        return em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
    }

    @Override
    @Transactional
    public List<Comment> getCommentsWithRideId(Long id) {
        try {
            return em.createQuery("SELECT c FROM Comment c WHERE c.ride.id = :id",
                                Comment.class).setParameter("id", id)
                                .getResultList();
        } catch (NoResultException nrf) {
                return null;
        }
    }

    @Override
    @Transactional
    public List<Comment> getCommentsWithUserId(Long id) {
        try {
            return em.createQuery("SELECT c FROM Comment c WHERE c.user.id = :id",
                                Comment.class).setParameter("id", id)
                                .getResultList();
        } catch (NoResultException nrf) {
                return null;
        }
    }
}
