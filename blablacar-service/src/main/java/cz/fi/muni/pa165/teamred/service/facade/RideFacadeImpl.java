package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.AddPassengerDTO;
import cz.fi.muni.pa165.teamred.dto.RemovePassengerDTO;
import cz.fi.muni.pa165.teamred.dto.RideCreateDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.facade.RideFacade;
import cz.fi.muni.pa165.teamred.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
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
    private PlaceService placeService;

    @Inject
    private PassengerService passengerService;

    @Autowired
    private BeanMappingService beanMappingService;


    @Override
    public Long createRide(RideCreateDTO rideCreateDTO) {
        Ride mappedRide = beanMappingService.mapTo(rideCreateDTO, Ride.class);

//        User mappedUser = beanMappingService.mapTo(userDTO, User.class);

        User user = userService.findUserById(rideCreateDTO.getDriverId());
        mappedRide.setDriver(user);
        user.addRideAsDriver(mappedRide);

        Place sourcePlace = placeService.findById(rideCreateDTO.getSourcePlaceId());
        mappedRide.setSourcePlace(sourcePlace);
        sourcePlace.addOriginatingRide(mappedRide);

        Place destinationPlace = placeService.findById(rideCreateDTO.getDestinationPlaceId());
        mappedRide.setDestinationPlace(destinationPlace);
        destinationPlace.addDestinationRide(mappedRide);

        mappedRide.setAvailableSeats(rideCreateDTO.getSeatsAvailable());
        mappedRide.setSeatPrice(rideCreateDTO.getSeatPrize());

        Ride ride = rideService.createRide(mappedRide);
        log.debug("Created new Ride: " + ride.toString());
        return  ride.getId();
    }

    @Override
    public void deleteRide(Long rideId) {
        Ride ride = new Ride();
        ride.setId(rideId);
        rideService.deleteRide(ride);
    }

    @Override
    public RideDTO getRideWithId(Long rideId) {
        Ride ride = rideService.findById(rideId);
        log.debug("Found Ride in " + RideFacadeImpl.class + "with paramenters" + ride.toString());
        return (ride == null) ? null : beanMappingService.mapTo(ride, RideDTO.class);
    }

    @Override
    public List<RideDTO> getAllRides() {
        ArrayList<Ride> rides = (ArrayList<Ride>) rideService.findAll();
        log.debug("Found " + rides.size() + " Rides in " + RideFacadeImpl.class);
        return (rides.size() == 0) ? new ArrayList<>() : beanMappingService.mapTo(rides, RideDTO.class);
    }

    @Override
    public void changePrice(Long rideId, double newPrice) {
        Ride ride = rideService.findById(rideId);
        ride.setSeatPrice(newPrice);
        rideService.updateRide(ride);
        log.debug("Updated price in Ride: " + ride.toString());
    }

    @Override
    public void editAvailableSeats(Long rideId, int availableSeats) {
        Ride ride = rideService.findById(rideId);
        ride.setAvailableSeats(availableSeats);
        rideService.updateRide(ride);
        log.debug("Updated available seats in Ride: " + ride.toString());
    }

    @Override
    public void editDeparture(Long rideId, Date newDeparture) {
        Ride ride = rideService.findById(rideId);
        ride.setDeparture(newDeparture);
        rideService.updateRide(ride);
        log.debug("Updated departure in Ride: " + ride.toString());
    }

    @Override
    public void addPassenger(AddPassengerDTO addPassengerDTO) {
        Ride ride = rideService.findById(addPassengerDTO.getRideId());
        User user = userService.findUserById(addPassengerDTO.getPassengerId());
        if (!ride.getPassengers().contains(user)) {
            passengerService.addPassengerToRide(user.getId(),ride.getId());
        }
    }

    @Override
    public void removePassenger(RemovePassengerDTO removePassengerDTO) {
        Ride ride = rideService.findById(removePassengerDTO.getRideId());
        User user = userService.findUserById(removePassengerDTO.getPassengerId());
        if (ride.getPassengers().contains(user)) {
            passengerService.removePassengerFromRide(user.getId(),ride.getId());
        }
    }

}
