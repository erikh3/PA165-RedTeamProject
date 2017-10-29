package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.teamred.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class UserDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao userDao;

    private User katka = createKatka();

    @BeforeMethod
    void beforeEvery() {
        // init
    }

    @Test
    void test() {
        System.out.println("Ok");
    }

    @SuppressWarnings("WeakerAccess")
    public static User createKatka() {
        User katka = new User();
        katka.setName("Katatarina");
        katka.setSurename("Bohacova");
        katka.setNickname("kati");
        return katka;
    }

    @SuppressWarnings("WeakerAccess")
    public static User createAndrea() {
        User andrea = new User();
        andrea.setName("Andrea");
        andrea.setSurename("Mala");
        andrea.setNickname("andi");
        return andrea;
    }
}
