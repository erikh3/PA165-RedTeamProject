package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.teamred.entity.Driver;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author miroslav.laco@gmail.com
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class DriverDaoImplTest extends AbstractTestNGSpringContextTests {
    
        @Autowired
	private DriverDao driverDao;
    
	@Test
	public void createFindDeleteTest(){
            
            Driver driver1 = new Driver();
            driver1.setCarDescription("AUDI A8");
            driver1.setNote("Test note 1.");
            
            Driver driver2 = new Driver();
            driver2.setCarDescription("BMW 7");
            driver2.setNote("Test note 2.");
            
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
}
