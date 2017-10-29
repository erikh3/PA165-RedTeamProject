package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Driver;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DriverDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceContext
    EntityManager em;

    @SuppressWarnings("WeakerAccess")
    public static Driver createMilos() {
        Driver milos = new Driver();
        milos.setName("Milos");
        milos.setSurename("Sibal");
        milos.setNickname("XxxEr");
        milos.setCarDescription("trabant");
        milos.setNote("Only humans aboard.");
        return milos;
    }
}
