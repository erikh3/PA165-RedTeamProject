package cz.fi.muni.pa165.teamred.rest.controllers;

import cz.fi.muni.pa165.teamred.dto.UserCreateDTO;
import cz.fi.muni.pa165.teamred.rest.ApiUris;
import cz.fi.muni.pa165.teamred.rest.exceptions.ResourceAlreadyExistException;
import cz.fi.muni.pa165.teamred.rest.exceptions.ResourceCannotBeDeletedException;
import cz.fi.muni.pa165.teamred.rest.exceptions.ResourceNotFoundException;

import java.util.Collection;

import javax.inject.Inject;

import cz.fi.muni.pa165.teamred.dto.UserDTO;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Users
 *
 * @author Erik Horváth
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UsersController {

    private final static Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Inject
    private UserFacade userFacade;

    /**
     * Returns all users
     * @return list of UserDTOs
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<UserDTO> getUsers() {
        logger.debug("rest getUsers()");

        return userFacade.getAllUsers();
    }

    /**
     *
     * Returns user according to id
     *
     * @param id user identifier
     * @return UserDTO
     * @throws ResourceNotFoundException when user with id was not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO getUser(@PathVariable Long id) throws ResourceNotFoundException {
        logger.debug("rest getUser({})", id);

        UserDTO userDTO = userFacade.findUserById(id);
        if (userDTO == null){
            throw new ResourceNotFoundException();
        }
        return userDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteUser(@PathVariable Long id) throws ResourceNotFoundException, ResourceCannotBeDeletedException {
        logger.debug("rest deleteUser({})", id);

        try {
            userFacade.removeUser(id);
        } catch (JpaSystemException e) {
            throw new ResourceCannotBeDeletedException();
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO createUser(@RequestBody UserCreateDTO userCreateDTO) throws ResourceAlreadyExistException {
        logger.debug("rest createUser()");

        try {
            Long id = userFacade.createUser(userCreateDTO);
            return userFacade.findUserById(id);
        } catch (Exception e) {
            throw new ResourceAlreadyExistException();
        }
    }
}
