package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Place;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PlaceDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceContext
    EntityManager em;

    @SuppressWarnings("WeakerAccess")
    public static Place createBrnoHlavniNadrazi() {
        Place place = new Place();
        place.setName("Brno Hlavni nadrazi");
        return place;
    }

    @SuppressWarnings("WeakerAccess")
    public static Place createPrahaZoo() {
        Place place = new Place();
        place.setName("Praha Zoo");
        return place;
    }
}
