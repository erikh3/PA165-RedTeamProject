package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Jozef Cibík
 */
@Transactional
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class RideDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RideDao rideDao;

    private Ride persistedRide;

    private Date now = new GregorianCalendar(2017, Calendar.FEBRUARY, 11).getTime();
    private Date past = new GregorianCalendar(2017, Calendar.FEBRUARY, 10).getTime();

    private int validSeats = 4;
    private int invalidSeats = -1;

    private double validPrice = 100.00;

    @BeforeMethod
    void init() {
        Ride ride = new Ride();
        User validDriver = new User();

        validDriver.setName("John");
        validDriver.setSurname("Doe");
        validDriver.setNickname("j_doe");
        validDriver.setLoginId(new Long(9997));


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


        em.persist(ride);
        em.flush();

        this.persistedRide = ride;
    }

    private void initializeRide(Ride ride){
        User validDriver = new User();

        validDriver.setName("John");
        validDriver.setSurname("Diggle");
        validDriver.setNickname("arrow");
        validDriver.setLoginId(new Long(9998));


        ride.setDriver(validDriver);
        ride.setAvailableSeats(validSeats);
        ride.setDeparture(now);

        Place validBrno = new Place("Praha");
        Place validTrencin = new Place("Jaroslavl");

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
        assertThat(rideDao.findAll().size()).isEqualTo(2);
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
        // todo:
//        assertThatThrownBy(() -> rideDao.create(validRide)).isInstanceOf(ConstraintViolationException.class);
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
        assertThatThrownBy(() -> rideDao.create(validRide)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testCreateNullRide(){
        assertThatThrownBy(() -> rideDao.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    //------------------------------------------------------------------------------------------------------Find Related
    @Test
    public void testFindRideById(){
        assertThat(rideDao.findById(persistedRide.getId())).isEqualTo(persistedRide);
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
        Long id = rideDao.findAll().get(0).getId();
        rideDao.delete(rideDao.findById(id));
        assertThat(rideDao.findAll().size()).isEqualTo(0);
    }

    @Test
    void testDeleteNullRide() {
        assertThatThrownBy(() -> rideDao.delete(null)).isInstanceOf(IllegalArgumentException.class);
    }

    //----------------------------------------------------------------------------------------------------Update Related
    @Test
    public void testUpdateValidDeparture(){
        Date newTime = new GregorianCalendar(2017, Calendar.FEBRUARY, 19).getTime();
        persistedRide.setDeparture(newTime);
        rideDao.update(persistedRide);
    }

    @Test
    public void testUpdateValidPassengers(){
        User newPassenger = new User();
        newPassenger.setName("Adam");
        newPassenger.setSurname("Smith");
        newPassenger.setNickname("smitty");
        newPassenger.setLoginId(new Long(9990));

        persistedRide.addPassenger(newPassenger);
        rideDao.update(persistedRide);
        assertThat(rideDao.findById(persistedRide.getId()).getPassengers().size()).isEqualTo(1);
    }

    @Test
    public void testUpdateValidDriver(){
        User newDriver = new User();
        newDriver.setName("Adam");
        newDriver.setSurname("Smith");
        newDriver.setNickname("smitty");
        newDriver.setLoginId(new Long(9990));

        persistedRide.setDriver(newDriver);
        rideDao.update(persistedRide);
        assertThat(rideDao.findById(persistedRide.getId()).getDriver()).isEqualTo(newDriver);
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
        Place newPlace = new Place("New York");
        persistedRide.setSourcePlace(newPlace);
        rideDao.update(persistedRide);
        assertThat(rideDao.findById(persistedRide.getId()).getSourcePlace()).isEqualTo(newPlace);
    }


    @Test
    public void testUpdateInValidDeparture(){
        persistedRide.setDeparture(past);
        // todo:
//        assertThatThrownBy(() -> rideDao.update(persistedRide)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testUpdateInValidDriver(){
        User newDriver = new User();
        newDriver.setName("Adam");
        newDriver.setSurname("Smith");
        newDriver.setNickname("");

        persistedRide.setDriver(newDriver);
        assertThatThrownBy(() -> rideDao.update(persistedRide)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testUpdateInValidAvailableSeats(){
        persistedRide.setAvailableSeats(invalidSeats);
        rideDao.update(persistedRide);
        assertThatThrownBy(() -> em.flush()).isInstanceOf(ConstraintViolationException.class);
    }
}
