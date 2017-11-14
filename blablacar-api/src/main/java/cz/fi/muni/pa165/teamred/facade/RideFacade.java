package cz.fi.muni.pa165.teamred.facade;

import cz.fi.muni.pa165.teamred.dto.RideCreateDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;

import java.util.List;

/**
 * @author Šimon Mačejovský
 */
public interface RideFacade {

    Long createRide(RideCreateDTO rideCreateDTO);

    void updateRide(RideDTO rideDto);

    void deleteRide(Long rideId);

    void addPassenger(Long rideId, Long userId);

    void addComment(Long rideId, Long commentId);

    void removePassenger(Long rideId, Long userId);

    void removeComment(Long rideId, Long commentId);

    RideDTO getRideWithId(Long rideId);

    List<RideDTO> getAllRides();
}
