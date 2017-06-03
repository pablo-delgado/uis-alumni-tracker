package edu.uis.app.service;

import edu.uis.app.data.model.Alumni;
import edu.uis.app.data.model.AlumniNote;
import edu.uis.app.data.model.Employer;
import edu.uis.app.data.model.EmployerContact;
import edu.uis.app.data.repository.AlumniNoteRepository;
import edu.uis.app.data.repository.AlumniRepository;
import edu.uis.app.data.repository.EmployerRepository;
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
    private AlumniRepository alumniRepository;
    private AlumniNoteRepository alumniNoteRepository;

    @Autowired
    public void setAlumniRepository(AlumniRepository alumniRepository) {
        this.alumniRepository = alumniRepository;
    }

    @Autowired
    public void setAlumniNoteRepository(AlumniNoteRepository alumniNoteRepository) {
        this.alumniNoteRepository = alumniNoteRepository;
    }

    public List<Alumni> getAlumniPage(Integer pageNumber) {
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE, Sort.Direction.ASC, "lastName");
        Page<Alumni> result = alumniRepository.findAll(request);
        if(result == null) 
            return new ArrayList<>();
        return result.getContent();
    }
    
    public List<Alumni> getGraduateAlumniPage(Integer pageNumber) {
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE, Sort.Direction.ASC, "lastName");
        Page<Alumni> result = alumniRepository.findByGraduated(true, request);
        if(result == null) 
            return new ArrayList<>();
        return result.getContent();
    }

    public Boolean saveAlumni(Alumni alumni) {
        Alumni existing = alumniRepository.findOne(alumni.getId());
        if(existing.getEmployer() != null) {
            alumni.setEmployer(existing.getEmployer());
            alumni.setEmployed(Boolean.TRUE);
        }
        
        alumniRepository.save(alumni);
        
        return true;
    }
        
    public void deleteAlumniWithId(Alumni alumni) {
        if(alumni != null) alumniRepository.delete(alumni);
    }

    public Boolean addNote(Long alumniId, String note) {
        if(alumniId == null || note == null) return false;
        
        Alumni alumni = alumniRepository.findOne(alumniId);
        if(alumni == null) return false;
        
        alumni.addNote(new AlumniNote(note));        
        alumniRepository.save(alumni);
        
        return true;
    }

    public Boolean removeNote(Long noteId) {
        alumniNoteRepository.delete(noteId);
        return true;
    }
    
    public List<Alumni> getAlumniByEmployer(Employer employer) {
        if(employer == null) return new ArrayList<Alumni>();
        return alumniRepository.findByEmployer(employer);
    }
    
    public void addEmployer(Alumni alumni, Employer employer) {
        alumni.setEmployer(employer);
        alumni.setEmployed(Boolean.TRUE);
        alumniRepository.save(alumni);
    }

    public void removeEmployer(Alumni alumni) {   
        alumni.setEmployer(null);
        alumni.setEmployed(Boolean.FALSE);
        alumniRepository.save(alumni);
    }
    
}
