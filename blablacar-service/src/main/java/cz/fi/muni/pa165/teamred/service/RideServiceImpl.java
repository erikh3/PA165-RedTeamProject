package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.CommentDao;
import cz.fi.muni.pa165.teamred.dao.RideDao;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Šimon Mačejovský
 */
@Service
public class RideServiceImpl implements RideService {

    final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private RideDao rideDao;


    @Override
    public Ride createRide(Ride ride) throws IllegalArgumentException {
        if (!isValidRide(ride)){
            throw new IllegalArgumentException();
        }


        rideDao.create(ride);
        log.debug("Service: " + RideServiceImpl.class + "created Ride: " + ride.toString());
        return ride;
    }

    @Override
    public void updateRide(Ride ride) throws IllegalArgumentException {
        if (ride == null){
            throw new IllegalArgumentException();
        }

        rideDao.update(ride);
        log.debug("Service: " + RideServiceImpl.class + "updated Ride: " + ride.toString());

    }

    @Override
    public void deleteRide(Ride ride) throws IllegalArgumentException {
        if (!isValidRide(ride)){
            throw new IllegalArgumentException();
        }

        rideDao.delete(ride);
        log.debug("Service: " + RideServiceImpl.class + "deleted Ride: " + ride.toString());
    }


    @Override
    public void addPassenger(Ride ride, User passenger) throws IllegalArgumentException{
        if (!isValidRide(ride)){
            throw new IllegalArgumentException();
        }

        if (!isValidUser(passenger)){
            throw new IllegalArgumentException();
        }

        if (ride.getPassengers().contains(passenger)) {
            throw new IllegalArgumentException();
        }
        ride.addPassenger(passenger);
        log.debug("Service: " + RideServiceImpl.class + "added passenger to ride: " + passenger.toString()
                + " to Ride: "+ ride.toString());

    }

    @Override
    public void addComment(Ride ride, Comment comment) {
        if (!isValidRide(ride)){
            throw new IllegalArgumentException();
        }

        if (!isValidComment(comment)){
            throw new IllegalArgumentException();
        }

        if (ride.getComments().contains(comment)){
            throw new IllegalArgumentException();
        }

        ride.addComment(comment);
        log.debug("Service: " + RideServiceImpl.class + "added comment to ride: " + comment.toString()
                + " to Ride: "+ ride.toString());
    }

    @Override
    public void removePassenger(Ride ride, User passenger) {
        if (!isValidRide(ride)){
            throw new IllegalArgumentException();
        }

        if (!isValidUser(passenger)){
            throw new IllegalArgumentException();
        }

        ride.removePassenger(passenger);
        log.debug("Service: " + RideServiceImpl.class + "removed passenger from ride: " + passenger.toString()
                + " from Ride: "+ ride.toString());
    }

    @Override
    public void removeComment(Ride ride, Comment comment) {
        if (!isValidRide(ride)){
            throw new IllegalArgumentException();
        }

        if (!isValidComment(comment)){
            throw new IllegalArgumentException();
        }

        ride.removeComment(comment);
        log.debug("Service: " + RideServiceImpl.class + "removed comment from ride: " + comment.toString()
                + " from Ride: "+ ride.toString());
    }

    @Override
    public Ride findById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Passed id paramenter is null");
        }

        Ride found = rideDao.findById(id);
        log.debug("Service: " + RideServiceImpl.class + "found Ride: " + found.toString());
        return found;

    }

    @Override
    public List<Ride> findAll() {
        List<Ride> found = rideDao.findAll();
        log.debug("Service: " +
                RideServiceImpl.class + "found: " + found.size() + " rides.");
        return found;
    }

    private boolean isValidComment(Comment comment){
        if (comment == null){
            return false;
        }
/*
        if (comment.getAuthor() != null){
            return false;
        }
        if (comment.getText() == null){
            return false;
        }
        if (comment.getText().length() == 0){
            return false;
        }
*/
        return true;

    }

    private boolean isValidRide(Ride ride){
        if (ride == null){
            return false;
        }

        if (ride.getDestinationPlace() == null) {
            return false;
        }
        if (ride.getSourcePlace() == null){
            return false;
        }
        //<0 or <= 0 ?
        if (ride.getSeatPrice() <= 0){
            return false;
        }
        if (ride.getAvailableSeats() < 0){
            return false;
        }
        //Check ride departurte time if valid

        return true;
    }

    private boolean isValidUser(User user) {
        if (user == null) {
            return false;
        }
        /*
        String nickname = user.getNickname();
        if (nickname == null || nickname.length() == 0) {
            return false;
        }
        */
        String name = user.getName();
        if (name == null || name.length() == 0) {
            return false;
        }
        /*
        String surname = user.getSurname();
        if (surname == null || surname.length() == 0) {
            return false;
        }
        */
        /*Checking birthdate with actual time check*/

        return true;
    }
}
