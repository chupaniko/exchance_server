package com.example.exchance_server.person.info.education;

import com.example.exchance_server.appuser.AppUser;
import com.example.exchance_server.person.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PersonEducation {
    @SequenceGenerator(
            name = "education_sequence",
            sequenceName = "education_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "education_sequence"
    )
    private Long id;

    private String facultyName;
    private String specialization;
    private String degree;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(
            nullable = false,
            name = "person_id"
    )
    private Person person;

    public PersonEducation(String facultyName, String specialization, String degree, Person person) {
        this.facultyName = facultyName;
        this.specialization = specialization;
        this.degree = degree;
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PersonEducation that = (PersonEducation) o;
        return id.equals(that.id) && Objects.equals(facultyName, that.facultyName) && Objects.equals(specialization, that.specialization) && Objects.equals(degree, that.degree) && person.equals(that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, facultyName, specialization, degree, person);
    }
}
