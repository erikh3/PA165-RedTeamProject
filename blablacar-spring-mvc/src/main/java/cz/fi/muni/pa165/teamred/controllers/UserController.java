package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.dto.RideDTO;
import cz.fi.muni.pa165.teamred.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private Long actualUserId = 1L;

    @Autowired
    private UserFacade userFacade;

    @RequestMapping("")
    public String doWelcomeUserPage(Model model, HttpServletRequest request, HttpServletResponse response){
        if (actualUserId == null){
            return "redirect:/";
        }
        return "user";
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
}