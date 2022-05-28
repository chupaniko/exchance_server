package com.example.exchance_server.person.info.workexperience;


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
public class WorkExperience {
    @SequenceGenerator(
            name = "workexp_sequence",
            sequenceName = "workexp_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workexp_sequence"
    )
    private Long id;
    private String institute;
    private String post;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(
            nullable = false,
            name = "person_id"
    )
    private Person person;

    public WorkExperience(String institute, String post, Person person) {
        this.institute = institute;
        this.post = post;
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WorkExperience that = (WorkExperience) o;
        return id.equals(that.id) && Objects.equals(institute, that.institute) && Objects.equals(post, that.post) && person.equals(that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, institute, post, person);
    }
}
