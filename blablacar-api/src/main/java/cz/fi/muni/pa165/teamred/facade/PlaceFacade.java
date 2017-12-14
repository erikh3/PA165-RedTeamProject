package cz.fi.muni.pa165.teamred.facade;

import cz.fi.muni.pa165.teamred.dto.PlaceCreateDTO;
import cz.fi.muni.pa165.teamred.dto.PlaceDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;

import java.util.List;

/**
 * Facade interface for operations over Places
 *
 * @author miroslav.laco@gmail.com
 */
public interface PlaceFacade {
    /**
     * Creates place
     *
     * @param placeCreateDTO parameters will be used in creation of place
     * @return id of created place
     */
    Long createPlace(PlaceCreateDTO placeCreateDTO);

    /**
     * Sets new name for existing place
     *
     * @param placeId of place which name will be changed
     * @param newName name to set
     */
    void changePlaceName(Long placeId, String newName);

    /**
     * Deletes place
     *
     * @param placeId of place which will be deleted
     */
    void deletePlace(Long placeId);

    /**
     * Retrieves place according to id
     *
     * @param placeId of place which will be retrieved
     * @return found place or null if none found
     */
    PlaceDTO getPlaceWithId(Long placeId);

    /**
     * Retrieves place according to its name
     *
     * @param placeName of place which will be retrieved
     * @return found place or null if none found
     */
    PlaceDTO getPlaceWithName(String placeName);

    /**
     * Retrieves all rides originating in place
     *
     * @param placeId of place for which originating rides will be returned
     * @return found rides or null if none found
     */
    List<RideDTO> getRidesWithOriginatingPlace(Long placeId);

    /**
     * Retrieves all rides having destination in place
     *
     * @param placeId of place for which destination rides will be returned
     * @return found rides or null if none found
     */
    List<RideDTO> getRidesWithDestinationPlace(Long placeId);

    /**
     * Retrieves all rides originating in place and having destination in another place
     *
     * @param originatingPlaceId of place for which originating rides will be considered
     * @param destinationPlaceId of place for which destination rides will be considered
     * @return found rides with originating place and destination place specified or null if none found
     */
    List<RideDTO> getRidesWithOriginatingAndDestinationPlace(Long originatingPlaceId, Long destinationPlaceId);

    /**
     * Retrieves all places
     *
     * @return list of places
     */
    List<PlaceDTO> getAllPlaces();
}
