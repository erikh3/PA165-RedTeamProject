package cz.fi.muni.pa165.teamred.service;


import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Ride entity
 *
 * @author Šimon Mačejovský
 */
@Service
public interface RideService {

    /**
     * Creates {@link cz.fi.muni.pa165.teamred.entity.Ride} in app.
     *
     * @param ride {@link cz.fi.muni.pa165.teamred.entity.Ride} to be created
     * @return created Ride
     * @throws IllegalArgumentException if ride param is null
     */
    Ride createRide(Ride ride) throws IllegalArgumentException;

    /**
     * Updates {@link cz.fi.muni.pa165.teamred.entity.Ride) in app.
     *
     * @param ride {@link cz.fi.muni.pa165.teamred.entity.Ride) to be updated
     * @throws IllegalArgumentException if ride param is null
     */
    void updateRide(Ride ride) throws IllegalArgumentException;

    /**
     * Delete {@link cz.fi.muni.pa165.teamred.entity.Ride) in app.
     *
     * @param ride {@link cz.fi.muni.pa165.teamred.entity.Ride) to be deleted
     * @throws IllegalArgumentException if ride param is null
     */
    void deleteRide(Ride ride) throws IllegalArgumentException;

    /**
     * Adds {@link cz.fi.muni.pa165.teamred.entity.User) as passanger to {@link cz.fi.muni.pa165.teamred.entity.Ride).
     *
     * @param ride {@link cz.fi.muni.pa165.teamred.entity.Ride)
     * @param passenger {@link cz.fi.muni.pa165.teamred.entity.User) user to be added as passanger
     * @throws IllegalArgumentException if ride or passanger is null
     */
    void addPassenger(Ride ride, User passenger) throws IllegalArgumentException;

    /**
     * adds {@link cz.fi.muni.pa165.teamred.entity.Comment) to {@link cz.fi.muni.pa165.teamred.entity.Ride) in app.
     *
     * @param ride {@link cz.fi.muni.pa165.teamred.entity.Ride)
     * @param comment {@link cz.fi.muni.pa165.teamred.entity.Comment) to be added to ride
     */
    void addComment(Ride ride, Comment comment);

    /**
     * Removes {@link cz.fi.muni.pa165.teamred.entity.User) as passanger
     * from {@link cz.fi.muni.pa165.teamred.entity.Ride).
     *
     * @param ride {@link cz.fi.muni.pa165.teamred.entity.Ride)
     * @param passenger {@link cz.fi.muni.pa165.teamred.entity.User) to be removed from ride
     */
    void removePassenger(Ride ride, User passenger);

    /**
     * Removes {@link cz.fi.muni.pa165.teamred.entity.Comment) from {@link cz.fi.muni.pa165.teamred.entity.Ride) in app
     *
     * @param ride {@link cz.fi.muni.pa165.teamred.entity.Ride)
     * @param comment {@link cz.fi.muni.pa165.teamred.entity.Comment) to be removed from ride
     */
    void removeComment(Ride ride, Comment comment);

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Ride)in app by unique id.
     *
     * @param id id of {@link cz.fi.muni.pa165.teamred.entity.Ride) to be found
     * @return {@link cz.fi.muni.pa165.teamred.entity.Ride) with provided id, null if not found
     * @throws IllegalArgumentException if id param is null
     */
    Ride findById(Long id) throws IllegalArgumentException;

    /**
     * Finds all {@link cz.fi.muni.pa165.teamred.entity.Ride) in app.
     *
     * @return List of all {@link cz.fi.muni.pa165.teamred.entity.Ride) objects stored
     */
    List<Ride> findAll();
}
