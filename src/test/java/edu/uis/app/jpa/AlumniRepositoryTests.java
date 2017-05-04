package edu.uis.app.jpa;

/**
 *
 * @author Pablo Delgado
 */
import edu.uis.app.data.model.AlumniNote;
import edu.uis.app.data.repository.AlumniRepository;
import edu.uis.app.data.model.Employer;
import edu.uis.app.data.model.Alumni;
import java.util.Date;
import java.util.List;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AlumniRepositoryTests {
    final String NOTE1 = "P1 Student Note 1";
    final String NOTE2 = "P1 Student Note 2";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AlumniRepository repository;

    @Test
    public void count() {                
        assertThat((repository.findAll()).size()).isEqualTo(8);
    }
    
    @Test
    public void equality() {
        Alumni unsavedStudent = new Alumni("000001", "P1FName", "P1LName", "000-000-0000", "p1@uis.edu", Boolean.FALSE, null, Boolean.FALSE, null, null);
        assertThat((repository.findByUin("000001")).equals(unsavedStudent)).isTrue();
        
        Alumni unsavedGraduate = new Alumni("100002", "2PFName", "2PLName", "000-000-0000", "2p@uis.edu", Boolean.TRUE, new Date(1464753600), Boolean.FALSE, null, null);
        assertThat((repository.findByUin("100002")).equals(unsavedGraduate)).isTrue();
    }
    
    @Test
    public void findStudentByUIN() {
        assertThat((repository.findByUin("000001")).getFirstName()).isEqualTo("P1FName");
        assertThat((repository.findByUin("000006")).getFirstName()).isEqualTo("P6FName");
    }
    
    @Test
    public void findStudentByEmail() {
        assertThat((repository.findByEmail("p1@uis.edu")).getFirstName()).isEqualTo("P1FName");
        assertThat((repository.findByEmail("p3@uis.edu")).getFirstName()).isEqualTo("P3FName");
        assertThat((repository.findByEmail("p6@uis.edu")).getFirstName()).isEqualTo("P6FName");
    }
    
    @Test
    public void findStudentsByEmployer() {
        Employer employer = (repository.findByUin("000004")).getEmployer();
        List<Alumni> alumni = repository.findByEmployer(employer);
        assertThat(alumni.size()).isEqualTo(1);
        assertThat(alumni.get(0).getUin()).isEqualTo("000004");
    }
    
    @Test
    public void findCurrentStudents() {
        Alumni student1 = repository.findByUin("000001");
        List<Alumni> alumni = repository.findByGraduated(false);
        assertThat(alumni.size()).isEqualTo(6);
        assertThat(alumni.contains(student1)).isTrue();
    }
    
    @Test
    public void findGraduatedStudents() {
        Alumni student1 = repository.findByUin("100001");
        List<Alumni> alumni = repository.findByGraduated(true);
        assertThat(alumni.size()).isEqualTo(2);
        assertThat(alumni.contains(student1)).isTrue();
    }
    
    @Test
    public void addNotes() {
        Alumni student1 = repository.findByUin("000001");
        student1.addNote(new AlumniNote(NOTE1));
        student1.addNote(new AlumniNote(NOTE2));
        repository.saveAndFlush(student1);
        
        List<AlumniNote> notes = (repository.findByUin("000001")).getNotes();
        assertThat(notes.size()).isEqualTo(2);
        assertThat(notes.get(0).getText().equals(NOTE1) ||
                   notes.get(1).getText().equals(NOTE1)).isTrue();
    }
    
    @Test
    public void removeNote() {
        Alumni student1 = repository.findByUin("000001");
        student1.addNote(new AlumniNote(NOTE1));
        student1.addNote(new AlumniNote(NOTE2));
        repository.saveAndFlush(student1);
        
        
        AlumniNote note = (repository.findByUin("000001")).getNotes().get(0);        
        student1 = repository.findByUin("000001");
        student1.removeNote(note);
        repository.saveAndFlush(student1);
        
        assertThat((repository.findByUin("000001").getNotes()).size()).isEqualTo(1);
        assertThat((repository.findByUin("000001").getNotes()).get(0).equals(note)).isFalse();
    }
    
    @Test
    public void unemployedStudents() {        
        assertThat(repository.findByEmployer(null).size()).isEqualTo(5);
        assertThat((repository.findByEmployerIsNull()).size()).isEqualTo(5);
    }
    
    @Test
    public void employedStudents() {
        List<Alumni> alumni = repository.findByEmployerNotNull();
        assertThat(alumni.size()).isEqualTo(3);
    }
    
    @Before
    public void setup() {        
        // Current Students
        repository.save(new Alumni("000001", "P1FName", "P1LName", "000-000-0000", "p1@uis.edu", Boolean.FALSE, null, Boolean.FALSE, null, null));
        repository.save(new Alumni("000002", "P2FName", "P2LName", "000-000-0000", "p2@uis.edu", Boolean.FALSE, null, Boolean.FALSE, null, null));
        repository.save(new Alumni("000003", "P3FName", "P3LName", "000-000-0000", "p3@uis.edu", Boolean.FALSE, null, Boolean.FALSE, null, null));
        repository.save(new Alumni("000004", "P4FName", "P4LName", "000-000-0000", "p4@uis.edu", Boolean.FALSE, null, Boolean.TRUE, new Employer("Company P4", "Springfield", "IL"), null));
        repository.save(new Alumni("000005", "P5FName", "P5LName", "000-000-0000", "p5@uis.edu", Boolean.FALSE, null, Boolean.FALSE, null, null));
        repository.save(new Alumni("000006", "P6FName", "P6LName", "000-000-0000", "p6@uis.edu", Boolean.FALSE, null, Boolean.TRUE, new Employer("Company P6", "Miami", "FL"), null));
        
        // Graduated Students
        repository.save(new Alumni("100001", "1PFName", "1PLName", "000-000-0000", "1p@uis.edu", Boolean.TRUE, new Date(1433131200), Boolean.TRUE, new Employer("Company 1P", "Springfield", "IL"), null));
        repository.save(new Alumni("100002", "2PFName", "2PLName", "000-000-0000", "2p@uis.edu", Boolean.TRUE, new Date(1464753600), Boolean.FALSE, null, null));
    }
    
    @After
    public void teardown() {
        
    }

}