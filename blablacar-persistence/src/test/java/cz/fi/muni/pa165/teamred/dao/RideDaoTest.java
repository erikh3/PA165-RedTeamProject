package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Passenger;
import cz.fi.muni.pa165.teamred.entity.Ride;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jozef Cibík on 29.10.2017.
 */
public class RideDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceContext
    EntityManager em;

    @SuppressWarnings("WeakerAccess")
    public static Ride createRideBrnoToPraha() {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, Calendar.NOVEMBER, 25);
        Date date = cal.getTime();

        Ride ride = new Ride();
        ride.setPrice(12.5);
        ride.setDriver(DriverDaoTest.createMilos());
        ride.setAvailableSeats(4);
        ride.setDeparture(date);
        ride.setSourcePlace(PlaceDaoTest.createBrnoHlavniNadrazi());
        ride.setDestinationPlace(PlaceDaoTest.createPrahaZoo());
        ride.addPassenger(PassengerDaoTest.createRastislav());

        return ride;
    }
}
