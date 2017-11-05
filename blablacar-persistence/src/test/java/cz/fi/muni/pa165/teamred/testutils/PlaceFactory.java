package cz.fi.muni.pa165.teamred.testutils;

import cz.fi.muni.pa165.teamred.entity.Place;

/**
 * @author Erik Horváth
 */
@SuppressWarnings("WeakerAccess")
public class PlaceFactory {
    public static Place createPlace(String name) {
        Place place = new Place();
        place.setName(name);
        return place;
    }

    public static Place createBrno() {
        return createPlace("Brno");
    }

    public static Place createPraha() {
        return createPlace("Praha");
    }
}
