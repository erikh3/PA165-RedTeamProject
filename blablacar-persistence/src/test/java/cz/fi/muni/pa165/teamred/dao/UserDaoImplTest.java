package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.testutils.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Šimon Mačejovský
 */

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;


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

        u1.setSurname("surename1");
        u2.setSurname("surename2");
        u3.setSurname("surename3");

        u1.setNickname("nickname1");
        u2.setNickname("nickname2");
        u3.setNickname("nickname3");

        u1.setLoginId("9997");
        u2.setLoginId("9998");
        u3.setLoginId("9999");

        em.persist(u1);
        em.persist(u2);
        em.persist(u3);
    }

    @Test
    public void createNullUserTest() {
        assertThatThrownBy(() -> userDao.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createNullNameTest() {
        User user = UserFactory.createAdam();
        user.setName(null);
        assertThatThrownBy(() -> userDao.create(user)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void createNotUniqueNicknameTest() {
        User user = UserFactory.createAdam();
        user.setNickname(u1.getNickname());
        assertThatThrownBy(() -> userDao.create(user)).isInstanceOf(JpaSystemException.class);
    }

    @Test
    public void createUserTest() {
        User user = UserFactory.createBob();

        userDao.create(user);

        List<User> result = em.createQuery("select u from User u", User.class).getResultList();

        assertThat(result).contains(user);
    }

    @Test
    public void findByIdTest(){
        User user = userDao.findById(u2.getId());

        assertThat(user).isNotNull().isEqualToComparingFieldByField(u2);
    }

    @Test
    public void createNullTest() {
        assertThatThrownBy(() -> userDao.create(null)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void deleteTest() {
        Assert.assertNotNull(userDao.findById(u3.getId()));
        userDao.delete(u3);
        Assert.assertNull(userDao.findById(u3.getId()));
    }

    @Test
    public void deleteNullTest() {
        assertThatThrownBy(() -> userDao.delete(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByIdNullTest() {
        assertThatThrownBy(() -> userDao.findById(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByIdNonExistingTest() {
        User user = userDao.findById(-1L);
        assertThat(user).isNull();
    }

    @Test
    public void findAllTest() {
        List<User> found = userDao.findAll();
        assertThat(found).containsExactlyInAnyOrder(u1, u2, u3);
    }

    @Test
    public void updateUserTest() {
        u1.setNickname("newNickName");

        userDao.update(u1);

        User user = em.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", u1.getId())
                .getSingleResult();
        assertThat(user).isNotNull().isEqualToComparingFieldByField(u1);
    }

    @Test
    public void updateNullUserTest() {
        assertThatThrownBy(() -> userDao.update(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByNickNameTest() {
        User user = userDao.findByNickname(u1.getNickname());

        assertThat(user).isNotNull().isEqualToComparingFieldByField(u1);
    }

    @Test
    public void findByNonExistingNicknameTest() {
        User user = userDao.findByNickname("_NON EXISTENT_");

        assertThat(user).isNull();
    }

    @Test
    public void findByNullNickNameTest() {
        assertThatThrownBy(() -> userDao.findByNickname(null)).isInstanceOf(IllegalArgumentException.class);
    }
}
