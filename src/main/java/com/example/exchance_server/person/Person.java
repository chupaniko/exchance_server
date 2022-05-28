package com.example.exchance_server.person;

import com.example.exchance_server.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Person {
    @SequenceGenerator(
            name = "person_sequence",
            sequenceName = "person_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "person_sequence"
    )
    private Long id;

    private String surname;
    private String patronymic;
    @OneToOne()
    @JoinColumn(
            name = "app_user_id"
    )
    private AppUser appUser;
    private boolean gender;
    private String citizenship;
    private LocalDateTime birthday;

    public Person(String surname, String patronymic, AppUser appUser, boolean gender, String citizenship, LocalDateTime birthday) {
        this.surname = surname;
        this.patronymic = patronymic;
        this.appUser = appUser;
        this.gender = gender;
        this.citizenship = citizenship;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public boolean isGender() {
        return gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return gender == person.gender && id.equals(person.id) && Objects.equals(surname, person.surname) && Objects.equals(patronymic, person.patronymic) && Objects.equals(appUser, person.appUser) && Objects.equals(citizenship, person.citizenship) && Objects.equals(birthday, person.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, patronymic, appUser, gender, citizenship, birthday);
    }
}
