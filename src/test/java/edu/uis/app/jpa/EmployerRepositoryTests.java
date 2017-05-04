package edu.uis.app.jpa;

import edu.uis.app.data.repository.AlumniRepository;
import edu.uis.app.data.model.Employer;
import edu.uis.app.data.model.Alumni;
import edu.uis.app.data.model.EmployerContact;
import edu.uis.app.data.repository.EmployerRepository;
import java.util.Date;
import java.util.List;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * @author Pablo Delgado
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployerRepositoryTests {
    
    @Autowired
    private EmployerRepository repository;
    
    @Autowired
    private AlumniRepository alumniRepository;
    
    @Test
    public void count() {
        assertThat(repository.findAll().size()).isEqualTo(4);
    }
    
    @Test
    public void equality() {
        Employer emp1 = new Employer("Emp 1", "Miami", "FL");
        assertThat(repository.findByName("Emp 1").equals(emp1)).isTrue();
    }
    
    @Test
    public void findByName() {
        assertThat(repository.findByName("Emp 1")).isNotNull();
    }
    
    @Test
    public void findByCity() {
        assertThat(repository.findByCity("Miami").size()).isEqualTo(1);
        assertThat(repository.findByCity("Springfield").size()).isEqualTo(2);
    }
    
    @Test
    public void findByState() {
        assertThat(repository.findByState("FL").size()).isEqualTo(1);
        assertThat(repository.findByState("IL").size()).isEqualTo(2);
    }
    
    @Test
    public void getContactPerson() {
        EmployerContact contact = repository.findByName("Emp 4").getContactPerson();
        assertThat(contact).isNotNull();
        assertThat(contact.getEmail()).isEqualTo("cp2@emp4.com");
        assertThat(contact.getRole()).isEqualTo("Human Resource");
    }
    
    @Test
    public void getAlumni() {
        List<Alumni> alumni = repository.findByName("Emp 4").getEmployees();
        assertThat(alumni.size()).isEqualTo(2);        
    }
    
    @Before
    public void setup() {       
        
        repository.save(new Employer("Emp 1", "Miami", "FL"));
        repository.save(new Employer("Emp 2", "Springfield", "IL"));
        repository.save(new Employer("Emp 3", "Springfield", "IL"));
        
        // Employer with contact and alumni
        Employer employer = new Employer("Emp 4", "Brooklyn", "NY");
        employer.setContactPerson(new EmployerContact("CP1FirstName", "CP2LastName", "Human Resource", "123-456-7890", "cp2@emp4.com"));
        employer = repository.saveAndFlush(employer);
        
        List<Alumni> alumni = alumniRepository.findAll();
        if(alumni == null || alumni.size() == 0) {
            Date employmentDate = new Date(1433131200);
            alumniRepository.save(new Alumni("100001", "1PFName", "1PLName", "000-000-0000", "1p@uis.edu", Boolean.TRUE, employmentDate, Boolean.TRUE, employer, null));
            alumniRepository.save(new Alumni("100002", "2PFName", "2PLName", "000-000-0000", "2p@uis.edu", Boolean.TRUE, employmentDate, Boolean.TRUE, employer, null));
            
            alumni = alumniRepository.findAll();
        }
        
        employer.setEmployees(alumni);
        repository.save(employer);
    }
    
    @After
    public void teardown() {
        
    }
    
}
