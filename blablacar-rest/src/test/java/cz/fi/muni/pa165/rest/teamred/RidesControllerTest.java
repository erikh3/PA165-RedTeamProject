package cz.fi.muni.pa165.rest.teamred;

import cz.fi.muni.pa165.teamred.dto.PlaceDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.dto.UserDTO;
import cz.fi.muni.pa165.teamred.facade.RideFacade;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import cz.fi.muni.pa165.teamred.rest.RootWebContext;
import cz.fi.muni.pa165.teamred.rest.controllers.RidesController;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Šimon Mačejovský
 */
@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class RidesControllerTest extends AbstractTestNGSpringContextTests {
    @Mock
    private RideFacade rideFacade;

    @Mock
    private UserFacade userFacade;

    @Inject
    @InjectMocks
    private RidesController ridesController;

    private MockMvc mockMvc;

    @BeforeClass
    void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(ridesController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    void getAllRides() throws Exception {
        doReturn(Collections.unmodifiableList(this.createRides())).when(rideFacade).getAllRides();

        mockMvc.perform(get("/rides"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].availableSeats").value(4))
                .andExpect(jsonPath("$.[?(@.id==1)].seatPrice").value(124.8))
                .andExpect(jsonPath("$.[?(@.id==2)].availableSeats").value(2))
                .andExpect(jsonPath("$.[?(@.id==2)].seatPrice").value(200.0));
    }

    @Test
    void getRidesWithDriver() throws Exception {
        doReturn(Collections.unmodifiableList(this.createRides())).when(userFacade).getUserRidesAsDriver(anyLong());

        mockMvc.perform(get("/rides/by-driver/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].availableSeats").value(4))
                .andExpect(jsonPath("$.[?(@.id==1)].seatPrice").value(124.8))
                .andExpect(jsonPath("$.[?(@.id==2)].availableSeats").value(2))
                .andExpect(jsonPath("$.[?(@.id==2)].seatPrice").value(200.0));
    }

    @Test
    void getRidesWithPassenger() throws Exception {
        doReturn(Collections.unmodifiableList(this.createRides())).when(userFacade).getUserRidesAsPassenger(anyLong());

        mockMvc.perform(get("/rides/by-passenger/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].availableSeats").value(4))
                .andExpect(jsonPath("$.[?(@.id==1)].seatPrice").value(124.8))
                .andExpect(jsonPath("$.[?(@.id==2)].availableSeats").value(2))
                .andExpect(jsonPath("$.[?(@.id==2)].seatPrice").value(200.0));
    }

    @Test
    void getRide() throws Exception {
        List<RideDTO> rides = this.createRides();
        doReturn(rides.get(0)).when(rideFacade).getRideWithId(rides.get(0).getId());
        doReturn(rides.get(1)).when(rideFacade).getRideWithId(rides.get(1).getId());

        mockMvc.perform(get("/rides/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.availableSeats").value(4))
                .andExpect(jsonPath("$.seatPrice").value(124.8));

        mockMvc.perform(get("/rides/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.availableSeats").value(2))
                .andExpect(jsonPath("$.seatPrice").value(200.0));
    }

    @Test
    void deleteRideTest() throws Exception {
        doNothing().when(rideFacade).deleteRide(anyLong());

        mockMvc.perform(delete("/rides/1"))
                .andExpect(status().isOk());

        verify(rideFacade).deleteRide(1L);
    }

    @Test
    void createRide() throws Exception {
        UserDTO driver = new UserDTO();
        driver.setId(4L);

        UserDTO passenger = new UserDTO();
        passenger.setId(7L);

        RideDTO ride = new RideDTO();
        ride.setDriver(driver);
        ride.addPassenger(passenger);
        ride.setId(144L);
        ride.setAvailableSeats(1);
        ride.setSeatPrice(140.5);
        Calendar date = Calendar.getInstance();
        date.set(2016, 6, 20, 20, 0, 0);
        ride.setDeparture(date.getTime());

        doReturn(ride.getId()).when(rideFacade).createRide(any());
        doReturn(ride).when(rideFacade).getRideWithId(ride.getId());

        mockMvc.perform(post("/rides/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{" +
                        "\"driverId\": 1," +
                        "\"sourcePlaceId\": 1," +
                        "\"destinationPlaceId\": 2," +
                        "\"seatPrize\": 140.5," +
                        "\"seatsAvailable\": 1" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(144L))
                .andExpect(jsonPath("driver.id").value(4L))
                .andExpect(jsonPath("passengers[0].id").value(7L))
                .andExpect(jsonPath("seatPrice").value(140.5))
                .andExpect(jsonPath("availableSeats").value(1));
    }

    @Test
    void addPassenger() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);

        RideDTO ride = new RideDTO();
        ride.setId(5L);
        ride.addPassenger(user);

        doNothing().when(rideFacade).addPassenger(any());
        doReturn(ride).when(rideFacade).getRideWithId(ride.getId());

        mockMvc.perform(post("/rides/add-passenger")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{" +
                        "\"rideId\": 5," +
                        "\"passengerId\": 1" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(5L))
                .andExpect(jsonPath("passengers[0].id").value(1L));
    }

    @Test
    void removePassenger() throws Exception {
        RideDTO ride = new RideDTO();
        ride.setId(5L);

        doNothing().when(rideFacade).removePassenger(any());
        doReturn(ride).when(rideFacade).getRideWithId(ride.getId());

        mockMvc.perform(post("/rides/remove-passenger")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{" +
                        "\"rideId\": 5," +
                        "\"passengerId\": 1" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(5L))
                .andExpect(jsonPath("passengers.length()").value(0));
    }

    @Test
    void changePrice() throws Exception {
        RideDTO ride = new RideDTO();
        ride.setId(5L);
        ride.setSeatPrice(100.15);

        doNothing().when(rideFacade).removePassenger(any());
        doReturn(ride).when(rideFacade).getRideWithId(ride.getId());

        mockMvc.perform(post("/rides/change-price")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{" +
                        "\"rideId\": 5," +
                        "\"price\": 100.15" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(5L))
                .andExpect(jsonPath("seatPrice").value(100.15));
    }

    @Test
    void changeAvailableSeats() throws Exception {
        RideDTO ride = new RideDTO();
        ride.setId(5L);
        ride.setAvailableSeats(7);

        doNothing().when(rideFacade).removePassenger(any());
        doReturn(ride).when(rideFacade).getRideWithId(ride.getId());

        mockMvc.perform(post("/rides/change-seats")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{" +
                        "\"rideId\": 5," +
                        "\"seats\": 7" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(5L))
                .andExpect(jsonPath("availableSeats").value(7));
    }

    private List<RideDTO> createRides() {
        PlaceDTO brno = new PlaceDTO();
        brno.setId(4L);
        brno.setName("Brno");

        PlaceDTO praha = new PlaceDTO();
        praha.setId(48L);
        praha.setName("Praha");

        RideDTO brnoToPraha = new RideDTO();
        brnoToPraha.setId(1L);
        brnoToPraha.setAvailableSeats(4);
        brnoToPraha.setSeatPrice(124.8);
        brnoToPraha.setSourcePlace(brno);
        brno.addOriginatingRide(brnoToPraha.getId());
        brnoToPraha.setDestinationPlace(praha);
        praha.addDestinationRide(brnoToPraha.getId());

        RideDTO prahaToBrno = new RideDTO();
        prahaToBrno.setId(2L);
        prahaToBrno.setAvailableSeats(2);
        prahaToBrno.setSeatPrice(200.0);
        prahaToBrno.setSourcePlace(praha);
        praha.addOriginatingRide(praha.getId());
        prahaToBrno.setDestinationPlace(brno);
        brno.addDestinationRide(prahaToBrno.getId());

        return Arrays.asList(prahaToBrno, brnoToPraha);
    }
}