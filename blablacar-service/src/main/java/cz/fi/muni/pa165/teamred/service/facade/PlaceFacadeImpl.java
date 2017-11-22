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
    final static Logger log = LoggerFactory.getLogger(PlaceFacadeImpl.class);

    @Inject
    private PlaceService placeService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createPlace(PlaceCreateDTO placeCreateDTO) {
        if(placeCreateDTO == null || placeCreateDTO.getName() == null || placeCreateDTO.getName().isEmpty()) {
            //TODO: Exception handling
            return null;
        }
        Place mappedPlace = beanMappingService.mapTo(placeCreateDTO, Place.class);
        Place createdPlace = placeService.createPlace(mappedPlace);
        return createdPlace.getId();
    }

    @Override
    public void changePlaceName(Long placeId, String newName) {
        if(placeId == null || newName == null || newName.isEmpty()) {
            //TODO: Exception handling
            return;
        }
        Place place = placeService.findById(placeId);
        place.setName(newName);
        placeService.updatePlace(place);
    }

    @Override
    public void deletePlace(Long placeId) {
        if(placeId == null) {
            //TODO: Exception handling
            return;
        }
        Place place = new Place();
        place.setId(placeId);
        placeService.removePlace(place);
    }

    @Override
    public PlaceDTO getPlaceWithId(Long placeId) {
        if(placeId == null) {
            //TODO: Exception handling
            return null;
        }
        Place place = placeService.findById(placeId);
        return (place == null) ? null : beanMappingService.mapTo(place, PlaceDTO.class);
    }

    @Override
    public PlaceDTO getPlaceWithName(String placeName) {
        if(placeName == null || placeName.isEmpty()) {
            //TODO: Exception handling
            return null;
        }
        Place place = placeService.findByName(placeName);
        return (place == null) ? null : beanMappingService.mapTo(place, PlaceDTO.class);
    }

    @Override
    public List<RideDTO> getRidesWithOriginatingPlace(Long placeId) {
        if(placeId == null) {
            //TODO: Exception handling
            return null;
        }
        Place place = placeService.findById(placeId);
        return (place == null) ? null : beanMappingService.mapTo(place.getOriginatingRides(), RideDTO.class);
    }

    @Override
    public List<RideDTO> getRidesWithDestinationPlace(Long placeId) {
        if(placeId == null) {
            //TODO: Exception handling
            return null;
        }
        Place place = placeService.findById(placeId);
        return (place == null) ? null : beanMappingService.mapTo(place.getDestinationRides(), RideDTO.class);
    }

    @Override
    public List<RideDTO> getRidesWithOriginatingAndDestinationPlace(Long originatingPlaceId, Long destinationPlaceId) {
        if(originatingPlaceId == null || destinationPlaceId == null) {
            //TODO: Exception handling
            return null;
        }
        List<Ride> resultRideList = placeService
                .findRidesWithOriginatingAndDestinationPlace(originatingPlaceId, destinationPlaceId);
        return (resultRideList == null) ? null : beanMappingService.mapTo(resultRideList, RideDTO.class);
    }

    @Override
    public List<PlaceDTO> getAllPlaces() {
        return beanMappingService.mapTo(placeService.findAll(), PlaceDTO.class);
    }
}
