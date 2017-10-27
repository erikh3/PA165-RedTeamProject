package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Ride;

import java.util.List;

/**
 * Passenger DAO interface to manage Passanger class operations with database
 *
 * Created by Jozef Cibík on 25.10.2017.
 */
public interface PassengerDao {
    /**
    * Finds all passengers from database;
    */
    public List<Ride> findAllPassengers();
}
