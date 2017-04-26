package edu.uis.app.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Pablo Delgado
 */
@Entity
@Table(name = "uis_alumni")
public class Alumni implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true)
    private String uin;    
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "email", unique = true)
    private String email;
    
    private Boolean graduated;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "graduation_date")
    private Date graduationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employer")
    private Employer employer;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumni")
    private List<AlumniNote> notes;

    public Alumni() {
        this.graduated = Boolean.FALSE;        
    }

    public Alumni(String uin, String firstName, String lastName, String phone, String email, Boolean graduated, Date graduationDate, Employer employer) {
        this.uin = uin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.graduated = graduated;
        this.graduationDate = graduationDate;
        this.employer = employer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGraduated() {
        return graduated;
    }

    public void setGraduated(Boolean graduated) {
        this.graduated = graduated;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<AlumniNote> getNotes() {
        return notes;
    }

    public void setNotes(List<AlumniNote> notes) {
        this.notes = notes;
    }
    
    // Auxilary methods
    public void addNote(AlumniNote note) {
        if(this.notes == null) 
            this.notes = new ArrayList<>();
        this.notes.add(note);
    }
    
    public void removeNote(AlumniNote note) {
        if(this.notes != null)
            this.notes.remove(note);
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
        final Alumni other = (Alumni) obj;
        if (!Objects.equals(this.uin, other.uin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
    
}