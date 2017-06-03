package edu.uis.app.service;

import edu.uis.app.data.model.Alumni;
import edu.uis.app.data.model.Employer;
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
public class EmployerService {
    private static final Integer PAGE_SIZE = 20;
    private EmployerRepository employerRepository;
    private AlumniRepository alumniRepository;

    @Autowired
    public void setEmployerRepository(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @Autowired
    public void setAlumniRepository(AlumniRepository alumniRepository) {
        this.alumniRepository = alumniRepository;
    }

    public void saveEmployer(Employer employer) {
        if(employer != null) 
            employerRepository.save(employer);
    }

    public void deleteEmployer(Employer employer) {
         for (Alumni alumni : alumniRepository.findByEmployer(employer)) {
            System.out.println("FOUND ALUMNI: " + alumni);
            alumni.setEmployed(Boolean.FALSE);
            alumni.setEmployer(null);
            alumniRepository.saveAndFlush(alumni);
        }
         
        if(employer != null) 
            employerRepository.delete(employer);
    }

    public List<Employer> getEmployerPage(Integer pageNumber) {
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE, Sort.Direction.ASC, "name");
        Page<Employer> result = employerRepository.findAll(request);
        if(result == null) 
            return new ArrayList<>();
        return result.getContent();
    }

    public List<Employer> findEmployerContaining(String queryString) {
        return employerRepository.findByNameIgnoreCaseContaining(queryString);
    }

}
