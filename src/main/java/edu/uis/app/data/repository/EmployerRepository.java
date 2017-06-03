package edu.uis.app.data.repository;

import edu.uis.app.data.model.Employer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Pablo Delgado
 */
@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long>{
    Employer findByName(String name);
    List<Employer> findByNameIgnoreCaseContaining(String name);
    List<Employer> findByCity(String city);
    List<Employer> findByState(String state);
}
