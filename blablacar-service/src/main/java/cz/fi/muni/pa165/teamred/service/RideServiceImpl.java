package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.CommentDao;
import cz.fi.muni.pa165.teamred.dao.RideDao;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

// todo
@Service
public class RideServiceImpl implements RideService {

    @Inject
    private RideDao rideDao;


    @Override
    public Ride createRide(Ride ride) throws IllegalArgumentException {
        rideDao.create(ride);
        return ride;
    }

    @Override
    public void updateRide(Ride ride) throws IllegalArgumentException {
        rideDao.update(ride);

    }

    @Override
    public void deleteRide(Ride ride) throws IllegalArgumentException {
        rideDao.delete(ride);

    }

    @Override
    public void addPassenger(Ride ride, User passenger) throws IllegalArgumentException{
        if (ride.getPassengers().contains(passenger)) {
            throw new IllegalArgumentException();
        }
        ride.addPassenger(passenger);

    }

    @Override
    public void addComment(Ride ride, Comment comment) {
        ride.addComment(comment);

    }

    @Override
    public void removePassenger(Ride ride, User passenger) {
        ride.removePassenger(passenger);

    }

    @Override
    public void removeComment(Ride ride, Comment comment) {
        ride.removeComment(comment);

    }

    @Override
    public Ride findById(Long id) throws IllegalArgumentException {
        return rideDao.findById(id);
    }

    @Override
    public List<Ride> findAll() {
        return rideDao.findAll();
    }
}
