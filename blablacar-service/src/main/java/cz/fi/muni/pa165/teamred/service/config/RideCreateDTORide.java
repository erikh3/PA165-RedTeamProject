package cz.fi.muni.pa165.teamred.service.config;

import cz.fi.muni.pa165.teamred.dto.RideCreateDTO;
import cz.fi.muni.pa165.teamred.entity.Ride;
import org.dozer.DozerConverter;

public class RideCreateDTORide extends DozerConverter<RideCreateDTO, Ride> {
    public RideCreateDTORide() {
        super(RideCreateDTO.class, Ride.class);
    }

    @Override
    public RideCreateDTO convertFrom(Ride ride, RideCreateDTO rideCreateDTO) {
        return new RideCreateDTO();
    }

    @Override
    public Ride convertTo(RideCreateDTO rideCreateDTO, Ride ride) {
        if (ride == null) {
            ride = new Ride();
        }
        ride.setDeparture(rideCreateDTO.getDeparture());
        ride.setAvailableSeats(rideCreateDTO.getSeatsAvailable());
        ride.setSeatPrice(rideCreateDTO.getSeatPrize());

        return ride;
    }
}
