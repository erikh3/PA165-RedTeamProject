package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Driver;

import java.util.List;

/**
 * Interface for {@link cz.fi.muni.pa165.teamred.entity.Driver} database access object
 *
 * @author Erik Horváth
 */
public interface DriverDao {
    /**
     * Creates {@link cz.fi.muni.pa165.teamred.entity.Driver} object in database
     * @param driver object which will be created
     * @throws IllegalArgumentException if driver is null
     */
    void create(Driver driver) throws IllegalArgumentException;

    /**
     * Updates {@link cz.fi.muni.pa165.teamred.entity.Driver} object in database
     * @param driver object which will be updated
     */
    void update(Driver driver);

    /**
     * Deletes {@link cz.fi.muni.pa165.teamred.entity.Driver} from database
     * @param driver object which will be deleted
     * @throws IllegalArgumentException if driver is null
     */
    void delete(Driver driver) throws IllegalArgumentException;

    /**
     * Finds all {@link cz.fi.muni.pa165.teamred.entity.Driver} objects in database
     * @return list of {@link cz.fi.muni.pa165.teamred.entity.Driver} objects
     */
    List<Driver> findAll();

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Driver} according by its id
     * @param id unique id
     * @throws IllegalArgumentException if id is null
     * @return {@link cz.fi.muni.pa165.teamred.entity.Driver} or null if id was not found
     */
    Driver findById(Long id) throws IllegalArgumentException;
}
