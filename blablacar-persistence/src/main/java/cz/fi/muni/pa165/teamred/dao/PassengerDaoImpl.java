package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Ride;
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
    EntityManager entityManager;

    @Override
    public List<Ride> findAllPassengers() {
        return entityManager.createQuery("select p from Passenger p").getResultList();
    }
}
