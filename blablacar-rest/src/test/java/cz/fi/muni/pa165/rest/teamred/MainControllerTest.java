package cz.fi.muni.pa165.rest.teamred;

import cz.fi.muni.pa165.teamred.rest.RootWebContext;
import cz.fi.muni.pa165.teamred.rest.controllers.MainController;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Erik Horváth
 */
@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class MainControllerTest extends AbstractTestNGSpringContextTests {
    private final MockMvc mockMvc = standaloneSetup(new MainController()).build();

    @Test
    void mainControllerTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("rides_uri").value("/rides"))
                .andExpect(jsonPath("users_uri").value("/users"))
                .andExpect(jsonPath("comments_uri").value("/comments"))
                .andExpect(jsonPath("places_uri").value("/places"));
    }
}
