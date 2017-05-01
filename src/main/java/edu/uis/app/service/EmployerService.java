package edu.uis.app.service;

import edu.uis.app.jpa.Employer;
import edu.uis.app.jpa.EmployerRepository;
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
    private EmployerRepository repository;

    @Autowired
    public void setRepository(EmployerRepository repository) {
        this.repository = repository;
    }

    public void saveEmployer(Employer employer) {
        if(employer != null) repository.save(employer);
    }

    public void deleteEmployerWithId(Employer employer) {
        if(employer != null) repository.delete(employer);
    }

    public List<Employer> getEmployerPage(Integer pageNumber) {
        PageRequest request = new PageRequest(pageNumber, PAGE_SIZE, Sort.Direction.ASC, "name");
        Page<Employer> result = repository.findAll(request);
        if(result == null) 
            return new ArrayList<>();
        return result.getContent();
    }

}
