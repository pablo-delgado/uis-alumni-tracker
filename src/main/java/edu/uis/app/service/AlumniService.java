package edu.uis.app.service;

import edu.uis.app.data.model.Alumni;
import edu.uis.app.data.repository.AlumniRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author Pablo Delgado
 */
@Service
public class AlumniService {
    private static final Integer PAGE_SIZE = 20;
    private AlumniRepository repository;

    @Autowired
    public void setRepository(AlumniRepository repository) {
        this.repository = repository;
    }
    
    public List<Alumni> getAlumniPage(Integer pageNumber) {
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE, Sort.Direction.ASC, "lastName");
        Page<Alumni> result = repository.findAll(request);
        if(result == null) 
            return new ArrayList<>();
        return result.getContent();
    }
    
    public List<Alumni> getGraduateAlumniPage(Integer pageNumber) {
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE, Sort.Direction.ASC, "lastName");
        Page<Alumni> result = repository.findByGraduated(true, request);
        if(result == null) 
            return new ArrayList<>();
        return result.getContent();
    }

    public Boolean saveAlumni(Alumni alumni) {
        alumni = repository.save(alumni);
        return true;
    }

    public void deleteAlumniWithId(Alumni alumni) {
        if(alumni != null) repository.delete(alumni);
    }
        
}
