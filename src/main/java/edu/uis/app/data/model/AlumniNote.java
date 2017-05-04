package edu.uis.app.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Pablo Delgado <pdelg2@uis.edu>
 */
@Entity
@Table(name = "uis_alumni_note")
public class AlumniNote implements Serializable, Comparable<AlumniNote> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Lob
    private String text;
    
    @ManyToOne
    @JoinColumn(name="alumni")
    private Alumni alumni;

    public AlumniNote() {
        this.dateCreated = new Date();
        this.text = "";
    }

    public AlumniNote(String text) {
        this.text = text;
    }

    public AlumniNote(Date dateCreated, String text) {
        this.dateCreated = dateCreated;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Alumni getAlumni() {
        return alumni;
    }

    public void setAlumni(Alumni alumni) {
        this.alumni = alumni;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AlumniNote other = (AlumniNote) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.text;
    }

    @Override
    public int compareTo(AlumniNote other) {
        return this.dateCreated.compareTo(dateCreated);
    }
    
}
