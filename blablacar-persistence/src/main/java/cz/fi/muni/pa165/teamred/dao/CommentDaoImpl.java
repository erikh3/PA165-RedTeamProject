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

    /**
     * Sets entity manager
     *
     * @param entityManager entity manager
     */
    void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }


    @Override
    public void create(Comment comment) {
        if(comment == null) {
            throw new IllegalArgumentException("Comment argument is null.");
        }
        em.persist(comment);
    }

    @Override
    public void update(Comment comment) {
        if(comment == null) {
            throw new IllegalArgumentException("Comment argument is null.");
        }
        em.merge(comment);
    }

    @Override
    public void delete(Comment comment) {
        if(comment == null) {
            throw new IllegalArgumentException("Comment argument is null.");
        }             
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }

    @Override
    public Comment findById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Comment id argument is null.");
        }
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
    }

    @Override
    public List<Comment> getCommentsWithRideId(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Comment id argument is null.");
        }
        try {
            return em.createQuery("SELECT c FROM Comment c WHERE c.ride.id = :id ORDER BY c.created",
                                Comment.class).setParameter("id", id)
                                .getResultList();
        } catch (NoResultException nrf) {
                return null;
        }
    }

    @Override
    public List<Comment> getCommentsWithUserId(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Comment id argument is null.");
        }
        try {
            return em.createQuery("SELECT c FROM Comment c WHERE c.author.id = :id ORDER BY c.created",
                                Comment.class).setParameter("id", id)
                                .getResultList();
        } catch (NoResultException nrf) {
                return null;
        }
    }
}
