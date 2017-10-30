package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Driver;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Erik Horváth
 */
@Repository
public class DriverDaoImpl implements DriverDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void create(Driver driver) throws IllegalArgumentException {
        if(driver == null) {
            throw new IllegalArgumentException("Driver argument is null.");
        }
        em.persist(driver);
    }

    @Override
    public void delete(Driver driver) throws IllegalArgumentException {
        if(driver == null) {
            throw new IllegalArgumentException("Driver argument is null.");
        }
        em.remove(em.contains(driver) ? driver : em.merge(driver));
    }

    @Override
    public List<Driver> findAll() {
        return em.createQuery("select d from Driver d", Driver.class).getResultList();
    }

    @Override
    public Driver findById(Long id) throws IllegalArgumentException {
        if(id == null) {
            throw new IllegalArgumentException("Id argument is null.");
        }
        try {
            return em.find(Driver.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }
}
