package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Place;
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
public class PlaceDaoImpl implements PlaceDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void create(Place place) throws IllegalArgumentException {
        if (place == null) {
            throw new IllegalArgumentException("Place is null.");
        }
        em.persist(place);
    }

    @Override
    @Transactional
    public void delete(Place place) throws IllegalArgumentException {
        if (place == null) {
            throw new IllegalArgumentException("Place is null.");
        }
        em.remove(em.contains(place) ? place : em.merge(place));
    }

    @Override
    @Transactional
    public List<Place> findAll() {
        return em.createQuery("select p from Place p", Place.class).getResultList();
    }

    @Override
    @Transactional
    public Place findById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        }
        return em.find(Place.class, id);
    }

    @Override
    @Transactional
    public Place findByName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name is null.");
        }

        try {
            return em.createQuery("select p from Place p where p.name = :name", Place.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
