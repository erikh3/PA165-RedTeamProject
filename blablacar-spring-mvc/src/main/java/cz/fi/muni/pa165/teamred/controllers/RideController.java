package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.config.UserSession;
import cz.fi.muni.pa165.teamred.dto.*;
import cz.fi.muni.pa165.teamred.facade.PlaceFacade;
import cz.fi.muni.pa165.teamred.facade.RideFacade;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import cz.fi.muni.pa165.teamred.models.PlaceForm;
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

    final static Logger log = LoggerFactory.getLogger(RideController.class);

    @Autowired
    private RideFacade rideFacade;
    @Autowired
    private PlaceFacade placeFacade;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private UserSession userSession;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRide(@Valid @ModelAttribute("rideCreateDTO") RideCreateDTO ride,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        log.debug("create(ride={})", ride);

        if (ride.getDestinationPlaceId().equals(ride.getSourcePlaceId())){
            model.addAttribute("alert_danger", "Destination place and source place are the same.");
            return "rides/new";
        }
        if (!isValidBinding(result, model)) {
            return "rides/new";
        }

        //create
        Long id = rideFacade.createRide(ride);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Ride was created");
        //redirect to ride with this comment
        return "redirect:/ride/list-driver";
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
    public String createNewRide(){
        return "rides/new";
    }

    @ModelAttribute
    public void addRideForm(ModelMap model) {
        RideCreateDTO newRide = new RideCreateDTO();
        List<PlaceDTO> places = new ArrayList<>(placeFacade.getAllPlaces());
        newRide.setDriverId(userSession.getUserId());
        model.addAttribute("rideCreateDTO", newRide);
        model.addAttribute("places" , places);
    }

    @RequestMapping(value = "/showRide/{rideId}", method = RequestMethod.GET)
    public String showRide(@PathVariable Long rideId, Model model) {
        RideDTO rideDTO = rideFacade.getRideWithId(rideId);
        model.addAttribute("rideDTO", rideDTO);
        return "rides/ride";
    }

    @RequestMapping(value = "/showRide", method = RequestMethod.GET)
    public String showRide2(@RequestParam(name = "rideId") Long rideId, Model model) {
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

        rideFacade.changePrice(ride.getId(), ride.getSeatPrice());
        rideFacade.editAvailableSeats(ride.getId(), ride.getAvailableSeats());
        rideFacade.editDeparture(ride.getId(), ride.getDeparture());

        redirectAttributes.addFlashAttribute("alert_success", "Ride " + userSession.getUserId() + " was updated");

        return "redirect:/ride/showRide/" + ride.getId();
    }

    @RequestMapping(value = "/addPassenger")
    public String addPassengerToRide(@RequestParam(name = "rideId") Long rideId,
                                     Model model,
                                     RedirectAttributes redirectAttributes,
                                     HttpServletResponse response,
                                     HttpServletRequest request) {

        AddPassengerDTO passengerDTO = new AddPassengerDTO();
        passengerDTO.setPassengerId(userSession.getUserId());
        passengerDTO.setRideId(rideId);
        rideFacade.addPassenger(passengerDTO);

        model.addAttribute("rides", rideFacade.getAllRides());

        redirectAttributes.addFlashAttribute("alert_success", "Joined ride " + rideId);
        return "redirect:/ride/list";
    }

    @RequestMapping(value = "/removePassenger")
    public String removePassengerToRide(@RequestParam(name = "rideId") Long rideId,
                                        Model model,
                                        RedirectAttributes redirectAttributes,
                                        HttpServletResponse response,
                                        HttpServletRequest request) {

        RemovePassengerDTO removePassengerDTO = new RemovePassengerDTO();

        removePassengerDTO.setPassengerId(Long.valueOf(userSession.getUserId()));
        removePassengerDTO.setRideId(rideId);
        rideFacade.removePassenger(removePassengerDTO);

        redirectAttributes.addFlashAttribute("alert_success", "Removed from ride " + rideId);
        return "redirect:/ride/list";
    }

    @RequestMapping(value = "/delete")
    public String deleteRide(@RequestParam(value = "rideId", required = true) Long rideId, Model model,
                             RedirectAttributes redirectAttributes, HttpServletRequest request,
                             HttpServletResponse response) {

        rideFacade.deleteRide(rideId);

        redirectAttributes.addFlashAttribute("alert_success", "Ride was deleted");
        return "redirect:/ride/list-driver";
    }

    @RequestMapping(value = "/list-pass", method = RequestMethod.GET)
    public String listAllUserRidesAsPassenger(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<RideDTO> rides = new ArrayList<>(userFacade.getUserRidesAsPassenger(Long.valueOf(userSession.getUserId())));
        model.addAttribute("rides", rides);
        return "rides/list-pass";
    }

    @RequestMapping(value = "/list-driver", method = RequestMethod.GET)
    public String listAllUserRidesAsDriver(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<RideDTO> rides = new ArrayList<>(userFacade.getUserRidesAsDriver(Long.valueOf(userSession.getUserId())));
        model.addAttribute("rides", rides);
        return "rides/list-driver";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listAllRides(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<RideDTO> rides = rideFacade.getAllRides();
        model.addAttribute("rides", rides);
        return "rides/all";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String listSearchResult(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("placeForm", new PlaceForm());
        model.addAttribute("places", placeFacade.getAllPlaces());
        return "rides/search"; //create new view with search on top
    }

    @RequestMapping("")
    public String redirectTo404Page(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "error404";
    }

//    @RequestMapping(value = "/find", method = RequestMethod.POST)
//    public String findRides(@Valid @ModelAttribute("placeForm") PlaceForm placeForm,
//                                   Model model,
//                                   BindingResult result,
//                                   RedirectAttributes redirectAttributest) {
//        model.addAttribute("rides", placeFacade.getRidesWithOriginatingAndDestinationPlace(Long.valueOf(placeForm.getFrom()),Long.valueOf(placeForm.getTo())));
//        return "rides/search";
//    }
    @RequestMapping(value = "/connection", method = RequestMethod.GET)
    public String listSearchResult(@RequestParam(required=true) String placeFrom,
                                   @RequestParam(required=true) String placeTo,
                                   Model model) {

        model.addAttribute("rides",placeFacade.getRidesWithOriginatingAndDestinationPlaceByName(placeFrom,placeTo));
        return "rides/all"; //create new view with search on top
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String findRides(@Valid @ModelAttribute("placeForm") PlaceForm placeForm,
                            BindingResult result,
                            RedirectAttributes redirectAttributest) {

        return "redirect:/ride/connection?placeFrom=" + placeForm.getFrom() + "&placeTo=" + placeForm.getTo();
    }


    @ModelAttribute(name = "userSession")
    public UserSession addUserSession(){
        return userSession;
    }
}