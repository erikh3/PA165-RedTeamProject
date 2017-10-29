package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.teamred.entity.*;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Erik Horváth
 */
@Transactional
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class CommentDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RideDao rideDao;

    @PersistenceContext
    private EntityManager em;

    private Comment positiveComment;

    private Date comment1Date;

    private User katka;

    private Ride brnoToPraha;

    @BeforeMethod
    void createComments() {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, Calendar.FEBRUARY, 3);
        comment1Date = cal.getTime();

        katka = UserDaoTest.createKatka();

        userDao.create(katka);

        brnoToPraha = RideDaoTest.createRideBrnoToPraha();

        rideDao.create(brnoToPraha);

        positiveComment = new Comment();

        positiveComment.setText("Very good ride, enjoyed every single bit.");
        positiveComment.setCreated(comment1Date);
        positiveComment.setAuthor(katka);
        positiveComment.setRide(brnoToPraha);

        commentDao.create(positiveComment);
        em.flush();
    }

    @Test
    void findAll() {
        List<Comment> comments = commentDao.findAll();
        assertThat(comments).containsExactlyInAnyOrder(positiveComment);
    }

    @Test
    void delete() {
        commentDao.delete(positiveComment);

        List<Comment> comments = getAllComments();
        assertThat(comments).isEmpty();
    }

    @Test
    void deleteNonExistent() {
        Comment comment = new Comment();

        commentDao.delete(comment);

        List<Comment> comments = getAllComments();
        assertThat(comments).containsExactlyInAnyOrder(positiveComment);
    }

    @Test
    void deleteNull() {
        assertThatThrownBy(() -> commentDao.delete(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findById() {
        Comment result = commentDao.findById(positiveComment.getId());
        assertThat(result).isEqualToComparingFieldByFieldRecursively(positiveComment);
    }

    @Test
    void findByNonExistentId() {
        Comment result = commentDao.findById(-15L);
        assertThat(result).isNull();
    }

    @Test
    void findByIdNull() {
        assertThatThrownBy(() -> commentDao.findById(null)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void getCommentsWithRideId() {
        List<Comment> comments = commentDao.getCommentsWithRideId(brnoToPraha.getId());

        assertThat(comments).containsExactlyInAnyOrder(positiveComment);
    }

    @Test
    void getCommentsWithNonExistentRideId() {
        List<Comment> comments = commentDao.getCommentsWithRideId(-6L);

        assertThat(comments).isEmpty();
    }

    @Test
    void getCommentsWithRideIdNull() {
        assertThatThrownBy(() -> commentDao.getCommentsWithRideId(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void getCommentsWithUserId() {
        List<Comment> comments = commentDao.getCommentsWithUserId(katka.getId());

        assertThat(comments).containsExactlyInAnyOrder(positiveComment);
    }

    @Test
    void getCommentsWithNonExistentUserId() {
        List<Comment> comments = commentDao.getCommentsWithUserId(katka.getId());

        assertThat(comments).isEmpty();
    }

    @Test
    void getCommentsWithNullUserId() {
        assertThatThrownBy(() -> commentDao.getCommentsWithUserId(null)).isInstanceOf(IllegalArgumentException.class);
    }

    private List<Comment> getAllComments() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }
}
