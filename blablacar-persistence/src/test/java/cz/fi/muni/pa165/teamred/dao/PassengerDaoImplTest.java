package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.teamred.entity.Driver;
import cz.fi.muni.pa165.teamred.entity.Passenger;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.testng.annotations.BeforeMethod;

/**
 * Created by Šimon on 29.10.2017.
 */
@Transactional
@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class PassengerDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PassengerDao passengerDao;
        
    private Passenger passenger1, passenger2;
    private Ride ride;
    private Place fromCity, toCity;
    private Driver driver;
    private int seats = 4;
    private double price = 100.00;
    
    @BeforeMethod
    void init() {
        
//        fromCity = new Place("BRNO");
//        toCity = new Place("TRENCIN");
//        
//        driver = new Driver();
//        driver.setCarDescription("AUDI A8");
//        driver.setNote("Test note 1.");
//        driver.setName("Jan");
//        driver.setSurename("Mrkva");
//        driver.setNickname("janci");
//        
//        ride = new Ride();
//        ride.setSourcePlace(fromCity);
//        ride.setDestinationPlace(toCity);
//        ride.setDriver(driver);
//        ride.setAvailableSeats(seats);
//        ride.setPrice(price);
//        ride.setDeparture(new GregorianCalendar(2014, Calendar.FEBRUARY, 11)
//                .getTime());
        
        passenger1 = new Passenger();
        passenger1.setName("Jan");
        passenger1.setSurename("Mrkva");
        passenger1.setNickname("janci");
        //passenger1.getRides().add(ride);

        passenger2 = new Passenger();
        passenger2.setName("Cyprian");
        passenger2.setSurename("Kaluznik");
        passenger2.setNickname("kaluza");
        //passenger2.getRides().add(ride);
    }
    
    @Test
    public void createFindDeleteTest(){
        passengerDao.create(passenger1);
        passengerDao.create(passenger2);

        assertThat(passengerDao.findById(passenger1.getId()))
                .isEqualTo(passenger1);
        assertThat(passengerDao.findAll())
                .containsExactlyInAnyOrder(passenger1,passenger2);

        passengerDao.delete(passenger2);

        assertThat(passengerDao.findAll())
                .containsExactly(passenger1);
    }
    
    @Test
    public void createNullTest() {
        assertThatThrownBy(() -> passengerDao.create(null)).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void deleteNullTest() {
        assertThatThrownBy(() -> passengerDao.delete(null)).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void findByIdNullTest() {
        assertThatThrownBy(() -> passengerDao.findById(null)).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void findByIdNonExistingTest() {
        Passenger passengerFound = passengerDao.findById(-1L);
        assertThat(passengerFound).isNull();
    }
}
