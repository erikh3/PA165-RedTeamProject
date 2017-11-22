package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.RideCreateDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.entity.Ride;
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
        Ride ride = rideService.createRide(mappedRide);
        return  ride.getId();
    }

    @Override
    public void updateRide(RideDTO rideDto) {
        throw new NotImplementedException();

    }

    @Override
    public void deleteRide(Long rideId) {
        Ride ride = new Ride();
        ride.setId(rideId);
        rideService.deleteRide(ride);

    }

    @Override
    public void addPassenger(Long rideId, Long userId) {
        rideService.addPassenger(rideService.findById(rideId),
                userService.findUserById(userId));


    }

    @Override
    public void addComment(Long rideId, Long commentId) {
        rideService.addComment(rideService.findById(rideId),
                commentService.findById(commentId));

    }

    @Override
    public void removePassenger(Long rideId, Long userId) {
        rideService.removePassenger(rideService.findById(rideId),
                userService.findUserById(userId));

    }

    @Override
    public void removeComment(Long rideId, Long commentId) {
        rideService.removeComment(rideService.findById(rideId),
                commentService.findById(commentId));

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
}
