package cz.fi.muni.pa165.teamred.sampledata;

import cz.fi.muni.pa165.teamred.dao.CommentDao;
import cz.fi.muni.pa165.teamred.dao.PlaceDao;
import cz.fi.muni.pa165.teamred.dao.RideDao;
import cz.fi.muni.pa165.teamred.dao.UserDao;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Erik Horváth
 */
@ContextConfiguration(classes = {BlablacarWithSampleDataConfiguration.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SampleDataLoadingFacadeTest extends AbstractTestNGSpringContextTests {
    @Inject
    private PlaceDao placeDao;

    @Inject
    private UserDao userDao;

    @Inject
    private RideDao rideDao;

    @Inject
    private CommentDao commentDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    void createSampleData() throws IOException {
        List<Place> placeList = placeDao.findAll();
        assertThat(placeList).isNotNull();
        assertThat(placeList.size()).isEqualTo(5);

        List<User> userList = userDao.findAll();
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(4);

        List<Ride> rideList = rideDao.findAll();
        assertThat(rideList).isNotNull();
        assertThat(rideList.size()).isEqualTo(4);

        List<Comment> commentList = commentDao.findAll();
        assertThat(commentList).isNotNull();
        assertThat(commentList.size()).isEqualTo(4);
    }
}
