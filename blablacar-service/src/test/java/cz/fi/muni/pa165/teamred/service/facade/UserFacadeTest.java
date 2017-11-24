package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.UserCreateDTO;
import cz.fi.muni.pa165.teamred.dto.UserDTO;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.service.BeanMappingService;
import cz.fi.muni.pa165.teamred.service.PassengerService;
import cz.fi.muni.pa165.teamred.service.UserService;
import cz.fi.muni.pa165.teamred.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Jozef Cibík on 16.11.2017.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeTest {

    @Mock
    BeanMappingService beanMappingService;

    @Mock
    UserService userService;

    @Mock
    PassengerService passengerService;

    @Autowired
    @InjectMocks
    UserFacadeImpl userFacade;

    private User validUser;

    private Ride validRide;

    private UserCreateDTO userCreateDTO;

    private UserDTO validUserDTO;

    @BeforeClass
    void initMocks() { MockitoAnnotations.initMocks(this); }

    @BeforeMethod
    void init(){

        userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName("John");
        userCreateDTO.setSurname("Doe");
        userCreateDTO.setNickname("j_doe");

        validUser = new User();
        validUser.setName("John");
        validUser.setSurname("Doe");
        validUser.setNickname("j_doe");
        validUser.setId(1L);
        when(beanMappingService.mapTo(userCreateDTO, User.class)).thenReturn(validUser);

        validUserDTO =  new UserDTO();
        validUserDTO.setId(1L);
        validUserDTO.setName("John");
        validUserDTO.setSurname("Doe");
        validUserDTO.setNickname("j_doe");


        Calendar cal = Calendar.getInstance();
        cal.set(2010, Calendar.JULY, 25);

        validRide = new Ride();
        validRide.setId(1L);
        validRide.setSeatPrice(10);
        validRide.setSourcePlace(new Place());
        validRide.setDestinationPlace(new Place());
        validRide.setAvailableSeats(4);
        validRide.setDeparture(cal.getTime());
        validRide.setDriver(new User());

    }

    @Test
    void testCreateUser(){
        when(userService.createUser(validUser)).thenReturn(validUser);

        Long createdId = userFacade.createUser(userCreateDTO);
        verify(userService).createUser(validUser);
        assertThat(createdId).isEqualTo(validUser.getId());
    }

    @Test
    void testEditUser(){
        doNothing().when(userService).editUser(validUser);
        when(userService.findUserById(1L)).thenReturn(validUser);

        validUserDTO.setSurname("Von Neuman");

        userFacade.editUser(validUserDTO);

        validUser.setSurname("Von Neuman");
        verify(userService).editUser(validUser);
    }

    @Test
    void testDeleteUser(){
        doNothing().when(userService).deleteUser(any());
        when(userService.findUserById(1L)).thenReturn(validUser);

        userFacade.removeUser(validUser.getId());
        verify(userService).createUser(validUser);
    }
    //_________________________________________________________________________________________________________Add Tests

    @Test
    void testAddPassengerToRide(){
        doAnswer( invocation -> {
            validUser.addRideAsPassenger(validRide);
            validRide.addPassenger(validUser);
            return null;
        }).when(passengerService).addPassengerToRide(validUser.getId(), validRide.getId());

        userFacade.addRideAsPassenger(validUser.getId(), validRide.getId());

        verify(passengerService).addPassengerToRide(validUser.getId(), validRide.getId());

        assertThat(validUser.getRidesAsPassenger()).contains(validRide);
        assertThat(validRide.getPassengers()).contains(validUser);
    }


    //______________________________________________________________________________________________________Remove Tests

    @Test
    void testRemovePasssengerFromRide(){
        doNothing().when(passengerService).removePassengerFromRide(validUser.getId(), validRide.getId());

        userFacade.removeRideFromPassenger(validUser.getId(), validRide.getId());

        verify(passengerService).removePassengerFromRide(validUser.getId(), validRide.getId());

        assertThat(validUser.getRidesAsPassenger().size()).isEqualTo(0);
        assertThat(validRide.getPassengers().size()).isEqualTo(0);
    }

    //________________________________________________________________________________________________________Find Tests
    @Test
    void testFindAllUsers(){
        List<User> resUsers = new ArrayList<>();
        resUsers.add(validUser);
        when(userService.findAllUsers()).thenReturn(resUsers);

        List<UserDTO> resDTOUser = new ArrayList<>();
        resDTOUser.add(validUserDTO);
        when(beanMappingService.mapTo(resUsers,UserDTO.class)).thenReturn(resDTOUser);

        List<UserDTO> testUserRes = new ArrayList<>(userFacade.getAllUsers());

        verify(userService).findAllUsers();
        assertThat(testUserRes).containsExactlyInAnyOrder(validUserDTO);
    }

    @Test
    void testFindUserById(){
        when(beanMappingService.mapTo(validUser, UserDTO.class)).thenReturn(validUserDTO);
        when(userService.findUserById(1L)).thenReturn(validUser);

        UserDTO resUserDTO = userFacade.findUserById(validUser.getId());
        assertThat(resUserDTO).isEqualToComparingFieldByField(validUserDTO);
    }

    @Test
    void testFindUserByNickname(){
        when(beanMappingService.mapTo(validUser, UserDTO.class)).thenReturn(validUserDTO);
        when(userService.findUserByNickname("j_doe")).thenReturn(validUser);

        UserDTO resUserDTO = userFacade.findUserByNickname(validUser.getNickname());
        assertThat(resUserDTO).isEqualToComparingFieldByField(validUserDTO);
    }

    @Test
    void testFindUserRidesAsDriver(){
    }


    @Test
    void testFindUserRidesAsPassenger(){
    }
}
