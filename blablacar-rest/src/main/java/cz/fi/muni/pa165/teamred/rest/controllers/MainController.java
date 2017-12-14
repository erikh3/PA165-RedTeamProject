package cz.fi.muni.pa165.teamred.rest.controllers;

import cz.fi.muni.pa165.teamred.rest.ApiUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {
    final static Logger logger = LoggerFactory.getLogger(MainController.class);

    /**
     * @return resources uris
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {
        logger.debug("getResources()");

        Map<String,String> resourcesMap = new HashMap<>();
        
        resourcesMap.put("comments_uri", ApiUris.ROOT_URI_COMMENTS);
        resourcesMap.put("places_uri", ApiUris.ROOT_URI_PLACES);
        resourcesMap.put("users_uri", ApiUris.ROOT_URI_USERS);
        resourcesMap.put("rides_uri", ApiUris.ROOT_URI_RIDES);

        return Collections.unmodifiableMap(resourcesMap);
    }
}
