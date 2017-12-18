package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.config.UserSession;
import cz.fi.muni.pa165.teamred.dto.CommentDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.facade.CommentFacade;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcibik on 12/8/17.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private CommentFacade commentFacade;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String doWelcomeUserPage(Model model, HttpServletRequest request, HttpServletResponse response){
        if (userSession.getUser() == null){
            return "redirect:/";
        }

        addModelComments(model);
        return "users/user";
    }

    private void addModelComments(Model model){
        model.addAttribute("comments", commentFacade.getCommentsWithAuthor(userSession.getUserId()));
    }

    @RequestMapping("/user-driver-rides")
    public String getUserRidesAsDriver(Model model, HttpServletRequest request, HttpServletResponse response){
        List<RideDTO> rides = new ArrayList<>(userFacade.getUserRidesAsDriver(1L));
        model.addAttribute("rides", rides);
        return "user/listUserDriverRides";
    }


    @RequestMapping("/user-passenger-rides")
    public String getUserRidesAsPassenger(Model model, HttpServletRequest request, HttpServletResponse response){
        return "user/listUserPassengerRides";
    }

    @RequestMapping("/user-details")
    public String userDetails(Model model, HttpServletRequest request, HttpServletResponse response){
        model.addAttribute("user",userSession.getUser());
        return "users/user-details";
    }

    @ModelAttribute(name = "userSession")
    public UserSession addUserSession(){
        return userSession;
    }

    @ModelAttribute(name = "lastcomments")
    public List<CommentDTO> addComments(){
        return commentFacade.getCommentsWithAuthor(userSession.getUserId());
    }
}