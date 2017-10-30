package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Ride;

import java.util.List;

/**
 * Interface for {@link cz.fi.muni.pa165.teamred.entity.Ride} database access object
 *
 * Created by Šimon on 26.10.2017.
 */
public interface RideDao {

    /**
     * Creates {@link cz.fi.muni.pa165.teamred.entity.Ride} object in database
     * @param r ride to be created
     * @exception IllegalArgumentException if ride is null
     */
    public void create(Ride ride) throws IllegalArgumentException;


    /**
     * Deletes {@link cz.fi.muni.pa165.teamred.entity.Ride} from database
     * @param r ride to be deleted
     * @exception IllegalArgumentException if ride is null
     */
    public void delete(Ride ride) throws IllegalArgumentException;

    /**
     * Finds all {@link cz.fi.muni.pa165.teamred.entity.Ride} objects in database
     * @return list of {@link cz.fi.muni.pa165.teamred.entity.Ride} objects
     */
    public List<Ride> findAll();

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Ride} by its id
     * @param id id
     * @return {@link cz.fi.muni.pa165.teamred.entity.Ride} or null if id was not found
     * @exception IllegalArgumentException if id is null
     */
    public Ride findById(Long id) throws IllegalArgumentException;
}
