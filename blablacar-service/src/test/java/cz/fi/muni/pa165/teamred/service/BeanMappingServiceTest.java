package cz.fi.muni.pa165.teamred.service;

import cz.fi.muni.pa165.teamred.dto.PlaceDTO;
import cz.fi.muni.pa165.teamred.entity.Place;
import cz.fi.muni.pa165.teamred.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private BeanMappingService beanMappingService;

    private List<Place> placeList = new ArrayList<>();

    @BeforeMethod
    void before() {
        Place zlin = new Place("Zlín");
        placeList.add(zlin);

        Place brno = new Place("Brno");
        placeList.add(brno);
    }

    @Test
    void placeMapping() {
        List<PlaceDTO> resultList = beanMappingService.mapTo(placeList, PlaceDTO.class);

        assertThat(resultList.size())
                .isEqualTo(2);
    }
}
