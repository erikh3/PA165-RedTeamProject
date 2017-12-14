package cz.fi.muni.pa165.teamred.service.config;

import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.service.RideService;
import org.dozer.DozerConverter;

import javax.inject.Inject;

public class RideIdConverter extends DozerConverter<Ride, Long> {
    @Inject
    private RideService rideService;

    public RideIdConverter() {
        super(Ride.class, Long.class);
    }

    @Override
    public Long convertTo(Ride ride, Long id) {
        return ride == null ? null : ride.getId();
    }

    @Override
    public Ride convertFrom(Long id, Ride ride) {
        return id == null ? null : rideService.findById(id);
    }
}
