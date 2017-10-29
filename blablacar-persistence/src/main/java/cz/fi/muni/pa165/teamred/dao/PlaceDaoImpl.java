package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Place;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Erik Horváth
 */
@Repository
public class PlaceDaoImpl implements PlaceDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Place place) {
        em.persist(place);
    }

    @Override
    public void delete(Place place) {
        em.remove(place);
    }

    @Override
    public List<Place> findAll() {
        return em.createQuery("select p from Place p", Place.class).getResultList();
    }

    @Override
    public Place findById(Long id) {
        return em.find(Place.class, id);
    }

    @Override
    public Place findByName(String name) {
        try {
            return em.createQuery("select p from Place p where p.name = :name", Place.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
