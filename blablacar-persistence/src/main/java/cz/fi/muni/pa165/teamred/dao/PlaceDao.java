package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Place;

import java.util.List;

/**
 * Interface for {@link cz.fi.muni.pa165.teamred.entity.Place} database access object
 *
 * @author Erik Horváth
 */
public interface PlaceDao {
    /**
     * Creates {@link cz.fi.muni.pa165.teamred.entity.Place} object in database
     * @param place object which will be created
     */
    void create(Place place);

    /**
     * Deletes {@link cz.fi.muni.pa165.teamred.entity.Place} from database
     * @param place object which will be deleted
     */
    void delete(Place place);

    /**
     * Finds all {@link cz.fi.muni.pa165.teamred.entity.Place} objects in database
     * @return list of {@link cz.fi.muni.pa165.teamred.entity.Place} objects
     */
    List<Place> findAll();

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Place} by its id
     * @param id unique id
     * @return {@link cz.fi.muni.pa165.teamred.entity.Place} or null if id was not found
     */
    Place findById(Long id);

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Place} by its name
     * @param name attribute of the entity
     * @return {@link cz.fi.muni.pa165.teamred.entity.Place} or null if entity with name was not found
     */
    Place findByName(String name);
}
