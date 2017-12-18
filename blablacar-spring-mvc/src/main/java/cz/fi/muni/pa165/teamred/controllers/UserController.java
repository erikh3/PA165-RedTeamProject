package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.config.UserSession;
import cz.fi.muni.pa165.teamred.dto.CommentDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.facade.CommentFacade;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @RequestMapping("")

    public String doWelcomeUserPage(Model model, HttpServletRequest request, HttpServletResponse response){
        if (userSession.getUserId() == null){
            return "redirect:/";
        }
        addModelComments(model);
        return "users/user";
    }

    private void addModelComments(Model model){
        List<CommentDTO> comments = commentFacade.getCommentsWithAuthor(Long.valueOf(userSession.getUserId()));
        model.addAttribute("lastcomments",comments);
    }

    @RequestMapping("/user-details")
    public String userDetails(Model model, HttpServletRequest request, HttpServletResponse response){
        model.addAttribute("user",userFacade.findUserById(Long.valueOf(userSession.getUserId())));
        return "users/user-details";
    }

    @ModelAttribute(name = "userSession")
    public UserSession addUserSession(){
        return userSession;
    }


}