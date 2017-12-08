package cz.fi.muni.pa165.teamred.controllers;

import cz.fi.muni.pa165.teamred.facade.RideFacade;
import cz.fi.muni.pa165.teamred.service.facade.RideFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jcibik on 12/6/17.
 */
@Controller
@RequestMapping("/ride")
public class RideController {

    @Autowired
    private RideFacade rideFacade;

    @RequestMapping
    public @ResponseBody Integer ride(){
        return rideFacade.getAllRides().size();
    }

}
