package cz.fi.muni.pa165.teamred.rest.controllers;

import cz.fi.muni.pa165.teamred.dto.*;
import cz.fi.muni.pa165.teamred.facade.RideFacade;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import cz.fi.muni.pa165.teamred.rest.ApiUris;
import cz.fi.muni.pa165.teamred.rest.exceptions.ResourceAlreadyExistException;
import cz.fi.muni.pa165.teamred.rest.exceptions.ResourceCannotBeDeletedException;
import cz.fi.muni.pa165.teamred.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Šimon Mačejovský
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_RIDES)
public class RidesController {
    final static Logger logger = LoggerFactory.getLogger(RidesController.class);

    @Inject
    private RideFacade rideFacade;

    @Inject
    private UserFacade userFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<RideDTO> getRides() {
        logger.debug("rest getRides()");

        return rideFacade.getAllRides();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final RideDTO getRide(@PathVariable long id) throws ResourceNotFoundException {
        logger.debug("rest getRide({})", id);

        RideDTO rideDTO = rideFacade.getRideWithId(id);

        if (rideDTO == null) {
            throw new ResourceNotFoundException();
        }

        return rideDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteRide(@PathVariable Long id) throws ResourceNotFoundException, ResourceCannotBeDeletedException {
        logger.debug("rest deleteRide({})", id);

        try {
            rideFacade.deleteRide(id);
        } catch (JpaSystemException|TransactionSystemException e) {
            throw new ResourceCannotBeDeletedException();
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RideDTO createRide(@RequestBody RideCreateDTO rideCreateDTO) throws ResourceAlreadyExistException {
        logger.debug("rest createRide()");

        try {
            Long id = rideFacade.createRide(rideCreateDTO);
            return rideFacade.getRideWithId(id);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResourceAlreadyExistException();
        }
    }

    @RequestMapping(value = "/by-driver/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<RideDTO> getRidesWithDriver(@PathVariable Long id) {
        logger.debug("rest getRidesWithDriver({})", id);

        return userFacade.getUserRidesAsDriver(id);
    }

    @RequestMapping(value = "/by-passenger/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<RideDTO> getRidesWithPassenger(@PathVariable Long id) {
        logger.debug("rest getRidesWithPassenger({})", id);

        return userFacade.getUserRidesAsPassenger(id);
    }

    @RequestMapping(value = "/add-passenger", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RideDTO addPassenger(@RequestBody AddPassengerDTO addPassengerDTO) throws ResourceNotFoundException {
        logger.debug("rest addPassenger(rideId={}, userId={})", addPassengerDTO.getRideId(), addPassengerDTO.getPassengerId());

        try {
            rideFacade.addPassenger(addPassengerDTO);
            return rideFacade.getRideWithId(addPassengerDTO.getRideId());
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/remove-passenger", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RideDTO addPassenger(@RequestBody RemovePassengerDTO removePassengerDTO) throws ResourceNotFoundException {
        logger.debug("rest removePassenger(rideId={}, userId={})", removePassengerDTO.getRideId(), removePassengerDTO.getPassengerId());

        try {
            rideFacade.removePassenger(removePassengerDTO);
            return rideFacade.getRideWithId(removePassengerDTO.getRideId());
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/change-price", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RideDTO changePrice(@RequestBody ChangeRidePriceDTO changeRidePriceDTO) throws ResourceNotFoundException {
        logger.debug("rest changePrice(rideId={}, price={})", changeRidePriceDTO.getRideId(), changeRidePriceDTO.getPrice());

        try {
            rideFacade.changePrice(changeRidePriceDTO.getRideId(), changeRidePriceDTO.getPrice());
            return rideFacade.getRideWithId(changeRidePriceDTO.getRideId());
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/change-seats", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RideDTO changePrice(@RequestBody ChangeRideSeatsDTO changeRideSeatsDTO) throws ResourceNotFoundException {
        logger.debug("rest changePrice(rideId={}, seats={})", changeRideSeatsDTO.getRideId(), changeRideSeatsDTO.getSeats());

        try {
            rideFacade.editAvailableSeats(changeRideSeatsDTO.getRideId(), changeRideSeatsDTO.getSeats());
            return rideFacade.getRideWithId(changeRideSeatsDTO.getRideId());
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/change-departure", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RideDTO changeDeparture(@RequestBody ChangeRideDepartureDTO changeRideDepartureDTO) throws ResourceNotFoundException {
        logger.debug("rest changeDeparture(rideId={}, departure={})", changeRideDepartureDTO.getRideId(), changeRideDepartureDTO.getDeparture());

        try {
            rideFacade.editDeparture(changeRideDepartureDTO.getRideId(), changeRideDepartureDTO.getDeparture());
            return rideFacade.getRideWithId(changeRideDepartureDTO.getRideId());
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
