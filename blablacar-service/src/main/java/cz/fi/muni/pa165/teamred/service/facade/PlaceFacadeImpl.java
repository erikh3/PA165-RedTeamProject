package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.PlaceCreateDTO;
import cz.fi.muni.pa165.teamred.dto.PlaceDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.facade.PlaceFacade;
import cz.fi.muni.pa165.teamred.service.BeanMappingService;
import cz.fi.muni.pa165.teamred.service.PlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade implementation of {@link cz.fi.muni.pa165.teamred.facade.PlaceFacade}
 *
 * Method argument checks in the means of defensive programming.
 *
 * @author miroslav.laco@gmail.com
 */
@Service
@Transactional
public class PlaceFacadeImpl implements PlaceFacade {

    private final static Logger log = LoggerFactory.getLogger(PlaceFacadeImpl.class);

    @Inject
    private PlaceService placeService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createPlace(PlaceCreateDTO placeCreateDTO) {

        if(placeCreateDTO == null) {
            throw new IllegalArgumentException();
        }

        log.debug("facade createPlace()", placeCreateDTO.toString());

        Place mappedPlace = beanMappingService.mapTo(placeCreateDTO, Place.class);
        Place createdPlace = placeService.createPlace(mappedPlace);

        if(createdPlace == null) {
            return null;
        }

        log.debug("Place with id(" + createdPlace.getId() + ") has been created");
        return createdPlace.getId();

    }

    @Override
    public void changePlaceName(Long placeId, String newName) {

        if(placeId == null || newName == null) {
            throw new IllegalArgumentException();
        }

        log.debug("facade changePlaceName({})", placeId, newName);

        Place place = placeService.findById(placeId);
        place.setName(newName);
        placeService.updatePlace(place);

        log.debug("Place name has been updated for place id(" + place.toString() + ") to " + place.getName());
    }

    @Override
    public void deletePlace(Long placeId) {

        if(placeId == null) {
            throw new IllegalArgumentException();
        }

        log.debug("facade deletePlace({})", placeId);

        Place place = new Place();
        place.setId(placeId);
        placeService.removePlace(place);

        log.debug("Place with id(" + place.getId() + ") has been deleted");
    }

    @Override
    public PlaceDTO getPlaceWithId(Long placeId) {

        if(placeId == null) {
            throw new IllegalArgumentException();
        }

        log.debug("facade getPlaceWithId({})", placeId);

        Place place = placeService.findById(placeId);

        if (place == null) {
            log.debug("Place with id(" + placeId + ") has not been found");
            return null;
        }

        log.debug("Place with id(" + place.getId() + ") has been retrieved");

        return beanMappingService.mapTo(place, PlaceDTO.class);
    }

    @Override
    public PlaceDTO getPlaceWithName(String placeName) {

        if(placeName == null) {
            throw new IllegalArgumentException();
        }

        log.debug("facade getPlaceWithName({})", placeName);

        Place place = placeService.findByName(placeName);

        if (place == null) {
            log.debug("Place with name(" + placeName + ") has not been found");
            return null;
        }

        log.debug("Place with name(" + place.getName() + ") has been retrieved");

        return beanMappingService.mapTo(place, PlaceDTO.class);
    }

    @Override
    public List<RideDTO> getRidesWithOriginatingPlace(Long placeId) {

        if(placeId == null) {
            throw new IllegalArgumentException();
        }

        log.debug("facade getRidesWithOriginatingPlace({})", placeId);

        Place place = placeService.findById(placeId);

        if (place == null) {
            log.debug("Place with id(" + placeId + ") has not been found");
            return null;
        }

        log.debug("Place with id(" + place.getId() + ") has been retrieved");

        return beanMappingService.mapTo(place.getOriginatingRides(), RideDTO.class);
    }

    @Override
    public List<RideDTO> getRidesWithDestinationPlace(Long placeId) {

        if(placeId == null) {
            throw new IllegalArgumentException();
        }

        log.debug("facade getRidesWithDestinationPlace({})", placeId);

        Place place = placeService.findById(placeId);

        if (place == null) {
            log.debug("Place with id(" + placeId + ") has not been found");
            return null;
        }

        log.debug("Place with id(" + place.getId() + ") has been retrieved");

        return beanMappingService.mapTo(place.getDestinationRides(), RideDTO.class);
    }

    @Override
    public List<RideDTO> getRidesWithOriginatingAndDestinationPlace(Long originatingPlaceId, Long destinationPlaceId) {

        if(originatingPlaceId == null || destinationPlaceId == null) {
            throw new IllegalArgumentException();
        }

        log.debug("facade getRidesWithOriginatingAndDestinationPlace({})", originatingPlaceId, destinationPlaceId);

        List<Ride> resultRideList = placeService
                .findRidesWithOriginatingAndDestinationPlace(originatingPlaceId, destinationPlaceId);

        if (resultRideList == null) {
            log.debug("No rides with criteria have been found been found");
            return null;
        }

        log.debug("Rides with criteria have been retrieved, amount of rides: " + resultRideList.size());

        return beanMappingService.mapTo(resultRideList, RideDTO.class);
    }

    @Override
    public List<RideDTO> getRidesWithOriginatingAndDestinationPlaceByName(String originatingPlace, String destinationPlace) {

        if((originatingPlace == null || originatingPlace.isEmpty()) &&
                (destinationPlace == null || destinationPlace.isEmpty())) {
            throw new IllegalArgumentException();
        } else if (originatingPlace == null || originatingPlace.isEmpty()) {
            //TODO: Get with destination
        } else if (destinationPlace == null || destinationPlace.isEmpty()) {
            //TODO: Get with origin
        }

        log.debug("facade getRidesWithOriginatingAndDestinationPlaceByName({})", originatingPlace, destinationPlace);

        List<Ride> resultRideList = placeService
                .findRidesWithOriginatingAndDestinationPlaceByName(originatingPlace, destinationPlace);

        if (resultRideList == null) {
            log.debug("No rides with criteria have been found been found");
            return null;
        }

        log.debug("Rides with criteria have been retrieved, amount of rides: " + resultRideList.size());

        return beanMappingService.mapTo(resultRideList, RideDTO.class);
    }

    @Override
    public List<PlaceDTO> getAllPlaces() {

        log.debug("facade getAllPlaces({})");

        List<Place> allPlaces = placeService.findAll();

        log.debug("All places have been retrieved, amount of places: " + allPlaces.size());

        return beanMappingService.mapTo(allPlaces, PlaceDTO.class);
    }
}
