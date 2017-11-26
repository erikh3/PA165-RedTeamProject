package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.RideDao;
import cz.fi.muni.pa165.teamred.dao.UserDao;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by jcibik on 11/24/17.
 */
@Service
public class PassengerServiceImpl implements PassengerService {

    private final static Logger log = LoggerFactory.getLogger(PassengerService.class);

    @Inject
    private UserDao userDao;

    @Inject
    private RideDao rideDao;


    @Override
    public void addPassengerToRide(Long userId, Long rideId) {
        Ride ride = rideDao.findById(rideId);

        if (ride == null){
            throw new IllegalArgumentException("Ride was not found with id: " + rideId);
        }

        int seats = ride.getAvailableSeats();
        if (seats == 0) {
            log.debug("Ride with id: " + rideId + " is full.");
            return;
        }

        User user = userDao.findById(userId);

        if (user == null){
            throw new IllegalArgumentException("User was not found with id:" + userId);
        }

        if (ride.getPassengers().contains(user) && user.getRidesAsPassenger().contains(ride)){
            log.debug("User with id: " + userId +
            " is already in ride with id: " + rideId);
            return;
        }

        if (ride.getPassengers().contains(user) || user.getRidesAsPassenger().contains(ride)){
            throw new IllegalStateException("Bidirectional reference was set only on one side in ride:" + rideId + " and user: " + userId);
        }

        user.addRideAsPassenger(ride);
        ride.addPassenger(user);

        seats--;

        ride.setAvailableSeats(seats);

        userDao.update(user);
        rideDao.update(ride);

        log.debug("Added user with id: " + userId +
                    " in ride with id: " + rideId);
    }

    @Override
    public void removePassengerFromRide(Long userId, Long rideId) {
        Ride ride = rideDao.findById(rideId);
        if (ride == null){
            throw new IllegalArgumentException("Ride was not found with id: " + rideId);
        }

        int seats = ride.getAvailableSeats();

        User user = userDao.findById(userId);
        if (user == null){
            throw new IllegalArgumentException("User was not found with id:" + userId);
        }

        if (!ride.getPassengers().contains(user)){
            log.debug("User with id: " + userId +
                    " is not in ride with id: " + rideId);
            return;
        }

        user.removeRideAsPassenger(ride);
        ride.removePassenger(user);

        seats++;

        ride.setAvailableSeats(seats);

        userDao.update(user);
        rideDao.update(ride);

        log.debug("Removed user with id: " + userId +
                " from ride with id: " + rideId);
    }
}
