package edu.uis.app.controller;

import edu.uis.app.data.model.Employer;
import edu.uis.app.data.repository.EmployerRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Pablo Delgado
 */
@Controller
@RequestMapping("/webservice")
public class EmployerWebServiceController {
    
    @Autowired
    EmployerRepository employerRepository;
    
    @RequestMapping(value = "/employer-suggestions.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, List<String>> getSuggestions(@RequestParam(value = "query", required = false) String query) {
        List<String> suggestions = new ArrayList();
        for(Employer employer : employerRepository.findAll()) {
            suggestions.add(employer.getName());
        }
        
        Map<String, List<String>> result = new HashMap<>();
        result.put("suggestions", suggestions);
        return result;
    }
    
    @RequestMapping(value = "/employer-info.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Employer getEmployerInfo(@RequestParam("name") String name) {
        Employer employer = employerRepository.findByName(name);
        if(employer == null) return new Employer();
        return employer;
    }
    
}
