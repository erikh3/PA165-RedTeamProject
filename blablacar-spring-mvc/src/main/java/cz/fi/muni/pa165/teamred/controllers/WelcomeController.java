package cz.fi.muni.pa165.teamred.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import cz.fi.muni.pa165.teamred.config.UserSession;
import cz.fi.muni.pa165.teamred.dto.UserCreateDTO;
import cz.fi.muni.pa165.teamred.dto.UserDTO;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by jcibik on 12/4/17.
 */
@Controller
@RequestMapping(value = "/")
public class WelcomeController {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private UserSession session;

    private static final JacksonFactory jacksonFactory = new JacksonFactory();
    private static final HttpTransport transport = new NetHttpTransport();
    private static final String clientId = "332736943859-mrr2173fc1kseq1l2i4h0na68mnpmbp3.apps.googleusercontent.com";

    //matches all url "/*"
    @RequestMapping
    public String doWelcome(Model model){
        //Return to view
        return "welcome";
    }

    @RequestMapping(value = "/tokensignin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String doTokenSignIn(@RequestParam Map<String,String> paramMap, ModelMap modelMap){

        if(paramMap == null && paramMap.get("idtoken") == null) {
            throw new IllegalArgumentException("Token not provided");
        }

        String token = paramMap.get("idtoken").toString();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        //TODO: Handle Exceptions!
        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(token);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String id = session.getUserId();

        if (idToken == null) {
            System.out.println("Invalid ID token.");
            return "welcome"; //TODO: Display error message
        }
        GoogleIdToken.Payload payload = idToken.getPayload();

        //Get login ID = Google User identifier
        String googleId = payload.getSubject();
        UserDTO foundUser = null;
        try{
            foundUser = userFacade.findUserByLoginId(googleId);
        }catch (NullPointerException e) { //TODO: Other exceptions handling
            //this means probably that user is null; have not found out yet the cause
        }

        //Create new user in our app
        if(foundUser == null) {
            try {
                String email = payload.getEmail();
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                UserCreateDTO newUser = new UserCreateDTO();
                newUser.setName(givenName);
                newUser.setSurname(familyName);
                newUser.setNickname(email);
                newUser.setLoginId(googleId);

                userFacade.createUser(newUser);

            } catch (Exception ex) {
                return "welcome"; //TODO: Display error message
            }
        }

        session.setUserId(foundUser.getId().toString());

        //TODO: Redirect after POST
        //Return to view
        return "welcome";
    }

}