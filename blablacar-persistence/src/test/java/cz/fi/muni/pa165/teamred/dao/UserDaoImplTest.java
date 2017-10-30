package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Šimon on 29.10.2017.
 */

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoImplTest extends AbstractTestNGSpringContextTests {


    @Autowired
    private UserDao userDao;

    private User u1;
    private User u2;
    private User u3;

    @BeforeMethod
    public void createUsers(){
        u1 = new User();
        u2 = new User();
        u3 = new User();

        u1.setName("name1");
        u2.setName("name2");
        u3.setName("name3");

        u1.setSurename("surename1");
        u2.setSurename("surename2");
        u3.setSurename("surename3");

        u1.setNickname("nickname1");
        u2.setNickname("nickname2");
        u3.setNickname("nickname3");

        userDao.create(u1);
        userDao.create(u2);
        userDao.create(u3);
    }


    @Test
    public void delete() {
        Assert.assertNotNull(userDao.findById(u3.getId()));
        userDao.delete(u3);
        Assert.assertNull(userDao.findById(u3.getId()));
    }


    @Test
    public void find() {
        User found = userDao.findById(u1.getId());
        Assert.assertEquals(found.getName(), "name1");
        Assert.assertEquals(found.getSurename(), "surneame1");
        Assert.assertEquals(found.getNickname(), "nickname1");
    }

    @Test
    public void findAll() {
        List<User> found = userDao.findAll();
        Assert.assertEquals(found.size(), 3);
    }



}
