package cz.fi.muni.pa165.teamred.facade;

import cz.fi.muni.pa165.teamred.dto.RideCreateDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;

import java.util.Date;
import java.util.List;

/**
 * Facade interface for operations over Rides
 *
 * @author Šimon Mačejovský
 */
public interface RideFacade {


    /**
     * Creates ride.
     *
     * @param rideCreateDTO parameters will be used in creation of ride
     * @return Long - Id of created ride
     */
    Long createRide(RideCreateDTO rideCreateDTO);

    /**
     * Deletes ride.
     *
     * @param rideId id of ride which will be deleted
     */
    void deleteRide(Long rideId);

    /**
     * Adds passanger to ride.
     *
     * @param rideId id of ride
     * @param userId id of user which will be added to ride as passanger
     */
    void addPassenger(Long rideId, Long userId);

    /**
     * Removes passenger from ride.
     *
     * @param rideId id of ride
     * @param userId id passenger which will be removed from ride
     */
    void removePassenger(Long rideId, Long userId);

    /**
     * Removes comment from ride.
     *
     * @param rideId id of ride
     * @param commentId id of comment which will be removed from ride
     */
    void removeComment(Long rideId, Long commentId);


    /**
     * Retrieves ride with specific id.
     *
     * @param rideId id of ride which will be retrieved
     * @return founded ride or nullif not found
     */
    RideDTO getRideWithId(Long rideId);

    /**
     * Retrieves all rides.
     *
     * @return list of all rides
     */
    List<RideDTO> getAllRides();

    /**
     *  Changes seat price for existing ride.
     *
     * @param rideId id of ride
     * @param newPrice new price
     */
    void changePrice(Long rideId, double newPrice);

    /**
     * Edits available seats for existing ride.
     *
     * @param rideId id of ride
     * @param availableSeats new number of seats
     */
    void editAvailableSeats(Long rideId, int availableSeats);

    /**
     * Sets new departure for existing ride.
     *
     * @param rideId id of ride
     * @param newDeparture new departure to set
     */
    void editDeparture(Long rideId, Date newDeparture);

}
