package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Ride;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Šimon on 26.10.2017.
 */
@Repository
public class RideDaoImpl implements RideDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Ride r) {
        em.persist(r);
    }

    @Override
    public void delete(Ride r) {
        em.remove(r);
    }

    @Override
    public List<Ride> findAll() {
        return em.createQuery("select r from Ride r", Ride.class).getResultList();
    }

    @Override
    public Ride findById(Long id) {
        return em.find(Ride.class, id);
    }
}
