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
     * @throws IllegalArgumentException if place is null
     */
    void create(Place place) throws IllegalArgumentException;

    /**
     * Updates {@link cz.fi.muni.pa165.teamred.entity.Place} object in database
     * @param place object which will be updated
     */
    void update(Place place);

    /**
     * Deletes {@link cz.fi.muni.pa165.teamred.entity.Place} from database
     * @param place object which will be deleted
     * @throws IllegalArgumentException if place is null
     */
    void delete(Place place) throws IllegalArgumentException;

    /**
     * Finds all {@link cz.fi.muni.pa165.teamred.entity.Place} objects in database
     * @return list of {@link cz.fi.muni.pa165.teamred.entity.Place} objects
     */
    List<Place> findAll();

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Place} by its id
     * @param id unique id
     * @throws IllegalArgumentException if id is null
     * @return {@link cz.fi.muni.pa165.teamred.entity.Place} or null if id was not found
     */
    Place findById(Long id) throws IllegalArgumentException;

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Place} by its name
     * @param name attribute of the entity
     * @throws IllegalArgumentException if name is null
     * @return {@link cz.fi.muni.pa165.teamred.entity.Place} or null if entity with name was not found
     */
    Place findByName(String name) throws IllegalArgumentException;
}
