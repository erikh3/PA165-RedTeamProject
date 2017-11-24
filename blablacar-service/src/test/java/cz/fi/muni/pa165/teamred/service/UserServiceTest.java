package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.UserDao;
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

/**
 * @author Jozef Cibík
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    private User validUser;
    private User invalidUser;

    private Ride validRide;
    private Ride invalidRide;

    private Comment validComment;
    private Comment invalidComment;

    private Calendar calendar = Calendar.getInstance();


    @BeforeClass
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    void initMethod() {
        calendar.set(2017, Calendar.NOVEMBER, 7);

        //User Inits
        validUser = new User();
        validUser.setNickname("j_doe");
        validUser.setName("John");
        validUser.setSurname("Doe");
        validUser.setId(1L);

        invalidUser = new User();
        invalidUser.setNickname("b_barman");
        invalidUser.setName("Billy");
        invalidUser.setSurname("Barman");
        invalidUser.setId(2L);

        //Place Inits
        Place validPlaceSource = new Place();
        validPlaceSource.setId(1L);
        validPlaceSource.setName("Brno");

        Place validPlaceDest = new Place();
        validPlaceDest.setId(2L);
        validPlaceDest.setName("Blava");

        //Ride Inits
        validRide = new Ride();
        validRide.setSeatPrice(20.0);
        validRide.setDestinationPlace(validPlaceDest);
        validRide.setSourcePlace(validPlaceSource);
        validRide.setAvailableSeats(4);
        calendar.add(Calendar.DATE,5);
        validRide.setDeparture((calendar.getTime()));
        validRide.setId(1L);
        validRide.setDriver(new User()); //It is not required by this service to control this users attributed through ride


        invalidRide = new Ride();
        invalidRide.setSeatPrice(20.0);
        invalidRide.setDestinationPlace(validPlaceDest);
        invalidRide.setSourcePlace(validPlaceSource);
        invalidRide.setAvailableSeats(4);
        invalidRide.setDeparture(calendar.getTime());
        invalidRide.setId(1L);


        //Comment Inits
        validComment = new Comment();
        validComment.setId(1L);
        validComment.setCreated(calendar.getTime());
        validComment.setText("Hello from Valid Comment");

        invalidComment = new Comment();
        invalidComment.setId(2L);
        invalidComment.setCreated(calendar.getTime());
        invalidComment.setText("Hello from Invalid Comment");

    }

    //______________________________________________________________________________________________________Create Tests
    @Test
    void testCreateUserValid() {
        doNothing().when(userDao).create(any());
        User createdUser = userService.createUser(validUser);

        verify(userDao).create(validUser);

        assertThat(createdUser).isEqualToComparingFieldByField(validUser);
    }

    @Test
    void testCreateNullUser() {
        assertThatThrownBy(() -> userService.createUser(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testCreateUserNullName() {
        invalidUser.setName(null);

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testCreateUserNullNickname() {
        invalidUser.setNickname(null);

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void testCreateUserNullSurname() {
        invalidUser.setSurname(null);

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void testCreateUserEmptyName() {
        invalidUser.setName("");

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testCreateUserEmptyNickname() {
        invalidUser.setNickname("");

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void testCreateUserEmptySurname() {
        invalidUser.setSurname("");

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    //______________________________________________________________________________________________________Update Tests
    @Test
    void testUpdateValidUserName(){
        String newName = "JohnnyD";

        doAnswer( invocation -> {
            validUser.setName(newName);
            return null;
        }).when(userDao).update(any());

        userService.editUser(validUser);
        assertThat(validUser.getName()).isEqualTo(newName);
    }


    @Test
    void testUpdateValidUserSurname(){
        String newSurname = "Smitty";

        doAnswer( invocation -> {
            validUser.setSurname(newSurname);
            return null;
        }).when(userDao).update(any());

        userService.editUser(validUser);
        assertThat(validUser.getSurname()).isEqualTo(newSurname);
    }


    @Test
    void testUpdateValidUserNickname(){
        String newNickname = "predatorX";

        doAnswer( invocation -> {
            validUser.setNickname(newNickname);
            return null;
        }).when(userDao).update(any());

        userService.editUser(validUser);
        assertThat(validUser.getNickname()).isEqualTo(newNickname);
    }


    @Test
    void testUpdateNullUser(){
        assertThatThrownBy(() -> userService.editUser(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testUpdateNullUserName(){
        invalidUser.setName(null);

        assertThatThrownBy(() -> userService.editUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testUpdateNullUserSurname(){
        invalidUser.setSurname(null);

        assertThatThrownBy(() -> userService.editUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testUpdateNullUserNickname(){
        invalidUser.setNickname(null);

        assertThatThrownBy(() -> userService.editUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    //________________________________________________________________________________________________________Find Tests
    @Test
    void testFindAllUsers(){
        List<User> userList = new ArrayList<>();
        userList.add(validUser);

        when(userDao.findAll()).thenReturn(userList);

        List<User> resultUserList = (List<User>) userService.findAllUsers();

        assertThat(resultUserList).containsExactlyInAnyOrder(validUser);
    }

    @Test
    void testFindUserById(){
        when(userDao.findById(validUser.getId())).thenReturn(validUser);

        User foundUser = userService.findUserById(validUser.getId());
        assertThat(foundUser).isEqualToComparingFieldByField(validUser);
    }

    @Test
    void testFindAllUserRidesAsDriver(){
        when(userDao.findById(validUser.getId())).thenReturn(validUser);

        validUser.addRideAsDriver(validRide);

        assertThat(userService.getUserRidesAsDriver(validUser.getId())).containsExactly(validRide);
    }

    @Test
    void testFindAllUserRidesAsPassenger(){
        when(userDao.findById(validUser.getId())).thenReturn(validUser);

        validUser.addRideAsPassenger(validRide);

        assertThat(userService.getUserRidesAsPassenger(validUser.getId())).containsExactly(validRide);
    }

    //______________________________________________________________________________________________________Delete Tests
    @Test
    void testDeleteUser(){
        when(userDao.findById(validUser.getId())).thenReturn(validUser);

        doAnswer( invocation -> {
            validUser.setId(null);
            return null;
        }).when(userDao).delete(validUser);

        userService.deleteUser(validUser);

        verify(userDao).delete(validUser);
        assertThat(validUser.getId()).isNull();
    }

    @Test
    void testDeleteNullUser(){
        assertThatThrownBy(() -> userService.deleteUser(null)).isInstanceOf(IllegalArgumentException.class);
    }
}
