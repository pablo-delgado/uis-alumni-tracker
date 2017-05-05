package edu.uis.app.data.repository;

import edu.uis.app.data.model.AlumniNote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pablo Delgado
 */
public interface AlumniNoteRepository extends JpaRepository<AlumniNote, Long> {
    
}
