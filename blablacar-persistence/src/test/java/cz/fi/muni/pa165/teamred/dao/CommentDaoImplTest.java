package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.testutils.CommentFactory;
import cz.fi.muni.pa165.teamred.testutils.PlaceFactory;
import cz.fi.muni.pa165.teamred.testutils.RideFactory;
import cz.fi.muni.pa165.teamred.testutils.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.*;
import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Erik Horváth
 */
@Transactional
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class CommentDaoImplTest extends AbstractTestNGSpringContextTests {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CommentDao commentDao;

    private User adam, bob;
    private Ride brnoToPraha, prahaToBrno;
    private Comment positiveComment, negativeComment;

    @BeforeMethod
    void setUp() {
        Place brno = PlaceFactory.createBrno();
        Place praha = PlaceFactory.createPraha();

        adam = UserFactory.createAdam();
        bob = UserFactory.createBob();

        Calendar calendar = Calendar.getInstance();

        calendar.set(2017, Calendar.MARCH, 21);
        brnoToPraha = RideFactory.createRide(brno,
                praha,
                adam,
                calendar.getTime(),
                4,
                250.5,
                new HashSet<>(Collections.singletonList(bob)));

        calendar.set(2015, Calendar.DECEMBER, 2);
        prahaToBrno = RideFactory.createRide(praha,
                brno,
                bob,
                calendar.getTime(),
                2,
                308.45,
                new HashSet<>(Collections.singletonList(adam)));

        positiveComment = CommentFactory.createPositiveComment();
        positiveComment.setAuthor(bob);
        positiveComment.setRide(brnoToPraha);

        negativeComment = CommentFactory.createNegativeComment();
        negativeComment.setAuthor(adam);
        negativeComment.setRide(prahaToBrno);

        entityManager.persist(adam);
        entityManager.persist(bob);

        entityManager.persist(brno);
        entityManager.persist(praha);

        entityManager.persist(brnoToPraha);
        entityManager.persist(prahaToBrno);
    }

    @Test
    void createComment() {
        commentDao.create(positiveComment);

        List<Comment> resultList = entityManager.createQuery("select c from Comment c", Comment.class).getResultList();
        assertThat(resultList).containsExactlyInAnyOrder(positiveComment);
    }

    @Test
    void createMultipleComments() {
        commentDao.create(positiveComment);
        commentDao.create(negativeComment);

        List<Comment> resultList = entityManager.createQuery("select c from Comment c", Comment.class).getResultList();
        assertThat(resultList).containsExactlyInAnyOrder(positiveComment, negativeComment);
    }

    @Test
    void createCommentNull() {
        assertThatThrownBy(() -> commentDao.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createCommentNullText() {
        positiveComment.setText(null);

        assertThatThrownBy(() -> commentDao.create(positiveComment)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void createCommentNullCreationDate() {
        positiveComment.setCreated(null);

        assertThatThrownBy(() -> commentDao.create(positiveComment)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void createCommentNullAuthor() {
        positiveComment.setAuthor(null);

        assertThatThrownBy(() -> commentDao.create(positiveComment)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void createCommentNullRide() {
        positiveComment.setRide(null);

        assertThatThrownBy(() -> commentDao.create(positiveComment)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void updateComment() {
        entityManager.persist(positiveComment);

        String updatedText = positiveComment.getText() + " and something else here.";
        positiveComment.setText(updatedText);

        commentDao.update(positiveComment);

        Comment comment = entityManager.find(Comment.class, positiveComment.getId());

        assertThat(comment).isNotNull().isEqualToComparingFieldByField(positiveComment);
    }

    @Test
    void updateNonExistingComment() {
        Comment comment = CommentFactory.createNegativeComment();
        comment.setAuthor(adam);
        comment.setRide(prahaToBrno);

        commentDao.update(comment);

        List<Comment> resultList = entityManager.createQuery("select c from Comment c", Comment.class).getResultList();

        assertThat(resultList).containsExactlyInAnyOrder(comment);
    }

    @Test
    void updateCommentNull() {
        assertThatThrownBy(() -> commentDao.update(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findAll() {
        entityManager.persist(positiveComment);
        entityManager.persist(negativeComment);

        List<Comment> resultList = commentDao.findAll();
        assertThat(resultList).containsExactlyInAnyOrder(positiveComment, negativeComment);
    }

    @Test
    void deleteExistingComment() {
        entityManager.persist(positiveComment);
        entityManager.persist(negativeComment);

        commentDao.delete(negativeComment);

        List<Comment> resultList = entityManager.createQuery("select c from Comment c", Comment.class).getResultList();
        assertThat(resultList).containsExactlyInAnyOrder(positiveComment);
    }

    @Test
    void deleteNonExistentComment() {
        entityManager.persist(positiveComment);

        commentDao.delete(negativeComment);

        List<Comment> resultList = entityManager.createQuery("select c from Comment c", Comment.class).getResultList();
        assertThat(resultList).containsExactlyInAnyOrder(positiveComment);
    }

    @Test
    void deleteNull() {
        assertThatThrownBy(() -> commentDao.delete(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findById() {
        entityManager.persist(negativeComment);

        Comment result = commentDao.findById(negativeComment.getId());

        assertThat(result).isEqualToComparingFieldByField(negativeComment);
    }

    @Test
    void findByNonExistentId() {
        Comment result = commentDao.findById(-5L);

        assertThat(result).isNull();
    }

    @Test
    void findByIdNull() {
        assertThatThrownBy(() -> commentDao.findById(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getCommentsWithRideId() {
        entityManager.persist(positiveComment);

        // update ride side too
        positiveComment.getRide().addComment(positiveComment);
        entityManager.merge(positiveComment.getRide());

        List<Comment> resultList = commentDao.getCommentsWithRideId(positiveComment.getRide().getId());

        assertThat(resultList).containsExactlyInAnyOrder(positiveComment);
    }

    @Test
    void getCommentsWithRideIdNoComments() {
        List<Comment> resultList = commentDao.getCommentsWithRideId(brnoToPraha.getId());

        assertThat(resultList).isEmpty();
    }

    @Test
    void getCommentsWithNonExistentRideId() {
        List<Comment> resultList = commentDao.getCommentsWithRideId(-44L);

        assertThat(resultList).isEmpty();
    }

    @Test
    void getCommentsWithRideIdNull() {
        assertThatThrownBy(() -> commentDao.getCommentsWithRideId(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getCommentsWithUserId() {
        entityManager.persist(negativeComment);

        // update author side too
        negativeComment.getAuthor().addComment(negativeComment);
        entityManager.merge(negativeComment.getAuthor());

        List<Comment> resultList = commentDao.getCommentsWithUserId(negativeComment.getAuthor().getId());

        assertThat(resultList).containsExactlyInAnyOrder(negativeComment);
    }

    @Test
    void getCommentsWithNonExistentUserId() {
        List<Comment> resultList = commentDao.getCommentsWithUserId(-89L);

        assertThat(resultList).isEmpty();
    }

    @Test
    void getCommentsWithNullUserId() {
        assertThatThrownBy(() -> commentDao.getCommentsWithUserId(null)).isInstanceOf(IllegalArgumentException.class);
    }
}
