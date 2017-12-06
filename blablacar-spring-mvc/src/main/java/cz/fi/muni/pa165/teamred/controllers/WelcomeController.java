package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcibik on 12/4/17.
 */
@Controller
public class WelcomeController {

    @Autowired
    private WelcomeService welcomeService;

    @RequestMapping(value = "/")
    public String doWelcome(Model model){
        //Retrieve data
        String message = welcomeService.getWelcomeMessage("Visitor");

        //Add to model
        model.addAttribute("myWelcomeMessage", message);

        //Return to view
        return "welcome";
    }
}