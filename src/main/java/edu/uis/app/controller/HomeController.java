package edu.uis.app.controller;

import edu.uis.app.service.AnalyticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Pablo Delgado
 */
@Controller
@RequestMapping("/")
public class HomeController {
    
    private AnalyticService analyticService;

    @Autowired
    public void setAnalyticService(AnalyticService service) {
        analyticService = service;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("studentAlumniCount", analyticService.studentAlumniCount());
        model.addAttribute("graduatedAlumniCount", analyticService.graduatedAlumniCount());
        model.addAttribute("employerCount", analyticService.employerCount());
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
