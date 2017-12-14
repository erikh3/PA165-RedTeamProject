package cz.fi.muni.pa165.teamred.service.config;

import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.service.PlaceService;
import org.dozer.DozerConverter;

import javax.inject.Inject;

public class PlaceIdConverter extends DozerConverter<Place, Long> {
    @Inject
    private PlaceService placeService;

    public PlaceIdConverter() {
        super(Place.class, Long.class);
    }

    @Override
    public Long convertTo(Place place, Long id) {
        return place == null ? null : place.getId();
    }

    @Override
    public Place convertFrom(Long id, Place place) {
        return id == null ? null : placeService.findById(id);
    }
}
