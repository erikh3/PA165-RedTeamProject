package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.CommentDao;
import cz.fi.muni.pa165.teamred.dao.RideDao;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
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
    public Ride findById(Long id) throws IllegalArgumentException {
        return rideDao.findById(id);
    }

    @Override
    public List<Ride> findAll() {
        return rideDao.findAll();
    }
}
