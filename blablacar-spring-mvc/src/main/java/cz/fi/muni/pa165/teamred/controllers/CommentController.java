package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.config.UserSession;
import cz.fi.muni.pa165.teamred.dto.CommentCreateDTO;
import cz.fi.muni.pa165.teamred.dto.CommentDTO;
import cz.fi.muni.pa165.teamred.facade.CommentFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jcibik on 12/8/17.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {


    final static Logger log = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private UserSession userSession;

    @Autowired
    private CommentFacade commentFacade;

    //only for post method allowed creating a new comment
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewComment(@Valid @ModelAttribute("commentCreateDTO") CommentCreateDTO comment,
                                       BindingResult result,
                                       Model model,
                                   RedirectAttributes redirectAttributes,
                                       HttpServletRequest request,
                                       HttpServletResponse response){

        log.debug("create(formBean={})", comment);

        if (result.hasErrors()) {
            for (ObjectError ge : result.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : result.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            model.addAttribute("commentCreateDTO", comment);
            //TODO
            return "redirect:/comment/new?rideId=" + comment.getRideId();
        }
        //create
        Long id = commentFacade.createComment(comment);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Comment " + id + " was created");
        //redirect to ride with this comment
        return "redirect:../ride/showRide/" + comment.getRideId();
    }


    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String addCommentForm(@RequestParam(value = "rideId", required = true) Long rideId, ModelMap model){
        CommentCreateDTO newComment = new CommentCreateDTO();
        newComment.setRideId(rideId);
        newComment.setAuthorId(Long.valueOf(userSession.getUserId()));
        model.addAttribute("commentCreateDTO", newComment);
        //redirect to comment create form
        return "comments/new";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteComment(@RequestParam(value = "id", required = true) Long id, Model model, HttpServletRequest request, HttpServletResponse response){

        //TODO
        //Delete comment with received id
        //check it first


        // redirect to caller page
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    //Only for a get method allowed to list all comments
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody List<CommentDTO> listAllUserComments(Model model, HttpServletRequest request, HttpServletResponse response){
        //TODO
        //list all comments from user with id retrieved from user session bean
        //return the list
        return new ArrayList<>();
    }

    @RequestMapping("")
    public String redirectTo404Page(Model model, HttpServletRequest request, HttpServletResponse response){
        return "error404";
    }

    @ModelAttribute(name = "userSession")
    public UserSession addUserSession(){
        return userSession;
    }
}
