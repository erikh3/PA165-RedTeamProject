package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.facade.PlaceFacade;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jcibik on 12/8/17.
 */
@Controller
@RequestMapping("/place")
public class PlaceController {
    @Autowired
    private PlaceFacade placeFacade;

    @RequestMapping
    public String doWelcomePlacePage(Model model, HttpServletRequest request, HttpServletResponse response){
        return "place";
    }

}
