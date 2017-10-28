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
     */
    public void create(Ride r);


    /**
     * Deletes {@link cz.fi.muni.pa165.teamred.entity.Ride} from database
     * @param r ride to be deleted
     */
    public void delete(Ride r);

    /**
     * Finds all {@link cz.fi.muni.pa165.teamred.entity.Ride} objects in database
     * @return list of {@link cz.fi.muni.pa165.teamred.entity.Ride} objects
     */
    public List<Ride> findAll();

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Ride} by its id
     * @param id id
     * @return {@link cz.fi.muni.pa165.teamred.entity.Ride} or null if id was not found
     */
    public Ride findById(Long id);
}
