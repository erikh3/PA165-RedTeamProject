package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class providing operations related to {@link cz.fi.muni.pa165.teamred.entity.Place} entity.
 *
 * @author miroslav.laco@gmail.com
 */
@Service
public interface PlaceService {

    /**
     * Creates {@link cz.fi.muni.pa165.teamred.entity.Place} in app.
     *
     * @param place {@link cz.fi.muni.pa165.teamred.entity.Place} to be created
     * @return created place
     * @throws IllegalArgumentException if place param is null
     */
    Place createPlace(Place place) throws IllegalArgumentException;

    /**
     * Updates {@link cz.fi.muni.pa165.teamred.entity.Place} in app.
     *
     * @param place {@link cz.fi.muni.pa165.teamred.entity.Place} to be updated
     * @throws IllegalArgumentException if place param is null
     */
    void updatePlace(Place place) throws IllegalArgumentException;

    /**
     * Removes {@link cz.fi.muni.pa165.teamred.entity.Place} in app.
     *
     * @param place {@link cz.fi.muni.pa165.teamred.entity.Place} to be removed
     * @throws IllegalArgumentException if place param is null
     */
    void removePlace(Place place) throws IllegalArgumentException;

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Place} in app by unique id.
     *
     * @param id id of {@link cz.fi.muni.pa165.teamred.entity.Place} to be found
     * @return unique {@link cz.fi.muni.pa165.teamred.entity.Place} with provided id, null if not found
     * @throws IllegalArgumentException if id param is null
     */
    Place findById(Long id) throws IllegalArgumentException;

    /**
     * Finds {@link cz.fi.muni.pa165.teamred.entity.Place} in app by unique name.
     *
     * @param name name of {@link cz.fi.muni.pa165.teamred.entity.Place} to be found
     * @return unique {@link cz.fi.muni.pa165.teamred.entity.Place} with provided name, null if not found
     * @throws IllegalArgumentException if name param is null
     */
    Place findByName(String name) throws IllegalArgumentException;

    /**
     * Finds all {@link cz.fi.muni.pa165.teamred.entity.Place} in app.
     *
     * @return all {@link cz.fi.muni.pa165.teamred.entity.Place} objects stored
     */
    List<Place> findAll();

    /**
     * Retrieves all {@link cz.fi.muni.pa165.teamred.entity.Ride} originating in one place and having destination in
     * another place
     *
     * @param originatingPlaceId of place for which originating {@link cz.fi.muni.pa165.teamred.entity.Ride}
     *                           will be considered
     * @param destinationPlaceId of place for which destination {@link cz.fi.muni.pa165.teamred.entity.Ride}
     *                           will be considered
     * @return found rides with originating place and destination place specified or null if none found
     * @throws IllegalArgumentException
     */
    List<Ride> findRidesWithOriginatingAndDestinationPlace(Long originatingPlaceId, Long destinationPlaceId)
            throws IllegalArgumentException;
}
