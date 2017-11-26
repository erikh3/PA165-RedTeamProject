package cz.fi.muni.pa165.teamred.service.facade;

import cz.fi.muni.pa165.teamred.dto.CommentCreateDTO;
import cz.fi.muni.pa165.teamred.dto.CommentDTO;
import cz.fi.muni.pa165.teamred.entity.Comment;
import cz.fi.muni.pa165.teamred.entity.Ride;
import cz.fi.muni.pa165.teamred.entity.User;
import cz.fi.muni.pa165.teamred.service.*;
import cz.fi.muni.pa165.teamred.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Erik Horváth
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CommentFacadeTest {
    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private TimeService timeService;

    @Mock
    private CommentService commentService;

    @Mock
    private RideService rideService;

    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private CommentFacadeImpl commentFacadeImpl;

    private CommentCreateDTO commentCreateDTO;

    private Comment commentCreate;

    private Comment positiveComment;
    private CommentDTO positiveCommentDTO;

    @Mock
    private User mockedUser = new User();

    @Mock
    private Ride mockedRide = new Ride();

    @BeforeClass
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    void initDTO() {
        Calendar cal = Calendar.getInstance();
        cal.set(2010, Calendar.JULY, 25);
        when(timeService.getCurrentTime()).thenReturn(cal.getTime());

        commentCreateDTO = new CommentCreateDTO();
        commentCreateDTO.setAuthorId(5L);
        commentCreateDTO.setRideId(4L);
        commentCreateDTO.setText("sample comment text");

        commentCreate = new Comment();
        when(beanMappingService.mapTo(commentCreateDTO, Comment.class)).thenReturn(commentCreate);

        mockedUser.setId(4L);

        mockedRide.setId(8L);

        positiveComment = new Comment();
        positiveComment.setId(4L);
        positiveComment.setText("positive comment");
        when(commentService.findById(positiveComment.getId())).thenReturn(positiveComment);

        positiveCommentDTO = new CommentDTO();
        positiveCommentDTO.setId(positiveComment.getId());
        positiveCommentDTO.setText(positiveComment.getText());
        positiveCommentDTO.setCreated(positiveComment.getCreated());
        when(beanMappingService.mapTo(positiveComment, CommentDTO.class)).thenReturn(positiveCommentDTO);
    }

    @Test
    void createTest() {
        Comment createdComment = new Comment();
        createdComment.setId(42L);
        createdComment.setAuthor(mockedUser);
        createdComment.setRide(mockedRide);

        when(commentService.createComment(commentCreate)).thenReturn(createdComment);
        when(rideService.findById(any())).thenReturn(mockedRide);
        when(userService.findUserById(any())).thenReturn(mockedUser);

        Long id = commentFacadeImpl.createComment(commentCreateDTO);

        verify(commentService).createComment(commentCreate);

        verify(mockedUser).addComment(createdComment);
        verify(mockedRide).addComment(createdComment);

        assertThat(id).isEqualTo(createdComment.getId());
    }

    @Test
    void changeTextTest() {
        doNothing().when(commentService).updateComment(positiveComment);

        commentFacadeImpl.changeText(positiveComment.getId(), positiveComment.getText() + " appended");

        verify(commentService).updateComment(positiveComment);
    }

    @Test
    void deleteTest() {
        positiveComment.setAuthor(mockedUser);
        positiveComment.setRide(mockedRide);

        doNothing().when(commentService).deleteComment(positiveComment);

        commentFacadeImpl.deleteComment(positiveComment.getId());

        verify(commentService).deleteComment(positiveComment);

        verify(positiveComment.getAuthor()).removeComment(positiveComment);
        verify(positiveComment.getRide()).removeComment(positiveComment);
    }

    @Test
    void getCommentWithId() {
        CommentDTO commentDTO = commentFacadeImpl.getCommentWithId(positiveComment.getId());

        assertThat(commentDTO).isEqualToComparingFieldByFieldRecursively(positiveCommentDTO);
    }

    @Test
    void getAllComments() {
        List<Comment> commentList = new ArrayList<>();
        commentList.add(positiveComment);

        List<CommentDTO> commentDTOS = new ArrayList<>();
        commentDTOS.add(positiveCommentDTO);

        when(beanMappingService.mapTo(commentList, CommentDTO.class)).thenReturn(commentDTOS);
        when(commentService.findAll()).thenReturn(commentList);

        List<CommentDTO> commentDTOList = commentFacadeImpl.getAllComments();

        assertThat(commentDTOList).containsExactlyInAnyOrder(positiveCommentDTO);
    }

    @Test
    void getCommentsWithRideIdTest() {
        Ride ride = new Ride();
        ride.setId(7L);

        positiveComment.setRide(ride);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(positiveComment);

        List<CommentDTO> commentDTOS = new ArrayList<>();
        commentDTOS.add(positiveCommentDTO);

        when(beanMappingService.mapTo(commentList, CommentDTO.class)).thenReturn(commentDTOS);
        when(commentService.findAllWithRideId(ride.getId())).thenReturn(commentList);

        List<CommentDTO> commentDTOList = commentFacadeImpl.getCommentsWithRide(ride.getId());

        assertThat(commentDTOList).containsExactlyInAnyOrder(positiveCommentDTO);
    }

    @Test
    void getCommentsWithAuthorIdTest() {
        User user = new User();
        user.setId(9L);

        positiveComment.setAuthor(user);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(positiveComment);

        List<CommentDTO> commentDTOS = new ArrayList<>();
        commentDTOS.add(positiveCommentDTO);

        when(beanMappingService.mapTo(commentList, CommentDTO.class)).thenReturn(commentDTOS);
        when(commentService.findAllWithAuthorId(user.getId())).thenReturn(commentList);

        List<CommentDTO> commentDTOList = commentFacadeImpl.getCommentsWithAuthor(user.getId());

        assertThat(commentDTOList).containsExactlyInAnyOrder(positiveCommentDTO);
    }
}
