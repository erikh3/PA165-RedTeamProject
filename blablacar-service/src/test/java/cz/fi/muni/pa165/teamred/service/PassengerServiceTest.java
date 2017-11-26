package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.RideDao;
import cz.fi.muni.pa165.teamred.dao.UserDao;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by jcibik on 11/24/17.
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class PassengerServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @Mock
    private RideDao rideDao;

    @Autowired
    @InjectMocks
    private PassengerService passengerService;

    private Ride validRide;
    private Ride existingRide;

    private User validUser;
    private User existingUser;

    @BeforeClass
    void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    void prepareTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.NOVEMBER, 8);

        validRide = new Ride();
        validRide.setId(1L);
        validRide.setAvailableSeats(4);
        validRide.setDeparture(calendar.getTime());
        validRide.setDestinationPlace(new Place("Brno"));
        validRide.setSourcePlace(new Place("Prague"));
        validRide.setSeatPrice(10);

        validUser = new User();
        validUser.setId(1L);
        validUser.setName("John");
        validUser.setSurname("Doe");
        validUser.setNickname("j_doe");

        existingRide = new Ride();
        existingRide.setId(2L);
        existingRide.setAvailableSeats(4);
        existingRide.setDeparture(calendar.getTime());
        existingRide.setDestinationPlace(new Place("Bratislava"));
        existingRide.setSourcePlace(new Place("London"));
        existingRide.setSeatPrice(10);


        //create existing reference
        existingUser = new User();
        existingUser.setId(2L);
        existingUser.setName("Matt");
        existingUser.setSurname("Groenig");
        existingUser.setNickname("maggie");

        existingRide.addPassenger(existingUser);
        existingUser.addRideAsPassenger(existingRide);
        existingRide.setAvailableSeats(existingRide.getAvailableSeats() - 1);

        when(userDao.findById(validUser.getId())).thenReturn(validUser);
        when(rideDao.findById(validRide.getId())).thenReturn(validRide);

        when(userDao.findById(existingUser.getId())).thenReturn(existingUser);
        when(rideDao.findById(existingRide.getId())).thenReturn(existingRide);

        doNothing().when(userDao).update(any());
        doNothing().when(rideDao).update(any());
    }


    //_________________________________________________________________________________________________________Add Tests
    @Test
    void testAddPassengerToRide(){
        int seats = validRide.getAvailableSeats();

        passengerService.addPassengerToRide(validUser.getId(), validRide.getId());

        verify(userDao).update(validUser);
        verify(rideDao).update(validRide);

        assertThat(validUser.getRidesAsPassenger()
                .contains(validRide));
        assertThat(validRide.getPassengers()
                .contains(validUser));
        assertThat(seats-1)
                .isEqualTo(validRide.getAvailableSeats());
    }

    @Test
    void testAddExistingPassengerToRide(){
        int seats = existingRide.getAvailableSeats();

        passengerService.addPassengerToRide(existingRide.getId(), existingRide.getId());

        verify(userDao, never()).update(existingUser);
        verify(rideDao, never()).update(existingRide);

        assertThat(seats)
                .isEqualTo(existingRide.getAvailableSeats());
    }


    @Test
    void testAddPassengerToFullRide(){
        validRide.setAvailableSeats(0);
        Collection<User> passengers = validRide.getPassengers();
        Collection<Ride> rides = validUser.getRidesAsPassenger();

        passengerService.addPassengerToRide(validUser.getId(), validRide.getId());

        assertThat(validUser.getRidesAsPassenger())
                .isEqualTo(rides);
        assertThat(validRide.getPassengers())
                .isEqualTo(passengers);
        assertThat(validRide.getAvailableSeats())
                .isEqualTo(0);
    }

    @Test
    void testNonCompleteBidirectionalReferenceRideOnly(){
        validRide.addPassenger(validUser);

        assertThatThrownBy( () -> passengerService.addPassengerToRide(validUser.getId(), validRide.getId()))
                .isInstanceOf(IllegalStateException.class);
    }


    @Test
    void testNonCompleteBidirectionalReferenceUserOnly(){
        validUser.addRideAsPassenger(validRide);

        assertThatThrownBy( () -> passengerService.addPassengerToRide(validUser.getId(), validRide.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testAddPasssengerToNonExistingRide(){
        when(rideDao.findById(validRide.getId())).thenReturn(null);

        assertThatThrownBy( () -> passengerService.addPassengerToRide(validUser.getId(), validRide.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testAddNonExistingPassengerToRide(){
        when(userDao.findById(validUser.getId())).thenReturn(null);

        assertThatThrownBy( () -> passengerService.addPassengerToRide(validUser.getId(), validRide.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    //______________________________________________________________________________________________________Remove Tests
    @Test
    void testRemovePassengerFromRide(){
        int seats = existingRide.getAvailableSeats();

        passengerService.removePassengerFromRide(existingUser.getId(), existingRide.getId());

        assertThat(!validUser.getRidesAsPassenger().contains(validRide));
        assertThat(!validRide.getPassengers().contains(validUser));
        assertThat(seats + 1)
                .isEqualTo(validRide.getAvailableSeats());
    }


    @Test
    void testRemoveNonExistingPassengerFromRide() {
        int seats = existingRide.getAvailableSeats();

        passengerService.removePassengerFromRide(validUser.getId(), existingRide.getId());

        assertThat(seats)
                .isEqualTo(existingRide.getAvailableSeats());
    }
}
