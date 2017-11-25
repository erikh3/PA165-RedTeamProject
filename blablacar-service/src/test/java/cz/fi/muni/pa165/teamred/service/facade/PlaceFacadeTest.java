package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.PlaceCreateDTO;
import cz.fi.muni.pa165.teamred.dto.PlaceDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.service.BeanMappingService;
import cz.fi.muni.pa165.teamred.service.PlaceService;
import cz.fi.muni.pa165.teamred.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author miroslav.laco@gmail.com
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class PlaceFacadeTest {

    @Mock
    BeanMappingService beanMappingService;

    @Mock
    PlaceService placeService;

    @Autowired
    @InjectMocks
    PlaceFacadeImpl placeFacade;

    private RideDTO rideDTO1;
    private RideDTO rideDTO2;

    private PlaceDTO placeDTO1;
    private PlaceDTO placeDTO2;

    private Place place1;
    private Place place2;

    private Ride ride1;
    private Ride ride2;

    private PlaceCreateDTO placeCreate1;
    private PlaceCreateDTO placeCreate2;

    public PlaceFacadeTest() {
    }

    @BeforeClass
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    void initMethod() {

        placeCreate1 = new PlaceCreateDTO();
        placeCreate1.setName("Brno");

        placeDTO1 = new PlaceDTO();
        placeDTO1.setId(1L);
        placeDTO1.setName("Brno");

        place1 = new Place();
        place1.setId(1L);
        place1.setName("Brno");

        placeCreate2 = new PlaceCreateDTO();
        placeCreate2.setName("Prague");

        placeDTO2 = new PlaceDTO();
        placeDTO2.setId(2L);
        placeDTO2.setName("Prague");

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

        Mockito.reset(placeService);
    }

    @Test
    void placeCreateTest() {
        when(beanMappingService.mapTo(placeCreate1, Place.class)).thenReturn(place1);
        when(placeService.createPlace(place1)).thenReturn(place1);

        Long verifyId = placeFacade.createPlace(placeCreate1);

        verify(placeService).createPlace(place1);
        assertThat(verifyId).isEqualTo(place1.getId());
    }

    @Test
    void placeCreateNullTest() {
        assertThatThrownBy(() -> placeFacade.createPlace(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void placeCreateEmptyNameTest() {
        Long verifyId = placeFacade.createPlace(new PlaceCreateDTO());
        assertThat(verifyId).isNull();
    }

    @Test
    void changePlaceNameTest() {
        doNothing().when(placeService).updatePlace(any());
        when(placeService.findById(place1.getId())).thenReturn(place1);

        placeFacade.changePlaceName(place1.getId(),"Breclav");

        place1.setName("Breclav");
        verify(placeService).updatePlace(place1);
    }

    @Test
    void changePlaceNameIdNullTest() {
        assertThatThrownBy(() -> placeFacade.changePlaceName(null,"Breclav"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void changePlaceNameStringNullTest() {
        assertThatThrownBy(() -> placeFacade.changePlaceName(place1.getId(),null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deletePlaceTest() {
        doNothing().when(placeService).removePlace(any());

        placeFacade.deletePlace(place1.getId());

        Place testPlace = new Place();
        testPlace.setId(place1.getId());
        verify(placeService).removePlace(testPlace);
    }

    @Test
    void deletePlaceNullTest() {
        assertThatThrownBy(() -> placeFacade.deletePlace(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getPlaceWithIdTest() {
        when(placeService.findById(place1.getId())).thenReturn(place1);
        when(beanMappingService.mapTo(place1, PlaceDTO.class)).thenReturn(placeDTO1);

        PlaceDTO testPlace = placeFacade.getPlaceWithId(place1.getId());

        verify(placeService).findById(place1.getId());
        assertThat(testPlace).isEqualToComparingFieldByField(placeDTO1);
    }

    @Test
    void getPlaceWithIdNullTest() {
        assertThatThrownBy(() -> placeFacade.getPlaceWithId(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getPlaceWithNameTest() {
        when(placeService.findByName(place1.getName())).thenReturn(place1);
        when(beanMappingService.mapTo(place1, PlaceDTO.class)).thenReturn(placeDTO1);

        PlaceDTO testPlace = placeFacade.getPlaceWithName(place1.getName());

        verify(placeService).findByName(placeDTO1.getName());
        assertThat(testPlace).isEqualToComparingFieldByField(placeDTO1);
    }

    @Test
    void getPlaceWithNameNullTest() {
        assertThatThrownBy(() -> placeFacade.getPlaceWithName(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getRidesWithOriginatingPlaceTest() {
        List<RideDTO> returnRideList = new ArrayList<>();
        returnRideList.add(rideDTO1);

        when(placeService.findById(place1.getId())).thenReturn(place1);
        when(beanMappingService.mapTo(place1.getOriginatingRides(), RideDTO.class)).thenReturn(returnRideList);

        List<RideDTO> testRideList = placeFacade.getRidesWithOriginatingPlace(place1.getId());

        verify(placeService).findById(place1.getId());
        assertThat(testRideList).containsExactly(rideDTO1);
    }

    @Test
    void getRidesWithOriginatingPlaceNullTest() {
        assertThatThrownBy(() -> placeFacade.getRidesWithOriginatingPlace(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getRidesWithDestinationPlaceTest() {
        List<RideDTO> returnRideList = new ArrayList<>();
        returnRideList.add(rideDTO2);

        when(placeService.findById(place1.getId())).thenReturn(place1);
        when(beanMappingService.mapTo(place1.getDestinationRides(), RideDTO.class)).thenReturn(returnRideList);

        List<RideDTO> testRideList = placeFacade.getRidesWithDestinationPlace(place1.getId());

        verify(placeService).findById(place1.getId());
        assertThat(testRideList).containsExactly(rideDTO2);
    }

    @Test
    void getRidesWithDestinationPlaceNullTest() {
        assertThatThrownBy(() -> placeFacade.getRidesWithDestinationPlace(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getRidesWithOriginatingAndDestinationPlaceTest() {
        List<Ride> returnRideList = new ArrayList<>();
        returnRideList.add(ride1);

        List<RideDTO> returnRideDTOList = new ArrayList<>();
        returnRideDTOList.add(rideDTO1);

        when(placeService.findRidesWithOriginatingAndDestinationPlace(place1.getId(),place2.getId()))
                .thenReturn(returnRideList);
        when(beanMappingService.mapTo(returnRideList, RideDTO.class)).thenReturn(returnRideDTOList);

        List<RideDTO> testRideList = placeFacade
                .getRidesWithOriginatingAndDestinationPlace(place1.getId(),place2.getId());

        verify(placeService).findRidesWithOriginatingAndDestinationPlace(place1.getId(),place2.getId());
        assertThat(testRideList).containsExactly(rideDTO1);
    }

    @Test
    void getRidesWithOriginatingAndDestinationPlaceNullTest() {
        assertThatThrownBy(() -> placeFacade
                .getRidesWithOriginatingAndDestinationPlace(place1.getId(), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getAllPlacesTest() {
        List<Place> allPlaces = new ArrayList<>();
        allPlaces.add(place1);
        allPlaces.add(place2);

        List<PlaceDTO> allDtoPlaces = new ArrayList<>();
        allDtoPlaces.add(placeDTO1);
        allDtoPlaces.add(placeDTO2);

        when(placeService.findAll()).thenReturn(allPlaces);
        when(beanMappingService.mapTo(allPlaces,PlaceDTO.class)).thenReturn(allDtoPlaces);

        List<PlaceDTO> testPlaceList = placeFacade.getAllPlaces();

        verify(placeService).findAll();
        assertThat(testPlaceList).containsExactlyInAnyOrder(placeDTO1,placeDTO2);
    }

}
