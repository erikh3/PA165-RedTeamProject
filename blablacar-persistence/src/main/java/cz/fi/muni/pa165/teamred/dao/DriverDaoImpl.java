package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Driver;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Erik Horváth
 */
@Repository
public class DriverDaoImpl implements DriverDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Driver driver) {
        em.persist(driver);
    }

    @Override
    public void delete(Driver driver) {
        em.remove(driver);
    }

    @Override
    public List<Driver> findAll() {
        return em.createQuery("select d from Driver d", Driver.class).getResultList();
    }

    @Override
    public Driver findById(Long id) {
        return em.find(Driver.class, id);
    }
}
