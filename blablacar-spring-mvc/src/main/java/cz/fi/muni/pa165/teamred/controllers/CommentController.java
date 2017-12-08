package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.facade.CommentFacade;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentFacade commentFacade;

    @RequestMapping
    public String doWelcomeUserPage(Model model, HttpServletRequest request, HttpServletResponse response){
        return "comment";
    }
}
