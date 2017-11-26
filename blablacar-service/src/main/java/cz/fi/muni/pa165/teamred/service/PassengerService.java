package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;

/**
 * Created by jcibik on 11/24/17.
 */
public interface PassengerService {

    /**
     * Add a ride as passenger to a user.
     * @param userId User to add a ride to
     * @param rideId Ride to be added to user
     */
    void addPassengerToRide(Long userId, Long rideId);

    /**
     * Add a ride as driver to a user
     * @param userId User to add a ride to
     * @param rideId Ride to be added to user
     */
    void removePassengerFromRide(Long userId, Long rideId);
}
