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
    public String home(Model model) {    
        model.addAttribute("welcomeMessage", "Welcome to UIS Alumni Tracker");
        return "home";
    }
    
    
    /**
     * Used for developing and testing layouts, it should be deleted when no
     * longer useful.
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = "layout", method = RequestMethod.GET)
    public String layout(Model model) {
        return "main";
    }
    
}
