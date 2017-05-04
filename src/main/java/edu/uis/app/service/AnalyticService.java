package edu.uis.app.service;

import edu.uis.app.data.model.Alumni;
import edu.uis.app.data.repository.AlumniRepository;
import edu.uis.app.data.model.Employer;
import edu.uis.app.data.repository.EmployerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pablo Delgado
 */
@Service
public class AnalyticService {
    
    private AlumniRepository alumniRepository;
    private EmployerRepository employerRepository;

    @Autowired
    public void setAlumniRepository(AlumniRepository alumniRepository) {
        this.alumniRepository = alumniRepository;
    }

    @Autowired
    public void setEmployerRepository(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public Integer studentAlumniCount() {
        List<Alumni> students = alumniRepository.findByGraduated(Boolean.FALSE);        
        if(students == null) return 0;
        else return students.size();
    }

    public Integer graduatedAlumniCount() {
        List<Alumni> students = alumniRepository.findByGraduated(Boolean.TRUE);
        if(students == null) return 0;
        else return students.size();
    }

    public Integer employerCount() {
        List<Employer> employers = employerRepository.findAll();
        if(employers == null) return 0;
        else return employers.size();
    }
    
}
