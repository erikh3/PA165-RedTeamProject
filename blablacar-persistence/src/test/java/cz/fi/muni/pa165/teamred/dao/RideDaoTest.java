package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.teamred.entity.Driver;
import cz.fi.muni.pa165.teamred.entity.Passenger;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;

import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.in;

/**
 * Created by Jozef Cibík on 29.10.2017.
 */
@Transactional
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class RideDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RideDao rideDao;

    @Autowired
    private PlaceDao placeDao;

    @Autowired
    private UserDao userDao;

    private Ride validRide = new Ride();
    private Ride invalidRide = new Ride();

    private Driver validDriver = new Driver();

    private Calendar now = Calendar.getInstance();
    private Date today = now.getTime();

    private int validSeats = 4;
    private int invalidSeats = -1;

    private double validPrice = 100.00;
    private double invlidPrice = -1;

    private Place fromCity = new Place("BRNO");
    private Place toCity = new Place("TRENCIN");


    @BeforeClass
    public void init(){
        placeDao.create(fromCity);
        placeDao.create(toCity);

        validDriver.setName("John");
        validDriver.setSurename("Doe");
        validDriver.setNickname("j_doe");
        userDao.create(validDriver);

        validRide.setDriver(validDriver);
        validRide.setAvailableSeats(validSeats);
        validRide.setDeparture(today);
        validRide.setSourcePlace(fromCity);
        validRide.setDestinationPlace(toCity);
        validRide.setPrice(validPrice);

        rideDao.create(validRide);

    }

    @AfterClass
    public void release(){
    }

    @Test
    public void testAllRides(){
        //One Ride persisted
        assertThat(rideDao.findAll().size()).isEqualTo(1);
    }

    @Test
    public void testFindRideById(){
        //Checking equality on objects
        assertThat(rideDao.findById(validRide.getId())).isEqualToComparingFieldByField(validRide);
        //Checking Id's
        assertThat(rideDao.findById(validRide.getId()).getId()).isEqualTo(validRide.getId());
    }

    @Test
    public void testDeleteRide(){
        rideDao.delete(validRide);
        assertThat(rideDao.findAll().size()).isEqualTo(0);
    }

    @Test
    void testDeleteNullRide() {
        assertThatThrownBy(() -> rideDao.delete(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testCreateNullRide() {
        assertThatThrownBy(() -> rideDao.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testFindRideByNullId() {
        assertThatThrownBy(() -> rideDao.findById(null)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void findRideByInvalidId(){
        assertThat(rideDao.findById(2L)).isEqualTo(null);
    }

}
