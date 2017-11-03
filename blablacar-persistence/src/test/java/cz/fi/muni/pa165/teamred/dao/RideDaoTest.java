package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.*;
import javax.validation.ConstraintViolationException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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


    private Date now = new GregorianCalendar(2017, Calendar.FEBRUARY, 11).getTime();
    private Date past = new GregorianCalendar(2017, Calendar.FEBRUARY, 10).getTime();

    private int validSeats = 4;
    private int invalidSeats = -1;

    private double validPrice = 100.00;


    private void initializeRide(Ride ride){
        User validDriver = new User();

        validDriver.setName("John");
        validDriver.setSurname("Doe");
        validDriver.setNickname("j_doe");


        ride.setDriver(validDriver);
        ride.setAvailableSeats(validSeats);
        ride.setDeparture(now);

        Place validBrno = new Place("Brno");
        Place validTrencin = new Place("Trencin");

        ride.setSourcePlace(validBrno);
        ride.setDestinationPlace(validTrencin);
        ride.setSeatPrice(validPrice);

        Set<Ride> rideSet = new HashSet<Ride>();
        rideSet.add(ride);

        validBrno.setOriginatingRides(rideSet);
        validTrencin.setDestinationRides(rideSet);

    }




    //----------------------------------------------------------------------------------------------------Create Related
    @Test
    public void testCreateValidRide(){
        Ride validRide = new Ride();
        initializeRide(validRide);

        rideDao.create(validRide);
        assertThat(validRide.getId()).isNotNull();
        assertThat(rideDao.findAll().size()).isEqualTo(1);
    }


    @Test
    public void testCreateInValidRideNullDriver(){
        Ride validRide = new Ride();
        initializeRide(validRide);

        validRide.setDriver(null);
        assertThatThrownBy(() -> rideDao.create(validRide)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testCreateInValidRidePastDeparture(){
        Ride validRide = new Ride();
        initializeRide(validRide);

        validRide.setDeparture(past);
        assertThatThrownBy(() -> rideDao.create(validRide)).isInstanceOf(ConstraintViolationException.class);
    }


    @Test
    public void testCreateInValidRideNullDeparture(){
        Ride validRide = new Ride();
        initializeRide(validRide);

        validRide.setDeparture(null);
        assertThatThrownBy(() -> rideDao.create(validRide)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testCreateInValidRideNullSourcePlace(){
        Ride validRide = new Ride();
        initializeRide(validRide);

        validRide.setSourcePlace(null);
        assertThatThrownBy(() -> rideDao.create(validRide)).isInstanceOf(ConstraintViolationException.class);
    }


    @Test
    public void testCreateInValidRideNullDestPlace(){
        Ride validRide = new Ride();
        initializeRide(validRide);

        validRide.setDestinationPlace(null);
        assertThatThrownBy(() -> rideDao.create(validRide)).isInstanceOf(ConstraintViolationException.class);
    }


    @Test
    public void testCreateInValidRideInvalidSeats(){
        Ride validRide = new Ride();
        initializeRide(validRide);

        validRide.setAvailableSeats(invalidSeats);
        assertThatThrownBy(() -> rideDao.create(validRide)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateNullRide(){
        assertThatThrownBy(() -> rideDao.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    //------------------------------------------------------------------------------------------------------Find Related
    @Test
    public void testFindRideById(){
        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);

        //Checking equality on objects
        Ride foundRide = rideDao.findById(validRide.getId());
        assertThat(rideDao.findById(validRide.getId())).isEqualTo(validRide);
        //Checking Id's
        assertThat(rideDao.findById(validRide.getId()).getId()).isEqualTo(validRide.getId());
    }

    @Test
    public void findRideByInvalidId(){
        assertThat(rideDao.findById(2L)).isEqualTo(null);
    }

    @Test
    void testFindRideByNullId() {
        assertThatThrownBy(() -> rideDao.findById(null)).isInstanceOf(IllegalArgumentException.class);
    }

    //----------------------------------------------------------------------------------------------------Delete Related

    @Test
    public void testDeleteRide(){
        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);

        rideDao.delete(validRide);
        assertThat(rideDao.findById(validRide.getId())).isNull();
    }

    @Test
    void testDeleteNullRide() {
        assertThatThrownBy(() -> rideDao.delete(null)).isInstanceOf(IllegalArgumentException.class);
    }

    //----------------------------------------------------------------------------------------------------Update Related
    @Test
    public void testUpdateValidDeparture(){
        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);

        Date newTime = new GregorianCalendar(2017, Calendar.FEBRUARY, 19).getTime();
        validRide.setDeparture(newTime);
        rideDao.update(validRide);
    }

    @Test
    public void testUpdateValidPassengers(){
        User newPassenger = new User();
        newPassenger.setName("Adam");
        newPassenger.setSurname("Smith");
        newPassenger.setNickname("smitty");

        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);


        validRide.addPassenger(newPassenger);
        rideDao.update(validRide);
        assertThat(rideDao.findById(validRide.getId()).getPassengers().size()).isEqualTo(1);
    }

    @Test
    public void testUpdateValidDriver(){
        User newDriver = new User();
        newDriver.setName("Adam");
        newDriver.setSurname("Smith");
        newDriver.setNickname("smitty");


        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);


        validRide.setDriver(newDriver);
        rideDao.update(validRide);
        assertThat(rideDao.findById(validRide.getId()).getDriver()).isEqualTo(newDriver);
    }

    @Test
    public void testUpdateValidAvailableSeates(){
        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);

        validRide.setAvailableSeats(3);
        rideDao.update(validRide);
        assertThat(rideDao.findById(validRide.getId()).getAvailableSeats()).isEqualTo(3);
    }

    @Test
    public void testUpdateValidDestPlace(){

        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);

        Place newPlace = new Place("New York");
        validRide.setDestinationPlace(newPlace);
        rideDao.update(validRide);
        assertThat(rideDao.findById(validRide.getId()).getDestinationPlace()).isEqualTo(newPlace);
    }

    @Test
    public void testUpdateValidSourcePlace(){
        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);

        Place newPlace = new Place("New York");
        validRide.setSourcePlace(newPlace);
        rideDao.update(validRide);
        assertThat(rideDao.findById(validRide.getId()).getSourcePlace()).isEqualTo(newPlace);
    }


    @Test
    public void testUpdateInValidDeparture(){
        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);

        validRide.setDeparture(past);
        assertThatThrownBy(() -> rideDao.update(validRide)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testUpdateInValidDriver(){
        User newDriver = new User();
        newDriver.setName("Adam");
        newDriver.setSurname("Smith");
        newDriver.setNickname("");


        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);


        validRide.setDriver(newDriver);
        assertThatThrownBy(() -> rideDao.update(validRide)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testUpdateInValidAvailableSeates(){
        Ride validRide = new Ride();
        initializeRide(validRide);
        rideDao.create(validRide);

        validRide.setAvailableSeats(invalidSeats);
        assertThatThrownBy(() -> rideDao.update(validRide)).isInstanceOf(ConstraintViolationException.class);
    }
}
