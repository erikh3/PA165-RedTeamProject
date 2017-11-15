package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.entity.Comment;
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
import cz.fi.muni.pa165.teamred.dao.RideDao;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * @author Šimon Mačejovský
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RideServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private RideDao rideDao;

    @Autowired
    @InjectMocks
    private RideService rideService;

    private Ride ride1;
    private Ride ride2;

    private Place brno;
    private Place prague;

    private User adam;
    private User fero;

    private Comment comment1;
    private Comment comment2;

    @BeforeClass
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    void prepareRide(){
        Calendar departure1 = Calendar.getInstance();
        departure1.set(2017, Calendar.DECEMBER, 5, 16, 10);

        Calendar calendar1 = Calendar.getInstance();
        comment1 = new Comment();
        comment1.setId(8L);
        comment1.setText("sample text");
        calendar1.set(2017, Calendar.NOVEMBER, 14);
        comment1.setCreated(calendar1.getTime());

        ride1 = new Ride();
        ride1.setId(8L);
        ride1.setDeparture(departure1.getTime());
        ride1.setAvailableSeats(4);
        ride1.setSeatPrice(5.00);
        //ride1.addComment(comment1);

        brno = new Place();
        brno.setId(1L);
        brno.setName("Brno");
        brno.addOriginatingRide(ride1);

        adam = new User();
        adam.setId(8L);
        adam.setName("Adam");
        adam.addRideAsDriver(ride1);

        Calendar departure2 = Calendar.getInstance();
        departure1.set(2016, Calendar.OCTOBER, 10, 20, 0);

        Calendar calendar2 = Calendar.getInstance();
        comment2 = new Comment();
        comment2.setId(8L);
        comment2.setText("another sample text");
        calendar2.set(2016, Calendar.NOVEMBER, 4);
        comment2.setCreated(calendar2.getTime());

        ride2 = new Ride();
        ride2.setId(5L);
        ride2.setDeparture(departure2.getTime());
        ride2.setAvailableSeats(3);
        ride2.setSeatPrice(7.00);
        ride2.addComment(comment2);

        prague = new Place();
        prague.setId(2L);
        prague.setName("Prague");
        prague.addOriginatingRide(ride2);

        fero = new User();
        fero.setId(8L);
        fero.setName("Fero");
        fero.addRideAsDriver(ride2);

        brno.addDestinationRide(ride2);
        adam.addRideAsPassenger(ride2);
        prague.addDestinationRide(ride1);
        fero.addRideAsPassenger(ride1);

        ride1.setSourcePlace(brno);
        ride1.setDestinationPlace(prague);
        ride2.setSourcePlace(prague);
        ride2.setDestinationPlace(brno);
    }


    // Create tests
    @Test
    void createRide(){
        doNothing().when(rideDao).create(any());
        rideService.createRide(ride1);
        verify(rideDao).create(ride1);
    }

    @Test
    void createNullRide() {
        doThrow(new IllegalArgumentException()).when(rideDao).create(null);
        assertThatThrownBy(() -> rideService.createRide(null)).isInstanceOf(IllegalArgumentException.class);
    }

    // Update tests
    @Test
    void updateRide() {
        ride1.setSeatPrice(10);
        doNothing().when(rideDao).update(ride1);
        rideService.updateRide(ride1);
        verify(rideDao).update(ride1);
    }

    @Test
    void updateNullRide() {
        doThrow(new IllegalArgumentException()).when(rideDao).update(null);
        assertThatThrownBy(() -> rideService.updateRide(null)).isInstanceOf(IllegalArgumentException.class);
    }

    // Delete tests
    @Test
    void deleteRide() {
        doNothing().when(rideDao).delete(ride1);

        rideService.deleteRide(ride1);

        verify(rideDao).delete(ride1);
    }

    @Test
    void deleteNullRide() {
        doThrow(new IllegalArgumentException()).when(rideDao).delete(null);

        assertThatThrownBy(() -> rideService.deleteRide(null)).isInstanceOf(IllegalArgumentException.class);
    }

    // Finding tests
    @Test
    void findAllTest() {
        List<Ride> allRides = new ArrayList<>();
        allRides.add(ride1);
        allRides.add(ride2);

        when(rideDao.findAll()).thenReturn(allRides);

        List<Ride> result = rideService.findAll();

        assertThat(result).containsExactlyInAnyOrder(ride1, ride2);
    }

    @Test
    void findAllNothingFoundTest() {
        when(rideDao.findAll()).thenReturn(new ArrayList<>());

        List<Ride> result = rideService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void findByIdTest() {
        when(rideDao.findById(ride1.getId())).thenReturn(ride1);
        when(rideDao.findById(ride2.getId())).thenReturn(ride2);

        Ride shouldBeSample = rideService.findById(ride1.getId());
        assertThat(shouldBeSample).isEqualToComparingFieldByField(shouldBeSample);

        Ride shouldBeSecond = rideService.findById(ride2.getId());
        assertThat(shouldBeSecond).isEqualToComparingFieldByFieldRecursively(ride2);
    }

    @Test
    void findByIdNotFoundTest() {
        when(rideDao.findById(any())).thenReturn(null);

        Ride result = rideService.findById(-1L);

        assertThat(result).isNull();
    }

    // Add and remove tests
    @Test
    void addPassenger() {
        rideService.addPassenger(ride1, fero);
        assertThat(ride1.getPassengers().contains(fero));
    }

    @Test
    void addComment() {
        rideService.addComment(ride1, comment1);
        assertThat(ride1.getComments().contains(comment1));
    }


    //TODO add/remove tests


}
