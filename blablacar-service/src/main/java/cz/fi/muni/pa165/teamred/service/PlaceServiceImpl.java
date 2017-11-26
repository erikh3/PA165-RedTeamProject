package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.PlaceDao;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miroslav.laco@gmail.com
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    @Inject
    private PlaceDao placeDao;

    @Override
    public Place createPlace(Place place) throws IllegalArgumentException {
        if(place == null) {
            throw new IllegalArgumentException("Place to create is null!");
        }
        placeDao.create(place);
        return place;
    }

    @Override
    public void updatePlace(Place place) throws IllegalArgumentException {
        if(place == null) {
            throw new IllegalArgumentException("Place to update is null!");
        }
        placeDao.update(place);
    }

    @Override
    public void removePlace(Place place) throws IllegalArgumentException {
        if(place == null) {
            throw new IllegalArgumentException("Place to remove is null!");
        }
        placeDao.delete(place);
    }

    @Override
    public Place findById(Long id) throws IllegalArgumentException {
        if(id == null) {
            throw new IllegalArgumentException("Place id is null!");
        }
        return placeDao.findById(id);
    }

    @Override
    public Place findByName(String name) throws IllegalArgumentException {
        if(name == null) {
            throw new IllegalArgumentException("Place name is null!");
        }
        return placeDao.findByName(name);
    }

    @Override
    public List<Place> findAll() {
        return placeDao.findAll();
    }

    @Override
    public List<Ride> findRidesWithOriginatingAndDestinationPlace(Long originatingPlaceId, Long destinationPlaceId) throws IllegalArgumentException {

        if(originatingPlaceId == null || destinationPlaceId == null) {
            throw new IllegalArgumentException();
        }

        Place placeOriginating = placeDao.findById(originatingPlaceId);
        Place placeDestination = placeDao.findById(destinationPlaceId);

        if(placeOriginating == null || placeDestination == null) {
            return null;
        }

        List<Ride> resultRideList = new ArrayList<>(placeOriginating.getOriginatingRides());
        resultRideList.retainAll(placeDestination.getDestinationRides());

        return resultRideList.isEmpty() ? null : resultRideList;
    }
}
