package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.teamred.entity.Driver;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author miroslav.laco@gmail.com
 */
@Transactional
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class DriverDaoImplTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private DriverDao driverDao;
        
    private Driver driver1, driver2;
    
    @BeforeMethod
    void init() {
        driver1 = new Driver();
        driver1.setCarDescription("AUDI A8");
        driver1.setNote("Test note 1.");
        driver1.setName("Jan");
        driver1.setSurename("Mrkva");
        driver1.setNickname("janci");

        driver2 = new Driver();
        driver2.setCarDescription("BMW 7");
        driver2.setNote("Test note 2.");
        driver2.setName("Cyprian");
        driver2.setSurename("Kaluznik");
        driver2.setNickname("kaluza");
    }
    
    @Test
    public void createFindDeleteTest(){
        driverDao.create(driver1);
        driverDao.create(driver2);

        assertThat(driverDao.findById(driver1.getId()).getCarDescription())
                .isEqualTo("AUDI A8");
        assertThat(driverDao.findAll())
                .containsExactlyInAnyOrder(driver1,driver2);

        driverDao.delete(driver2);

        assertThat(driverDao.findAll())
                .containsExactly(driver1);
    }
    
    @Test
    public void createNullTest() {
        assertThatThrownBy(() -> driverDao.create(null)).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void deleteNullTest() {
        assertThatThrownBy(() -> driverDao.delete(null)).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void findByIdNullTest() {
        assertThatThrownBy(() -> driverDao.findById(null)).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void findByIdNonExistingTest() {
        Driver driverFound = driverDao.findById(-1L);
        assertThat(driverFound).isNull();
    }
}
