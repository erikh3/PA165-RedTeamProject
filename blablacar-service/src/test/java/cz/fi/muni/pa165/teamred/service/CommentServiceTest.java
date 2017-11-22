package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dao.CommentDao;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Erik Horváth
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CommentServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private CommentDao commentDao;

    @Mock
    private TimeService timeService;

    @Autowired
    @InjectMocks
    private CommentService commentService;

    private Ride ride1;
    private Ride ride2;

    private User adam;
    private User maros;

    private Comment sampleComment;
    private Comment secondComment;

    @BeforeClass
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    void prepareComment() {
        Calendar calendar = Calendar.getInstance();

        sampleComment = new Comment();
        sampleComment.setId(8L);
        sampleComment.setText("sample text");
        calendar.set(2017, Calendar.NOVEMBER, 8);
        sampleComment.setCreated(calendar.getTime());

        ride1 = new Ride();
        ride1.setId(1L);
        ride1.addComment(sampleComment);
        sampleComment.setRide(ride1);

        adam = new User();
        adam.setId(8L);
        adam.setName("Adam");
        adam.addComment(sampleComment);
        sampleComment.setAuthor(adam);

        secondComment = new Comment();
        secondComment.setId(782L);
        secondComment.setText("second comment");
        calendar.set(2014, Calendar.FEBRUARY, 28);
        secondComment.setCreated(calendar.getTime());

        ride2 = new Ride();
        ride2.setId(2L);
        ride2.addComment(secondComment);
        secondComment.setRide(ride2);

        maros = new User();
        maros.setId(99L);
        maros.setName("Maroš");
        maros.addComment(secondComment);
        secondComment.setAuthor(maros);
    }

    @Test
    void createCommentTest() {
        doNothing().when(commentDao).create(any());

        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.MARCH, 8);
        when(timeService.getCurrentTime()).thenReturn(cal.getTime());

        Comment result = commentService.createComment(sampleComment);

        verify(timeService).getCurrentTime();
        verify(commentDao).create(sampleComment);

        assertThat(result).isEqualToComparingFieldByField(sampleComment);
    }

    @Test
    void createNullCommentTest() {
        doThrow(new IllegalArgumentException()).when(commentDao).create(null);

        assertThatThrownBy(() -> commentService.createComment(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateCommentTest() {
        sampleComment.setText("new text");

        doNothing().when(commentDao).update(sampleComment);

        commentService.updateComment(sampleComment);

        verify(commentDao).update(sampleComment);
    }

    @Test
    void updateNullCommentTest() {
        doThrow(new IllegalArgumentException()).when(commentDao).update(null);

        assertThatThrownBy(() -> commentService.updateComment(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findAllTest() {
        List<Comment> allComments = new ArrayList<>();
        allComments.add(sampleComment);
        allComments.add(secondComment);

        when(commentDao.findAll()).thenReturn(allComments);

        List<Comment> result = commentService.findAll();

        assertThat(result).containsExactlyInAnyOrder(sampleComment, secondComment);
    }

    @Test
    void findAllNothingFoundTest() {
        when(commentDao.findAll()).thenReturn(new ArrayList<>());

        List<Comment> result = commentService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void findByIdTest() {
        when(commentDao.findById(sampleComment.getId())).thenReturn(sampleComment);
        when(commentDao.findById(secondComment.getId())).thenReturn(secondComment);

        Comment shouldBeSample = commentService.findById(sampleComment.getId());
        assertThat(shouldBeSample).isEqualToComparingFieldByField(shouldBeSample);

        Comment shouldBeSecond = commentService.findById(secondComment.getId());
        assertThat(shouldBeSecond).isEqualToComparingFieldByFieldRecursively(secondComment);
    }

    @Test
    void findByIdNotFoundTest() {
        when(commentDao.findById(any())).thenReturn(null);

        Comment result = commentService.findById(-1L);

        assertThat(result).isNull();
    }

    @Test
    void findCommentsWithRideTest() {
        when(commentDao.getCommentsWithRideId(ride1.getId())).thenReturn(new ArrayList<>(ride1.getComments()));
        when(commentDao.getCommentsWithRideId(ride2.getId())).thenReturn(new ArrayList<>(ride2.getComments()));

        List<Comment> ride1Comments = commentService.findAllWithRideId(ride1.getId());
        assertThat(ride1Comments).containsExactlyInAnyOrder(sampleComment);

        List<Comment> ride2Comments = commentService.findAllWithRideId(ride2.getId());
        assertThat(ride2Comments).containsExactlyInAnyOrder(secondComment);
    }

    @Test
    void findCommentsWithRideMultipleCommentsTest() {
        secondComment.setRide(ride1);
        ride1.addComment(secondComment);
        when(commentDao.getCommentsWithRideId(ride1.getId())).thenReturn(new ArrayList<>(ride1.getComments()));

        List<Comment> ride1Comments = commentService.findAllWithRideId(ride1.getId());
        assertThat(ride1Comments).containsExactlyInAnyOrder(sampleComment, secondComment);
    }

    @Test
    void findCommentsWithRideNoCommentsTest() {
        when(commentDao.getCommentsWithRideId(ride1.getId())).thenReturn(new ArrayList<>());

        List<Comment> result = commentService.findAllWithRideId(ride1.getId());

        assertThat(result).isEmpty();
    }

    @Test
    void findCommentsWithRideNullRideTest() {
        when(commentDao.getCommentsWithRideId(null)).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> commentService.findAllWithRideId(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findCommentsWithAuthorTest() {
        when(commentDao.getCommentsWithUserId(adam.getId())).thenReturn(new ArrayList<>(adam.getUserComments()));
        when(commentDao.getCommentsWithUserId(maros.getId())).thenReturn(new ArrayList<>(maros.getUserComments()));

        List<Comment> adamComments = commentService.findAllWithAuthorId(adam.getId());
        assertThat(adamComments).containsExactlyInAnyOrder(sampleComment);

        List<Comment> marosComments = commentService.findAllWithAuthorId(maros.getId());
        assertThat(marosComments).containsExactlyInAnyOrder(secondComment);
    }

    @Test
    void findCommentsWithAuthorMultipleCommentsTest() {
        secondComment.setAuthor(adam);
        adam.addComment(secondComment);
        when(commentDao.getCommentsWithUserId(adam.getId())).thenReturn(new ArrayList<>(adam.getUserComments()));

        List<Comment> adamComments = commentService.findAllWithAuthorId(adam.getId());
        assertThat(adamComments).containsExactlyInAnyOrder(sampleComment, secondComment);
    }

    @Test
    void findCommentsWithAuthorNoCommentsTest() {
        when(commentDao.getCommentsWithUserId(adam.getId())).thenReturn(new ArrayList<>());

        List<Comment> adamComments = commentService.findAllWithAuthorId(adam.getId());

        assertThat(adamComments).isEmpty();
    }

    @Test
    void findCommentsWithAuthorNullAuthorTest() {
        when(commentDao.getCommentsWithUserId(null)).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> commentService.findAllWithAuthorId(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deleteCommentTest() {
        doNothing().when(commentDao).delete(sampleComment);

        commentService.deleteComment(sampleComment);

        verify(commentDao).delete(sampleComment);
    }

    @Test
    void deleteNullCommentTest() {
        doThrow(new IllegalArgumentException()).when(commentDao).delete(null);

        assertThatThrownBy(() -> commentService.deleteComment(null)).isInstanceOf(IllegalArgumentException.class);
    }
}
