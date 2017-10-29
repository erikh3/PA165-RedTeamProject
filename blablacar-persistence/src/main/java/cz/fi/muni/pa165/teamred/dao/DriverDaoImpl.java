package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Driver;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    public void create(Driver driver) {
        em.persist(driver);
    }

    @Override
    @Transactional
    public void delete(Driver driver) {
        em.remove(em.contains(driver) ? driver : em.merge(driver));
    }

    @Override
    @Transactional
    public List<Driver> findAll() {
        return em.createQuery("select d from Driver d", Driver.class).getResultList();
    }

    @Override
    @Transactional
    public Driver findById(Long id) {
        return em.find(Driver.class, id);
    }
}
