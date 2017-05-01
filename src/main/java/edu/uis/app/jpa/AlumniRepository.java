package edu.uis.app.jpa;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Pablo Delgado <pdelg2@uis.edu>
 */
@Repository
public interface AlumniRepository extends JpaRepository<Alumni, Long> {
    Alumni findByUin(String uin);
    Alumni findByEmail(String email);
    List<Alumni> findByGraduated(Boolean graduated);
    Page<Alumni> findByGraduated(Boolean graduated, Pageable pageable);
    List<Alumni> findByEmployer(Employer employer);
    List<Alumni> findByEmployerNotNull();
    List<Alumni> findByEmployerIsNull();
}
