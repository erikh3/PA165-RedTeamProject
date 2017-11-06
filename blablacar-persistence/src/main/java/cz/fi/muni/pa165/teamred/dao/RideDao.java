package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Ride;

import java.util.List;

/**
 * Interface for {@link cz.fi.muni.pa165.teamred.entity.Ride} database access object
 *
 * @author Šimon Mačejovský
 */
public interface RideDao {

    /**
     * Creates {@link cz.fi.muni.pa165.teamred.entity.Ride} object in database
     * @param ride ride to be created
     * @exception IllegalArgumentException if ride is null
     */
    void create(Ride ride) throws IllegalArgumentException;

    /**
     * Updates {@link cz.fi.muni.pa165.teamred.entity.Ride} object in database
     * @param ride object which will be updated
     * @exception IllegalArgumentException if ride is null
     */
    void update(Ride ride) throws IllegalArgumentException;

    /**
     * Deletes {@link cz.fi.muni.pa165.teamred.entity.Ride} from database
     * @param ride ride to be deleted
     * @exception IllegalArgumentException if ride is null
     */
    void delete(Ride ride) throws IllegalArgumentException;

    /**
     * Finds all {@link cz.fi.muni.pa165.teamred.entity.Ride} objects in database
     * @return list of {@link cz.fi.muni.pa165.teamred.entity.Ride} objects
     */
    List<Ride> findAll();

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Ride} by its id
     * @param id id
     * @return {@link cz.fi.muni.pa165.teamred.entity.Ride} or null if id was not found
     * @exception IllegalArgumentException if id is null
     */
    Ride findById(Long id) throws IllegalArgumentException;
}
