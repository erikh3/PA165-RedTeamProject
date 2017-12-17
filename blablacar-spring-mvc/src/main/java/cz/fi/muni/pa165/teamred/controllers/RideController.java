package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.dto.PlaceDTO;
import cz.fi.muni.pa165.teamred.dto.RideCreateDTO;
import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.facade.PlaceFacade;
import cz.fi.muni.pa165.teamred.facade.RideFacade;
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
 * Created by jcibik on 12/6/17.
 */
@Controller
@RequestMapping("/ride")
public class RideController {

    @Autowired
    private RideFacade rideFacade;


    @Autowired
    private PlaceFacade placeFacade;

    final static Logger log = LoggerFactory.getLogger(CommentController.class);

    //change this to user session bean
    private Long id = 1L;

    //This is just an example you can work with models or models map
    //as for variables from jsp you can parse them from model or a url path it is up to you,
    //again this is just and example of allowed urls to work with


    //only for post method allowed creating a new comment
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRide(@Valid @ModelAttribute("rideCreateDTO") RideCreateDTO ride,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        log.debug("create(ride={})", ride);

//        if (!isValidBinding(result, model)) {
//            model.addAttribute("rideCreateDTO", ride);
//            String referer = request.getHeader("Referer");
//            return "redirect:" + referer;
//        }

        //create
        Long id = rideFacade.createRide(ride);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Ride " + id + " was created");
        //redirect to ride with this comment
        return "redirect:showRide/" + id;
    }

    private boolean isValidBinding(BindingResult result, Model model) {
        if (result.hasErrors()) {
            for (ObjectError ge : result.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : result.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return false;
        }
        return true;
    }


    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String addRideForm(ModelMap model) {
        RideCreateDTO newRide = new RideCreateDTO();
        List<PlaceDTO> places = new ArrayList<>(placeFacade.getAllPlaces());
        //TODO change this to user session bean
        newRide.setDriverId(this.id);
        model.addAttribute("rideCreateDTO", newRide);
        model.addAttribute("places" , places);
        //redirect to comment create form
        return "rides/new";
    }

    @RequestMapping(value = "/showRide/{rideId}", method = RequestMethod.GET)
    public String showRide(@PathVariable Long rideId, Model model) {
        RideDTO rideDTO = rideFacade.getRideWithId(rideId);
        model.addAttribute("rideDTO", rideDTO);
        return "rides/ride";
    }

    @RequestMapping(value = "/updateRide", method = RequestMethod.POST)
    public String showRide(@Valid @ModelAttribute("rideDTO") RideDTO ride,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes,
                           HttpServletResponse response,
                           HttpServletRequest request) {

        log.debug("update(ride={})", ride);

        if ( isValidBinding(result, model)) {
            model.addAttribute("rideCreateDTO", ride);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }

        //create
        rideFacade.changePrice(ride.getId(), ride.getSeatPrice());
        rideFacade.editAvailableSeats(ride.getId(), ride.getAvailableSeats());
        rideFacade.editDeparture(ride.getId(), ride.getDeparture());
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Comment " + id + " was created");
        //redirect to ride with this comment
        return "redirect:ride/showRide/" + id;
    }

    @RequestMapping(value = "/addPassenger")
    public String addPassengerToRide() {
        //TODO
        return "";
    }



    @RequestMapping(value = "/removePassenger")
    public String removePassengerToRide() {
        //TODO
        return "";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteRide(@RequestParam(value = "id", required = true) Long rideId, Model model, HttpServletRequest request, HttpServletResponse response) {

        //TODO
        //Delete ride with received id
        //check it first


        // redirect to caller page
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    //Only for a get method allowed to list all rides
    @RequestMapping(value = "/list-pass", method = RequestMethod.GET)
    public @ResponseBody
    List<RideDTO> listAllUserRidesAsPassenger(Model model, HttpServletRequest request, HttpServletResponse response) {
        //TODO
        //list all rides from user with id retrieved from user session bean
        //return the list
        return new ArrayList<>();
    }

    //Only for a get method allowed to list all rides
    @RequestMapping(value = "/list-driver", method = RequestMethod.GET)
    public @ResponseBody
    List<RideDTO> listAllUserRidesAsDriver(Model model, HttpServletRequest request, HttpServletResponse response) {
        //TODO
        //return the list
        return new ArrayList<>();
    }

    //Only for a get method allowed to list all rides
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listAllRides(Model model, HttpServletRequest request, HttpServletResponse response) {
        //return the list
        List<RideDTO> rides = rideFacade.getAllRides();
        model.addAttribute("rides", rides);
        return "rides/all";
    }

    @RequestMapping("")
    public String redirectTo404Page(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "error404";
    }
}
