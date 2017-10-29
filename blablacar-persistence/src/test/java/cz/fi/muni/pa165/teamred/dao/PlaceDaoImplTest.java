package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import cz.fi.muni.pa165.teamred.entity.Place;

import static org.assertj.core.api.Assertions.assertThat;
/**
 *
 * @author miroslav.laco@gmail.com
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class PlaceDaoImplTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private PlaceDao placeDao;

    @Test
    public void createFindDeleteTest(){

        Place place1 = new Place();
        place1.setName("Brno");

        Place place2 = new Place();
        place2.setName("Praha");

        placeDao.create(place1);
        placeDao.create(place2);

        assertThat(placeDao.findById(place1.getId()).getName())
                .isEqualTo("Brno");
        assertThat(placeDao.findAll())
                .containsExactlyInAnyOrder(place1,place2);

        placeDao.delete(place2);

        assertThat(placeDao.findAll())
                .containsExactly(place1);

    }
}
