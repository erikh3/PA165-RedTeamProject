package cz.fi.muni.pa165.rest.teamred;

import cz.fi.muni.pa165.teamred.dto.UserDTO;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import cz.fi.muni.pa165.teamred.rest.RootWebContext;
import cz.fi.muni.pa165.teamred.rest.controllers.UsersController;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Jozef Cibík on 17.12.2017.
 */

@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class UsersControllerTest extends AbstractTestNGSpringContextTests {
    @Mock
    private UserFacade userFacade;

    @Inject
    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;

    @BeforeClass
    void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(usersController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    void getAllUsers() throws Exception {
        doReturn(Collections.unmodifiableList(this.createUsers())).when(userFacade).getAllUsers();

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("John"))
                .andExpect(jsonPath("$.[?(@.id==1)].surname").value("Smith"))
                .andExpect(jsonPath("$.[?(@.id==1)].nickname").value("ONEANDONLY"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Clara"))
                .andExpect(jsonPath("$.[?(@.id==2)].surname").value("Williams"))
                .andExpect(jsonPath("$.[?(@.id==2)].nickname").value("sweet clara"));
    }

    @Test
    void getUser() throws Exception {
        List<UserDTO> users = this.createUsers();
        doReturn(users.get(0)).when(userFacade).findUserById(users.get(0).getId());
        doReturn(users.get(1)).when(userFacade).findUserById(users.get(1).getId());

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.surname").value("Smith"));

        mockMvc.perform(get("/users/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.surname").value("Williams"));
    }

    @Test
    void deleteUserTest() throws Exception {
        doNothing().when(userFacade).removeUser(anyLong());

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        verify(userFacade).removeUser(1L);
    }

    @Test
    void createUser() throws Exception {
        UserDTO john = new UserDTO();
        john.setId(15L);
        john.setNickname("Johny21");
        john.setName("John");
        john.setSurname("Cowcall");

        doReturn(john.getId()).when(userFacade).createUser(any());
        doReturn(john).when(userFacade).findUserById(john.getId());

        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{" +
                        "\"name\": \"John\"," +
                        "\"surname\": \"Cowcall\"," +
                        "\"nickname\": \"Johny21\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(15L))
                .andExpect(jsonPath("name").value("John"))
                .andExpect(jsonPath("surname").value("Cowcall"))
                .andExpect(jsonPath("nickname").value("Johny21"));
    }

    private List<UserDTO> createUsers() {
        UserDTO john = new UserDTO();
        john.setId(1L);
        john.setName("John");
        john.setSurname("Smith");
        john.setNickname("ONEANDONLY");

        UserDTO clara = new UserDTO();
        clara.setId(2L);
        clara.setName("Clara");
        clara.setSurname("Williams");
        clara.setNickname("sweet clara");

        return Arrays.asList(john, clara);
    }
}