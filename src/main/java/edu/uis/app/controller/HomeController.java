package edu.uis.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Pablo Delgado <pdelg2@uis.edu>
 */
@Controller
@RequestMapping("/")
public class HomeController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String homeAction(Model model) {    
        model.addAttribute("welcomeMessage", "Welcome to UIS Alumni Tracker");
        return "home";
    }
    
}
