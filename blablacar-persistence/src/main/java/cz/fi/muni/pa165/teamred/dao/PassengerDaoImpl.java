package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Passenger;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Jozef Cibík on 25.10.2017.
 */
@Repository
public class PassengerDaoImpl implements PassengerDao {

    @PersistenceContext
    EntityManager em;
    
    /**
     * Sets entity manager
     *
     * @param entityManager entity manager
     */
    void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Passenger findById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id argument is null.");
        }
        return em.find(Passenger.class, id);
    }

    @Override
    public void create(Passenger passenger) {
        if(passenger == null) {
            throw new IllegalArgumentException("Passenger argument is null.");
        }
        em.persist(passenger);
    }

    @Override
    public void update(Passenger passenger) {
        em.merge(passenger);
    }

    @Override
    public void delete(Passenger passenger) {
        if(passenger == null) {
            throw new IllegalArgumentException("Passenger argument is null.");
        }             
        em.remove(em.contains(passenger) ? passenger : em.merge(passenger));
    }

    @Override
    public List<Passenger> findAll() {
        return em.createQuery("SELECT p FROM Passenger p", Passenger.class).getResultList();
    }
}
