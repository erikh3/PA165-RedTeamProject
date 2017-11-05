package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import cz.fi.muni.pa165.teamred.entity.Place;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 *
 * @author miroslav.laco@gmail.com
 */
@Transactional
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class PlaceDaoImplTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private PlaceDao placeDao;

    @PersistenceContext
    private EntityManager em;
    
    private Place place1, place2;
    
    @BeforeMethod
    void init() {
        place1 = new Place();
        place1.setName("Brno");
        
        place2 = new Place();
        place2.setName("Praha");

        em.persist(place1);
        em.persist(place2);
        em.flush();
    }

    @Test
    public void createTest() {
        Place testPlace = new Place("Jicin");
        placeDao.create(testPlace);

        Place foundPlace = em.find(Place.class, testPlace.getId());
        assertThat(foundPlace).isNotNull().isEqualTo(testPlace);
    }

    @Test
    public void createNullPlaceTest() {
        assertThatThrownBy(() -> placeDao.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createNullPlaceNameTest() {
        Place testPlace = new Place();
        testPlace.setName(null);
        assertThatThrownBy(() -> placeDao.create(testPlace))
                        .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void createNotUniquePlaceNameTest() {
        Place testPlace = new Place(place1.getName());
        assertThatThrownBy(() -> placeDao.create(testPlace))
                 .isInstanceOf(PersistenceException.class);
    }

    @Test
    public void createExistingPlaceTest() {
        List<Place> listBeforeCreate = em.createQuery("SELECT p FROM Place p", Place.class).getResultList();
        placeDao.create(place1);

        //No change in DB
        assertThat(em.createQuery("SELECT p FROM Place p", Place.class).getResultList()).isEqualTo(listBeforeCreate);
    }

    @Test
    public void deleteTest() {
        placeDao.delete(place1);
        assertThat(em.createQuery("SELECT p FROM Place p", Place.class).getResultList()).containsExactly(place2);
    }

    @Test
    public void deleteNullPlaceTest() {
        assertThatThrownBy(() -> placeDao.delete(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteNotExistingPlaceTest() {
        Place testPlace = new Place("nowhere");
        placeDao.delete(testPlace);
        assertThat(em.createQuery("SELECT p FROM Place p", Place.class).getResultList())
                .containsExactly(place1, place2);
    }

    @Test
    public void updatePlaceNameTest() {
        String newName = "newPlaceName";
        Place foundPlace = em.find(Place.class, place1.getId());

        foundPlace.setName(newName);
        placeDao.update(foundPlace);
        
        assertThat(em.find(Place.class, place1.getId()).getName()).isEqualTo(newName);
    }

    @Test
    public void updateNullPlaceTest() {
        assertThatThrownBy(() -> placeDao.update(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateNullPlaceNameTest() {
        Place foundPlace = em.find(Place.class, place1.getId());
        foundPlace.setName(null);
        placeDao.update(foundPlace);
        assertThatThrownBy(() ->  em.flush()).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void findAllTest() {
        assertThat(placeDao.findAll())
                .containsExactly(place1,place2);
    }

    @Test
    public void findByIdTest() {
        Place placeFound = placeDao.findById(place1.getId());
        assertThat(placeFound).isEqualTo(place1);
    }
    
    @Test
    public void findByIdNullTest() {
        assertThatThrownBy(() -> placeDao.findById(null)).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void findByIdNonExistingTest() {
        Place placeFound = placeDao.findById(-1L);
        assertThat(placeFound).isNull();
    }

    @Test
    public void findByNameTest() {
        Place placeFound = placeDao.findByName(place1.getName());
        assertThat(placeFound).isEqualTo(place1);
    }

    @Test
    public void findByNameNullTest() {
        assertThatThrownBy(() -> placeDao.findByName(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByNameNonExistingTest() {
        Place placeFound = placeDao.findByName("New York");
        assertThat(placeFound).isNull();
    }
}
