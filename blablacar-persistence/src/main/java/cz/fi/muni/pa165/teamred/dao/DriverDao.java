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
     */
    void create(Driver driver);

    /**
     * Deletes {@link cz.fi.muni.pa165.teamred.entity.Driver} from database
     * @param driver object which will be deleted
     */
    void delete(Driver driver);

    /**
     * Finds all {@link cz.fi.muni.pa165.teamred.entity.Driver} objects in database
     * @return list of {@link cz.fi.muni.pa165.teamred.entity.Driver} objects
     */
    List<Driver> findAll();

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Driver} according by its id
     * @param id unique id
     * @return {@link cz.fi.muni.pa165.teamred.entity.Driver} or null if id was not found
     */
    Driver findById(Long id);
}
