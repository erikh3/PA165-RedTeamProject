package cz.fi.muni.pa165.rest.teamred;

import cz.fi.muni.pa165.teamred.dto.PlaceDTO;
import cz.fi.muni.pa165.teamred.facade.PlaceFacade;
import cz.fi.muni.pa165.teamred.rest.RootWebContext;
import cz.fi.muni.pa165.teamred.rest.controllers.PlacesController;
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

@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class PlacesControllerTest extends AbstractTestNGSpringContextTests {
    @Mock
    private PlaceFacade placeFacade;

    @Inject
    @InjectMocks
    private PlacesController placesController;

    private MockMvc mockMvc;

    @BeforeClass
    void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(placesController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    void getAllPlaces() throws Exception {
        doReturn(Collections.unmodifiableList(this.createPlaces())).when(placeFacade).getAllPlaces();

        mockMvc.perform(get("/places"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==4)].name").value("Brno"))
                .andExpect(jsonPath("$.[?(@.id==48)].name").value("Praha"));
    }

    @Test
    void getPlace() throws Exception {
        List<PlaceDTO> places = this.createPlaces();
        doReturn(places.get(0)).when(placeFacade).getPlaceWithId(places.get(0).getId());
        doReturn(places.get(1)).when(placeFacade).getPlaceWithId(places.get(1).getId());

        mockMvc.perform(get("/places/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.name").value("Brno"));

        mockMvc.perform(get("/places/48"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("48"))
                .andExpect(jsonPath("$.name").value("Praha"));
    }

    @Test
    void deleteUserTest() throws Exception {
        doNothing().when(placeFacade).deletePlace(anyLong());

        mockMvc.perform(delete("/places/1"))
                .andExpect(status().isOk());

        verify(placeFacade).deletePlace(1L);
    }

    @Test
    void createUser() throws Exception {
        PlaceDTO ostrava = new PlaceDTO();
        ostrava.setId(15L);
        ostrava.setName("Ostrava");

        doReturn(ostrava.getId()).when(placeFacade).createPlace(any());
        doReturn(ostrava).when(placeFacade).getPlaceWithId(ostrava.getId());

        mockMvc.perform(post("/places/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{" +
                                "\"name\": \"Ostrava\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(15L))
                .andExpect(jsonPath("name").value("Ostrava"));
    }

    private List<PlaceDTO> createPlaces() {
        PlaceDTO brno = new PlaceDTO();
        brno.setId(4L);
        brno.setName("Brno");

        PlaceDTO praha = new PlaceDTO();
        praha.setId(48L);
        praha.setName("Praha");

        return Arrays.asList(brno, praha);
    }
}