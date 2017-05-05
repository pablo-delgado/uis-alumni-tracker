package edu.uis.app.data.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Pablo Delgado
 */
@Entity
@Table(name = "uis_employer")
public class Employer implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true)
    private String name;
    
    private String city;
    
    private String state;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployerContact contactPerson;
    
    public Employer() {
    }

    public Employer(String name) {
        this.name = name;
    }

    public Employer(String name, String city, String state) {
        this.name = name;
        this.city = city;
        this.state = state;
    }

    public Employer(String name, String city, String state, EmployerContact contactPerson) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.contactPerson = contactPerson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public EmployerContact getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(EmployerContact contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.name);
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
        final Employer other = (Employer) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
}
