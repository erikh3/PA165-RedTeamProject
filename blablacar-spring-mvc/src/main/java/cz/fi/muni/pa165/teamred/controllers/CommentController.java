package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.config.UserSession;
import cz.fi.muni.pa165.teamred.dto.CommentAuthorDTO;
import cz.fi.muni.pa165.teamred.dto.CommentCreateDTO;
import cz.fi.muni.pa165.teamred.dto.CommentDTO;
import cz.fi.muni.pa165.teamred.facade.CommentFacade;
import cz.fi.muni.pa165.teamred.facade.RideFacade;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private RideFacade rideFacade;

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
            return "comments/new";
        }
        //create
        Long id = commentFacade.createComment(comment);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Ride was commented");
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

    @RequestMapping(value = "/delete")
    public String deleteComment(@RequestParam(name = "commentId") Long commentId,
                                Model model,
                                HttpServletRequest request,
                                HttpServletResponse response){

        commentFacade.deleteComment(commentId);

        // redirect to caller page
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    //Only for a get method allowed to list all comments
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String listComments(Model model){
        List<CommentDTO> comments = commentFacade.getAllComments();

        ArrayList<CommentAuthorDTO> displayComments = new ArrayList<>();
        for (CommentDTO comment:comments) {

            CommentAuthorDTO newComment = new CommentAuthorDTO(comment);
            newComment.setAuthor(userFacade.findUserById(comment.getAuthorId()));

            displayComments.add(newComment);
        }

        model.addAttribute("comments", displayComments);
        return "comments/all-manage";
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
