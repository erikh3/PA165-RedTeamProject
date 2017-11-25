package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.PlaceDao;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author miroslav.laco@gmail.com
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class PlaceServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    PlaceDao placeDao;

    @Autowired
    @InjectMocks
    PlaceService placeService;

    private Ride ride1;
    private Ride ride2;

    private Place place1;
    private Place place2;

    @BeforeClass
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    void initMethod() {
        place1 = new Place();
        place1.setId(1L);
        place1.setName("Brno");

        place2 = new Place();
        place2.setId(2L);
        place2.setName("Prague");

        User user1 = new User();
        user1.setNickname("nick1");
        user1.setName("Nick");
        user1.setSurname("Cage");

        User user2 = new User();
        user2.setNickname("nick2");
        user2.setName("Nick");
        user2.setSurname("Backstrom");

        ride1 = new Ride();
        ride1.setId(10L);
        ride1.setDriver(user1);
        ride1.setSourcePlace(place1);
        ride1.setDestinationPlace(place2);
        place1.addOriginatingRide(ride1);
        place2.addDestinationRide(ride1);

        ride2 = new Ride();
        ride2.setId(20L);
        ride2.setDriver(user2);
        ride2.setSourcePlace(place2);
        ride2.setDestinationPlace(place1);
        place2.addOriginatingRide(ride2);
        place1.addDestinationRide(ride2);
    }

    @Test
    void createPlaceTest() {
        doNothing().when(placeDao).create(any());

        Place verifyPlace = placeService.createPlace(place1);

        verify(placeDao).create(place1);
        assertThat(verifyPlace).isEqualToComparingFieldByField(place1);
    }

    @Test
    void createNullPlaceTest() {
        //not needed using defensive programming
        doThrow(new IllegalArgumentException()).when(placeDao).create(null);

        assertThatThrownBy(() -> placeService.createPlace(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updatePlaceTest() {
        doNothing().when(placeDao).update(any());

        place1.setName("Bratislava");
        placeService.updatePlace(place1);

        verify(placeDao).update(place1);
    }

    @Test
    void updateNullPlaceTest() {
        //not needed using defensive programming
        doThrow(new IllegalArgumentException()).when(placeDao).update(null);

        assertThatThrownBy(() -> placeService.updatePlace(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void removePlaceTest() {
        doNothing().when(placeDao).delete(any());

        placeService.removePlace(place1);

        verify(placeDao).delete(place1);
    }

    @Test
    void removeNullPlaceTest() {
        //not needed using defensive programming
        doThrow(new IllegalArgumentException()).when(placeDao).delete(null);

        assertThatThrownBy(() -> placeService.removePlace(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findAllTest() {
        List<Place> allPlaces = new ArrayList<>();
        allPlaces.add(place1);
        allPlaces.add(place2);

        when(placeDao.findAll()).thenReturn(allPlaces);

        List<Place> result = placeService.findAll();

        assertThat(result).containsExactlyInAnyOrder(place1, place2);
    }

    @Test
    void findAllNothingFoundTest() {
        when(placeDao.findAll()).thenReturn(new ArrayList<>());

        List<Place> result = placeService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void findByIdTest() {
        when(placeDao.findById(place1.getId())).thenReturn(place1);
        when(placeDao.findById(place2.getId())).thenReturn(place2);

        Place found1 = placeService.findById(place1.getId());
        assertThat(found1).isEqualToComparingFieldByField(found1);

        Place found2 = placeService.findById(place2.getId());
        assertThat(found2).isEqualToComparingFieldByFieldRecursively(place2);
    }

    @Test
    void findByIdNotFoundTest() {
        when(placeDao.findById(any())).thenReturn(null);

        Place result = placeService.findById(-1L);

        assertThat(result).isNull();
    }

    @Test
    void findByIdNullTest() {
        //not needed using defensive programming
        doThrow(new IllegalArgumentException()).when(placeDao).findById(null);

        assertThatThrownBy(() -> placeService.findById(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findByNameTest() {
        when(placeDao.findByName(place1.getName())).thenReturn(place1);
        when(placeDao.findByName(place2.getName())).thenReturn(place2);

        Place found1 = placeService.findByName(place1.getName());
        assertThat(found1).isEqualToComparingFieldByField(found1);

        Place found2 = placeService.findByName(place2.getName());
        assertThat(found2).isEqualToComparingFieldByFieldRecursively(place2);
    }

    @Test
    void findByNameNotFoundTest() {
        when(placeDao.findByName(any())).thenReturn(null);

        Place result = placeService.findByName("not existing name");

        assertThat(result).isNull();
    }

    @Test
    void findByNameNullTest() {
        //not needed using defensive programming
        doThrow(new IllegalArgumentException()).when(placeDao).findByName(null);


    }

    @Test
    void findRidesWithOriginatingAndDestinationPlaceTest() {
        when(placeDao.findById(place1.getId())).thenReturn(place1);
        when(placeDao.findById(place2.getId())).thenReturn(place2);

        List<Ride> fromFirstToSecond =
                placeService.findRidesWithOriginatingAndDestinationPlace(place1.getId(),place2.getId());
        List<Ride> fromSecondToFirst =
                placeService.findRidesWithOriginatingAndDestinationPlace(place2.getId(),place1.getId());

        assertThat(fromFirstToSecond).containsExactly(ride1);
        assertThat(fromSecondToFirst).containsExactly(ride2);
    }

    @Test
    void findRidesWithOriginatingAndDestinationPlaceNonExistingTest() {
        when(placeDao.findById(place1.getId())).thenReturn(place1);
        when(placeDao.findById(place2.getId())).thenReturn(null);

        List<Ride> fromFirstToSecond =
                placeService.findRidesWithOriginatingAndDestinationPlace(place1.getId(),place2.getId());

        assertThat(fromFirstToSecond).isNull();
    }

    @Test
    void findRidesWithOriginatingAndDestinationPlaceEmptyResultTest() {
        when(placeDao.findById(place1.getId())).thenReturn(place1);

        List<Ride> fromFirstToFirst =
                placeService.findRidesWithOriginatingAndDestinationPlace(place1.getId(),place1.getId());

        assertThat(fromFirstToFirst).isNull();
    }
}
