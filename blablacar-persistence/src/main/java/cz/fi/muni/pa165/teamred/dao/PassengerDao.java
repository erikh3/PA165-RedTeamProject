package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Passenger;
import java.util.List;

/**
 * Passenger DAO interface to manage Passenger class operations with database
 *
 * Created by Jozef Cibík on 25.10.2017.
 */
public interface PassengerDao {
    /**
     * Creates {@link cz.fi.muni.pa165.teamred.entity.Passenger} object in database
     * @param passenger ride to be created
     * @exception IllegalArgumentException if passenger is null
     */
    public void create(Passenger passenger) throws IllegalArgumentException;

    /**
     * Deletes {@link cz.fi.muni.pa165.teamred.entity.Passenger} from database
     * @param passenger ride to be deleted
     * @exception IllegalArgumentException if passenger is null
     */
    public void delete(Passenger passenger) throws IllegalArgumentException;

    /**
     * Finds all {@link cz.fi.muni.pa165.teamred.entity.Passenger} objects in database
     * @return list of {@link cz.fi.muni.pa165.teamred.entity.Passenger} objects
     */
    public List<Passenger> findAll();

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Passenger} by its id
     * @param id id
     * @return {@link cz.fi.muni.pa165.teamred.entity.Passenger} 
     * or null if id was not found
     * @exception IllegalArgumentException if is is null
     */
    public Passenger findById(Long id) throws IllegalArgumentException;
}
