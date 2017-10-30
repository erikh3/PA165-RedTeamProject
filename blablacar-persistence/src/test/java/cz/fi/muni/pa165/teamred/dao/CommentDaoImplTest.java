package cz.fi.muni.pa165.teamred.dao;

import cz.fi.muni.pa165.teamred.entity.Comment;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Unit test with mocked EntityManager
 * @author Erik Horváth
 *
 * This pattern can be seen in multiple open source projects
 * @see <a href="https://github.com/jeffxor/jaymen-code-examples">https://github.com/jeffxor/jaymen-code-examples</a>
 */
public class CommentDaoImplTest {
    private Comment positiveComment, negativeComment;

    private EntityManager entityManager;
    private TypedQuery typedQuery;

    private CommentDaoImpl commentDaoImpl;

    @BeforeMethod
    void before() {
        positiveComment = new Comment();
        positiveComment.setId(2L);
        positiveComment.setText("Very good ride, enjoyed every single bit.");

        negativeComment = new Comment();
        negativeComment.setText("Never again.");

        entityManager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        typedQuery = mock(TypedQuery.class);

        when(entityManager.getTransaction()).thenReturn(transaction);
        commentDaoImpl = new CommentDaoImpl();
        commentDaoImpl.setEntityManager(entityManager);
    }

    @Test
    void createCommentTest() {
        doNothing().when(entityManager).persist(positiveComment);   // tells entityManager what to do

        commentDaoImpl.create(positiveComment);         // execute tested action

        verify(entityManager).persist(positiveComment); // verifies that persist() was called with positiveComment as argument
    }

    @Test
    void createCommentNullTest() {
        assertThatThrownBy(() -> commentDaoImpl.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findAll() {
        final List<Comment> expectedResult = Arrays.asList(positiveComment, negativeComment);
        when(typedQuery.getResultList()).thenReturn(expectedResult);
        when(entityManager.createQuery(eq("SELECT c FROM Comment c"), eq(Comment.class))).thenReturn(typedQuery);

        List<Comment> result = commentDaoImpl.findAll();

        assertThat(result).containsExactlyInAnyOrder(positiveComment, negativeComment);
    }

    @Test
    void deleteExistingComment() {
        when(entityManager.contains(positiveComment)).thenReturn(true);
        doNothing().when(entityManager).remove(positiveComment);

        commentDaoImpl.delete(positiveComment);

        verify(entityManager).remove(positiveComment);
    }

    @Test
    void deleteNonExistentComment() {
        when(entityManager.contains(positiveComment)).thenReturn(false);
        when(entityManager.merge(positiveComment)).thenReturn(positiveComment);
        doNothing().when(entityManager).remove(positiveComment);

        commentDaoImpl.delete(positiveComment);

        verify(entityManager).remove(positiveComment);
    }

    @Test
    void deleteNull() {
        assertThatThrownBy(() -> commentDaoImpl.delete(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findById() {
        when(entityManager.find(eq(Comment.class), eq(positiveComment.getId()))).thenReturn(positiveComment);

        Comment result = commentDaoImpl.findById(positiveComment.getId());

        assertThat(result).isEqualToComparingFieldByFieldRecursively(positiveComment);
    }

    @Test
    void findByNonExistentId() {
        final Long nonExistentId = 5L;
        when(entityManager.find(eq(Comment.class), eq(nonExistentId))).thenReturn(null);

        Comment result = commentDaoImpl.findById(nonExistentId);

        assertThat(result).isNull();
    }

    @Test
    void findByIdNull() {
        assertThatThrownBy(() -> commentDaoImpl.findById(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getCommentsWithRideId() {
        final Long rideId = 4L;
        final String queryString = "SELECT c FROM Comment c WHERE c.ride.id = :id";
        final List<Comment> expectedResult = Collections.singletonList(positiveComment);
        TypedQuery intermediateQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(eq(queryString), eq(Comment.class))).thenReturn(intermediateQuery);
        when(intermediateQuery.setParameter(matches("id"), eq(rideId))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expectedResult);

        List<Comment> comments = commentDaoImpl.getCommentsWithRideId(rideId);

        assertThat(comments).containsExactlyInAnyOrder(positiveComment);
    }

    @Test
    void getCommentsWithNonExistentRideId() {
        final Long nonExistentId = 6L;
        final String queryString = "SELECT c FROM Comment c WHERE c.ride.id = :id";
        TypedQuery intermediateQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(eq(queryString), eq(Comment.class))).thenReturn(intermediateQuery);
        when(intermediateQuery.setParameter(matches("id"), eq(nonExistentId))).thenThrow(new NoResultException());

        List<Comment> result = commentDaoImpl.getCommentsWithRideId(nonExistentId);

        assertThat(result).isNull();
    }

    @Test
    void getCommentsWithRideIdNull() {
        assertThatThrownBy(() -> commentDaoImpl.getCommentsWithRideId(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getCommentsWithUserId() {
        final Long userId = 6848L;
        final String queryString = "SELECT c FROM Comment c WHERE c.author.id = :id";
        final List<Comment> expectedResult = Collections.singletonList(negativeComment);
        TypedQuery intermediateQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(eq(queryString), eq(Comment.class))).thenReturn(intermediateQuery);
        when(intermediateQuery.setParameter(matches("id"), eq(userId))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expectedResult);

        List<Comment> result = commentDaoImpl.getCommentsWithUserId(userId);

        assertThat(result).containsExactlyInAnyOrder(negativeComment);
    }

    @Test
    void getCommentsWithNonExistentUserId() {
        final Long nonExistentId = 66L;
        final String queryString = "SELECT c FROM Comment c WHERE c.author.id = :id";
        TypedQuery intermediateQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(eq(queryString), eq(Comment.class))).thenReturn(intermediateQuery);
        when(intermediateQuery.setParameter(matches("id"), eq(nonExistentId))).thenThrow(new NoResultException());

        List<Comment> comments = commentDaoImpl.getCommentsWithUserId(nonExistentId);

        assertThat(comments).isNull();
    }

    @Test
    void getCommentsWithNullUserId() {
        assertThatThrownBy(() -> commentDaoImpl.getCommentsWithUserId(null)).isInstanceOf(IllegalArgumentException.class);
    }
}
