package cz.fi.muni.pa165.teamred.rest.controllers;

import cz.fi.muni.pa165.teamred.dto.PlaceCreateDTO;
import cz.fi.muni.pa165.teamred.dto.PlaceDTO;
import cz.fi.muni.pa165.teamred.facade.PlaceFacade;
import cz.fi.muni.pa165.teamred.rest.ApiUris;
import cz.fi.muni.pa165.teamred.rest.exceptions.ResourceAlreadyExistException;
import cz.fi.muni.pa165.teamred.rest.exceptions.ResourceCannotBeDeletedException;
import cz.fi.muni.pa165.teamred.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;

@RestController
@RequestMapping(value = ApiUris.ROOT_URI_PLACES)
public class PlacesController {
    final static Logger logger = LoggerFactory.getLogger(CommentsController.class);

    @Inject
    private PlaceFacade placeFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<PlaceDTO> getPlaces() {
        logger.debug("rest getAllPlaces()");

        return placeFacade.getAllPlaces();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final PlaceDTO getPlace(@PathVariable Long id) throws ResourceNotFoundException {
        logger.debug("rest getPlace({})", id);

        PlaceDTO placeDTO = placeFacade.getPlaceWithId(id);

        if (placeDTO == null) {
            throw new ResourceNotFoundException();
        }

        return placeDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deletePlace(@PathVariable Long id) throws ResourceNotFoundException, ResourceCannotBeDeletedException {
        logger.debug("rest deletePlace({})", id);

        try {
            placeFacade.deletePlace(id);
        } catch (JpaSystemException e) {
            throw new ResourceCannotBeDeletedException();
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public final PlaceDTO createPlace(@RequestBody PlaceCreateDTO placeCreateDTO) throws ResourceAlreadyExistException {
        logger.debug("rest createPlace()");

        try {
            Long id = placeFacade.createPlace(placeCreateDTO);
            return placeFacade.getPlaceWithId(id);
        } catch (Exception e) {
            throw new ResourceAlreadyExistException();
        }
    }
}
