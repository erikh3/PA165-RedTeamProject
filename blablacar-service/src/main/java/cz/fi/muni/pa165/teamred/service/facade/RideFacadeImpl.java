package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.RideCreateDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.facade.RideFacade;
import cz.fi.muni.pa165.teamred.service.BeanMappingService;
import cz.fi.muni.pa165.teamred.service.CommentService;
import cz.fi.muni.pa165.teamred.service.RideService;
import cz.fi.muni.pa165.teamred.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author Šimon Mačejovský
 */
@Service
@Transactional
public class RideFacadeImpl implements RideFacade {
    final static Logger log = LoggerFactory.getLogger(RideFacadeImpl.class);

    @Inject
    private RideService rideService;

    @Inject
    private UserService userService;

    @Inject
    private CommentService commentService;

    @Autowired
    private BeanMappingService beanMappingService;


    @Override
    public Long createRide(RideCreateDTO rideCreateDTO) {
        Ride mappedRide = beanMappingService.mapTo(rideCreateDTO, Ride.class);

        User user = userService.findUserById(rideCreateDTO.getDriver().getId());
        mappedRide.setDriver(user);
        user.addRideAsDriver(mappedRide);

        Ride ride = rideService.createRide(mappedRide);
        return  ride.getId();
    }

    @Override
    public void deleteRide(Long rideId) {
        Ride ride = new Ride();
        ride.setId(rideId);
        rideService.deleteRide(ride);

    }

    @Override
    public void addPassenger(Long rideId, Long userId) {
        Ride ride = rideService.findById(rideId);
        if (ride == null){
            log.info("No ride found with id: " + rideId);
            return;
        }

        User user = userService.findUserById(userId);
        if (user == null){
            log.info("No user found with id: " + userId);
            return;
        }

        int seats = ride.getAvailableSeats();
        if (seats == 0){
            log.info("No available seats in ride" + rideId);
            return;
        } else{
            rideService.addPassenger(ride, user);
            userService.addUserRideAsPassenger(user, ride);
            this.editAvailableSeats(rideId, seats-1);
        }
    }

    @Override
    public void removePassenger(Long rideId, Long userId) {
        Ride ride = rideService.findById(rideId);
        if (ride == null){
            log.info("No ride found with id: " + rideId);
            return;
        }

        User user = userService.findUserById(userId);
        if (user == null){
            log.info("No user found with id: " + userId);
            return;
        }

        int seats = ride.getAvailableSeats();

        rideService.removePassenger(ride, user);
        userService.removeUserRideAsPassenger(user, ride);
        this.editAvailableSeats(rideId, seats+1);

    }

    @Override
    public void removeComment(Long rideId, Long commentId) {
        Ride ride = rideService.findById(rideId);
        if (ride == null){
            log.info("No ride found with id: " + rideId);
            return;
        }

        Comment comment = commentService.findById(commentId);
        if (comment == null){
            log.info("No comment found with id: " + commentId);
            return;
        }

        User user = comment.getAuthor();
        rideService.removeComment(ride,comment);
        userService.removeUserComment(user, comment);
        commentService.deleteComment(comment);

    }

    @Override
    public RideDTO getRideWithId(Long rideId) {
        Ride ride = rideService.findById(rideId);
        return (ride == null) ? null : beanMappingService.mapTo(ride, RideDTO.class);
    }

    @Override
    public List<RideDTO> getAllRides() {
        return beanMappingService.mapTo(rideService.findAll(), RideDTO.class);
    }

    @Override
    public void changePrice(Long rideId, double newPrice) {
        Ride ride = rideService.findById(rideId);
        ride.setSeatPrice(newPrice);
        rideService.updateRide(ride);
    }

    @Override
    public void editAvailableSeats(Long rideId, int availableSeats) {
        Ride ride = rideService.findById(rideId);
        ride.setAvailableSeats(availableSeats);
        rideService.updateRide(ride);
    }

    @Override
    public void editDeparture(Long rideId, Date newDeparture) {
        Ride ride = rideService.findById(rideId);
        ride.setDeparture(newDeparture);
        rideService.updateRide(ride);
    }
}
