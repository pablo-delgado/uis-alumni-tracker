package edu.uis.app.controller;

import edu.uis.app.data.model.Alumni;
import edu.uis.app.data.model.Employer;
import edu.uis.app.service.EmployerService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Pablo Delgado
 */
@Controller
@RequestMapping(value = "/employers")
public class EmployerController {
    
    private EmployerService service;

    @Autowired
    public void setService(EmployerService service) {
        this.service = service;
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
         
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, @RequestParam(defaultValue = "0") Integer pageNumber) {
        model.addAttribute("employers", service.getEmployerPage(pageNumber));
        return "employer/index";
    }
    
    @RequestMapping(value = "/{id}/view")
    public String view(@PathVariable("id") Employer employer, Model model) {
        model.addAttribute("employer", employer);
        return "employer/view";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("employer", new Employer());
        return "employer/form";
    }
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Employer employer, Model model) {
        if(employer == null || employer.getId() == null) return "redirect:/employer";
        model.addAttribute("employer", employer);
        return "employer/form";
    }
    
    @RequestMapping(value = {"/new", "/{id}/edit"}, method = RequestMethod.POST)
    public String save(Employer employer, BindingResult bindingResult, @ModelAttribute("afterAction") String afterAction, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("employer", employer);
            return "employer/form";
        }
        
        service.saveEmployer(employer);
        if(afterAction.equals("close"))
            return "redirect:/employers";
        
        return "redirect:/employers/".concat(employer.getId().toString()).concat("/view");
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Employer employer) {
        service.deleteEmployerWithId(employer);
        return "redirect:/employers";
    }
    
}
