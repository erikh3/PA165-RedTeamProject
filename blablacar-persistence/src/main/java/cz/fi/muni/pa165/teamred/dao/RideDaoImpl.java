package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Ride;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Šimon on 26.10.2017.
 */
@Repository
public class RideDaoImpl implements RideDao {

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
    @Transactional
    public void create(Ride r) {
        if (r == null) {
            throw new IllegalArgumentException("Ride is null.");
        }
        em.persist(r);
    }

    @Override
    public void delete(Ride r) {
        if (r == null) {
            throw new IllegalArgumentException("Ride is null.");
        }
        em.remove(em.contains(r) ? r : em.merge(r));
    }

    @Override
    public List<Ride> findAll() {
        return em.createQuery("select r from Ride r", Ride.class).getResultList();
    }

    @Override
    public Ride findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        }
        return em.find(Ride.class, id);
    }
}
