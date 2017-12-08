package cz.fi.muni.pa165.teamred.service.implementations;

import cz.fi.muni.pa165.teamred.service.WelcomeService;
import org.springframework.stereotype.Service;

/**
 * Created by jcibik on 12/4/17.
 */
@Service
public class WelcomeServiceImpl implements WelcomeService {

    public String getWelcomeMessage(String name){
        String message = "Welcome to the PA165 BlaBlaCarProject, " + name;
        return message;
    }

}
