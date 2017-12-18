package cz.fi.muni.pa165.rest.teamred;

import cz.fi.muni.pa165.teamred.dto.CommentDTO;
import cz.fi.muni.pa165.teamred.facade.CommentFacade;
import cz.fi.muni.pa165.teamred.rest.RootWebContext;
import cz.fi.muni.pa165.teamred.rest.controllers.CommentsController;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Erik Horváth
 */
@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class CommentsControllerTest extends AbstractTestNGSpringContextTests {
    @Mock
    private CommentFacade commentFacade;

    @Inject
    @InjectMocks
    private CommentsController commentsController;

    private MockMvc mockMvc;

    @BeforeClass
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(commentsController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    void getAllComments() throws Exception {
        doReturn(Collections.unmodifiableList(this.createComments())).when(commentFacade).getAllComments();

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].text").value("positive comment"))
                .andExpect(jsonPath("$.[?(@.id==2)].text").value("negative comment"));
    }

    @Test
    void getCommentsWithAuthor() throws Exception {
        doReturn(Collections.unmodifiableList(this.createComments())).when(commentFacade).getCommentsWithAuthor(4L);

        mockMvc.perform(get("/comments/by-author/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].text").value("positive comment"))
                .andExpect(jsonPath("$.[?(@.id==2)].text").value("negative comment"));
    }

    @Test
    void getCommentsWithRide() throws Exception {
        doReturn(Collections.unmodifiableList(this.createComments())).when(commentFacade).getCommentsWithRide(40L);

        mockMvc.perform(get("/comments/by-ride/40"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].text").value("positive comment"))
                .andExpect(jsonPath("$.[?(@.id==2)].text").value("negative comment"));
    }

    @Test
    void getComment() throws Exception {
        List<CommentDTO> comments = this.createComments();
        doReturn(comments.get(0)).when(commentFacade).getCommentWithId(comments.get(0).getId());
        doReturn(comments.get(1)).when(commentFacade).getCommentWithId(comments.get(1).getId());

        mockMvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.text").value("positive comment"));

        mockMvc.perform(get("/comments/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.text").value("negative comment"));
    }

    @Test
    void deleteComment() throws Exception {
        doNothing().when(commentFacade).deleteComment(anyLong());

        mockMvc.perform(delete("/comments/1"))
                .andExpect(status().isOk());

        verify(commentFacade).deleteComment(1L);
    }

    @Test
    void createComment() throws Exception {
        CommentDTO comment = new CommentDTO();
        comment.setId(7L);
        comment.setText("Hey");
        comment.setAuthorId(4L);
        comment.setRideId(8L);

        doReturn(comment.getId()).when(commentFacade).createComment(any());
        doReturn(comment).when(commentFacade).getCommentWithId(comment.getId());

        mockMvc.perform(post("/comments/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{" +
                                "\"text\": \"Hey\"," +
                                "\"rideId\": \"8\"," +
                                "\"authorId\": \"4\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(7L))
                .andExpect(jsonPath("text").value("Hey"))
                .andExpect(jsonPath("authorId").value(4L))
                .andExpect(jsonPath("rideId").value(8L));
    }

    @Test
    void changeCommentText() throws Exception {
        CommentDTO comment = new CommentDTO();
        comment.setId(4L);
        comment.setText("Changed");
        doNothing().when(commentFacade).changeText(anyLong(), anyString());
        doReturn(comment).when(commentFacade).getCommentWithId(comment.getId());

        mockMvc.perform(post("/comments/change-text")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{" +
                                "\"commentId\": 4," +
                                "\"text\": \"Changed\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(4L))
                .andExpect(jsonPath("text").value("Changed"));
    }

    private List<CommentDTO> createComments() {
        CommentDTO positive = new CommentDTO();
        positive.setId(1L);
        positive.setText("positive comment");

        CommentDTO negative = new CommentDTO();
        negative.setId(2L);
        negative.setText("negative comment");

        return Arrays.asList(positive, negative);
    }
}
