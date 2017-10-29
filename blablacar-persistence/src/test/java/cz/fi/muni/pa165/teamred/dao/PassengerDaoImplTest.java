package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.teamred.entity.Passenger;
import cz.fi.muni.pa165.teamred.entity.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

/**
 * Created by Šimon on 29.10.2017.
 */
@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PassengerDaoImplTest extends AbstractTestNGSpringContextTests {


    @Autowired
    private PassengerDao passengerDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RideDao rideDao;

    @Test
    public void findAllRides(){
        Passenger psg = new Passenger();

        Ride r1 = new Ride();
        Ride r2 = new Ride();

        rideDao.create(r1);
        rideDao.create(r2);

        Set<Ride> rides = null;
        rides.add(r1);
        rides.add(r2);

        psg.setRides(rides);

        userDao.create(psg);

        List<Ride> psgRides  = passengerDao.findAllRides();

        Assert.assertEquals(psgRides.size(), 2);


        Assert.assertTrue(psgRides.contains(r1));
        Assert.assertTrue(psgRides.contains(r2));

    }
}
