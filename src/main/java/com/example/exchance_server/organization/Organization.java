package com.example.exchance_server.organization;


import com.example.exchance_server.appuser.AppUser;
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
public class Organization {
    @SequenceGenerator(
            name = "organization_sequence",
            sequenceName = "organization_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "organization_sequence"
    )
    private Long id;
    private String numTaxpayer;
    @OneToOne()
    @JoinColumn(
            name = "app_user_id"
    )
    private AppUser appUser;

    public Organization(String numTaxpayer, AppUser appUser) {
        this.numTaxpayer = numTaxpayer;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public String getNumTaxpayer() {
        return numTaxpayer;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Organization that = (Organization) o;
        return id.equals(that.id) && Objects.equals(numTaxpayer, that.numTaxpayer) && Objects.equals(appUser, that.appUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numTaxpayer, appUser);
    }
}
