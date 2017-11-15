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
import java.util.Date;
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
    UserDao userDao;

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

    @Test
    void testCreateUserValid() {
        doNothing().when(userDao).create(any());

        User createdUser = userService.createUser(validUser);

        verify(userDao).create(validUser);

        assertThat(createdUser).isEqualToComparingFieldByField(validUser);
    }

    //______________________________________________________________________________________________________Create Tests
    @Test
    void testCreateNullUser() {
        doThrow(new IllegalArgumentException()).when(userDao).create(null);
        assertThatThrownBy(() -> userService.createUser(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testCreateUserNullName() {
        doThrow(new IllegalArgumentException()).when(userDao).create(any());

        invalidUser.setName(null);

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testCreateUserNullNickname() {
        doThrow(new IllegalArgumentException()).when(userDao).create(any());

        invalidUser.setNickname(null);

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void testCreateUserNullSurname() {
        doThrow(new IllegalArgumentException()).when(userDao).create(any());

        invalidUser.setSurname(null);

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void testCreateUserEmptyName() {
        doThrow(new IllegalArgumentException()).when(userDao).create(any());

        invalidUser.setName("");

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testCreateUserEmptyNickname() {
        doThrow(new IllegalArgumentException()).when(userDao).create(any());

        invalidUser.setNickname("");

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void testCreateUserEmptySurname() {
        doThrow(new IllegalArgumentException()).when(userDao).create(any());

        invalidUser.setSurname("");

        assertThatThrownBy(() -> userService.createUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    //______________________________________________________________________________________________________Update Tests
    @Test
    void testUpdateValidUserName(){
        doNothing().when(userDao).update(any());

        validUser.setName("Ivan");
        userService.editUser(validUser);

        verify(userDao).update(validUser);
    }


    @Test
    void testUpdateValidUserSurname(){
        doNothing().when(userDao).update(any());

        validUser.setSurname("Clemetine");
        userService.editUser(validUser);

        verify(userDao).update(validUser);
    }


    @Test
    void testUpdateValidUserNickname(){
        doNothing().when(userDao).update(any());

        validUser.setNickname("jdo_e");
        userService.editUser(validUser);

        verify(userDao).update(validUser);
    }


    @Test
    void testUpdateNullUser(){
        doThrow(new IllegalArgumentException()).when(userDao).update(null);
        assertThatThrownBy(() -> userService.editUser(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testUpdateNullUserName(){
        doThrow(new IllegalArgumentException()).when(userDao).update(any());
        invalidUser.setName(null);
        assertThatThrownBy(() -> userService.editUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testUpdateNullUserSurname(){
        doThrow(new IllegalArgumentException()).when(userDao).update(any());
        invalidUser.setSurname(null);
        assertThatThrownBy(() -> userService.editUser(invalidUser)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testUpdateNullUserNickname(){
        doThrow(new IllegalArgumentException()).when(userDao).update(any());
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
        when(userDao.findById(anyLong())).thenReturn(validUser);

        User foundUser = userService.findUserById(1L);
        assertThat(foundUser).isEqualToComparingFieldByField(validUser);
    }

    //______________________________________________________________________________________________________Delete Tests
    @Test
    void testDeleteUser(){
        doNothing().when(userDao).delete(validUser);

        userService.deleteUser(validUser);

        verify(userDao).delete(validUser);
    }

    @Test
    void testDeleteNullUser(){
        doThrow(new IllegalArgumentException()).when(userDao).delete(null);

        assertThatThrownBy(() -> userService.deleteUser(null)).isInstanceOf(IllegalArgumentException.class);
    }


    //________________________________________________________________________________________________________Adds Tests
    @Test
    void testAddUserValidRideAsPassenger() {
        userService.addUserRideAsPassenger(validUser,validRide);
        assertThat(validUser.getRidesAsPassenger().contains(validRide));
    }

    @Test
    void testAddUserValidRideAsDriver() {
        userService.addUserRideAsDriver(validUser,validRide);
        assertThat(validUser.getRidesAsDriver().contains(validRide));
    }

    @Test
    void testAddUserValidComment() {
        userService.addUserComment(validUser, validComment);
        assertThat(validUser.getUserComments().contains(validComment));
    }

    @Test
    void testAddUserExistingRideAsPassenger() {

    }

    @Test
    void testAddUserExistingRideAsDriver() {

    }

    @Test
    void testAddUserExistingComment() {

    }

    @Test
    void testAddUserRideAsPassenger() {

    }

    @Test
    void testAddUserRideAsDriver() {

    }

    @Test
    void testAddUserComment() {

    }


    @Test
    void testAddUserNullComment() {

    }


    @Test
    void testAddUserCommentWithAuthor() {

    }


    @Test
    void testAddUserCommentNullText() {

    }


    @Test
    void testAddUserCommentEmptyText() {

    }

    //______________________________________________________________________________________________________Remove Tests
    @Test
    void testRemoveUserRideAsPassenger() {

    }

    @Test
    void testRemoveUserRideAsDriver() {

    }

    @Test
    void testRemoveUserComment() {

    }

    @Test
    void testRemoveUserNonExistingRideAsPassenger() {

    }

    @Test
    void testRemoveUserNonExistingRideAsDriver() {

    }

    @Test
    void testRemoveUserNonExistingComment() {

    }

}
