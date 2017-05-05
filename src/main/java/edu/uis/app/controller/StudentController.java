package edu.uis.app.controller;

import edu.uis.app.data.model.Alumni;
import edu.uis.app.data.model.Employer;
import edu.uis.app.service.AlumniService;
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
@RequestMapping(value = "/students")
public class StudentController {
    
    private AlumniService service;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
         
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
   
    @Autowired
    public void setService(AlumniService service) {
        this.service = service;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, @RequestParam(defaultValue = "0") Integer pageNumber) {
        model.addAttribute("alumni", service.getAlumniPage(pageNumber));
        return "student/index";
    }
    
    @RequestMapping(value = "/{id}/view")
    public String view(@PathVariable("id") Alumni alumni, Model model) {
        model.addAttribute("alumni", alumni);
        return "student/view";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("alumni", new Alumni());
        return "student/form";
    }
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Alumni alumni, Model model) {
        if(alumni == null || alumni.getId() == null) return "redirect:/students";
        model.addAttribute("alumni", alumni);
        return "student/form";
    }
    
    @RequestMapping(value = {"/new", "/{id}/edit"}, method = RequestMethod.POST)
    public String save(Alumni alumni, BindingResult bindingResult, Employer employer, @ModelAttribute("afterAction") String afterAction, Model model) throws Exception {
        if(bindingResult.hasErrors()) {
            model.addAttribute("alumni", alumni);
            return "student/form";
        }
        
        service.saveAlumni(alumni);
        
        if(afterAction.equals("close"))
            return "redirect:/students";
        
        return "redirect:/students/".concat(alumni.getId().toString()).concat("/view");
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Alumni alumni) {
        service.deleteAlumniWithId(alumni);
        return "redirect:/students";
    }
    
    @RequestMapping(value = "/{id}/note/add", method = RequestMethod.POST)
    public String addNote(@PathVariable("id") Long alumniId, @RequestParam("note") String note) {
        if(note.isEmpty())
            System.out.println("report error via request attribute and skip: Warning: emptry string will not be saved.");
        else 
            if(service.addNote(alumniId, note) == false) 
                System.out.println("Report errors via request attributes: Error ocurrred Unable to save note");
            
            
        return "redirect:/students/" + alumniId + "/view";
    }
    
    @RequestMapping(value = "/{id}/note/{noteId}/delete")
    public String deleteNote(@PathVariable("id") Long alumniId, @PathVariable("noteId") Long noteId) {
        if(alumniId == null || noteId == null)
            System.out.println("Report Error");
        else
            if(service.removeNote(noteId) == false)
                System.out.println("Report error");
        
        return "redirect:/students/" + alumniId + "/view";
    }
    
    @RequestMapping(value = "/graduates", method = RequestMethod.GET)
    public String graduates(Model model, @RequestParam(defaultValue = "0") Integer pageNumber) {
        model.addAttribute("alumni", service.getGraduateAlumniPage(pageNumber));
        return "student/index";
    }
    
}
