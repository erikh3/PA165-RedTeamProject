package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Passenger;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PassengerDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceContext
    EntityManager em;

    @SuppressWarnings("WeakerAccess")
    public static Passenger createRastislav() {
        Passenger rastislav = new Passenger();
        rastislav.setName("Rastislav");
        rastislav.setSurename("Ohen");
        rastislav.setNickname("moneyForLifeBB");
        return rastislav;
    }
}
