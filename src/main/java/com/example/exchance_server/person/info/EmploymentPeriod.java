package com.example.exchance_server.person.info;

import com.example.exchance_server.organization.Organization;
import com.example.exchance_server.person.info.education.PersonEducation;
import com.example.exchance_server.person.info.workexperience.WorkExperience;
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
public class EmploymentPeriod {
    @SequenceGenerator(
            name = "empl_period_sequence",
            sequenceName = "empl_period_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "empl_period_sequence"
    )
    private Long id;
    private String orgName;
    private int startYear;
    private int endYear;
    @OneToOne
    @JoinColumn(name = "person_education_id")
    private PersonEducation personEducation;
    @OneToOne
    @JoinColumn(name = "work_experience_id")
    private WorkExperience workExperience;
    @OneToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public EmploymentPeriod(String orgName, int startYear, int endYear, PersonEducation personEducation, WorkExperience workExperience, Organization organization) {
        this.orgName = orgName;
        this.startYear = startYear;
        this.endYear = endYear;
        this.personEducation = personEducation;
        this.workExperience = workExperience;
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EmploymentPeriod that = (EmploymentPeriod) o;
        return startYear == that.startYear && endYear == that.endYear && id.equals(that.id) && Objects.equals(orgName, that.orgName) && Objects.equals(personEducation, that.personEducation) && Objects.equals(workExperience, that.workExperience) && Objects.equals(organization, that.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orgName, startYear, endYear, personEducation, workExperience, organization);
    }
}
