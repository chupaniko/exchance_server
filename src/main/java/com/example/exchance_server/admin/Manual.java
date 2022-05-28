package com.example.exchance_server.admin;

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
public class Manual {
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
    @Column(unique = true, nullable = false)
    private String version;
    @Lob
    private String aboutProjConstructor;
    @Lob
    private String whatsNew;
    @Lob
    private String aboutDev;

    public Manual(String version, String aboutProjConstructor, String whatsNew, String aboutDev) {
        this.version = version;
        this.aboutProjConstructor = aboutProjConstructor;
        this.whatsNew = whatsNew;
        this.aboutDev = aboutDev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Manual manual = (Manual) o;
        return id.equals(manual.id) && version.equals(manual.version) && Objects.equals(aboutProjConstructor, manual.aboutProjConstructor) && Objects.equals(whatsNew, manual.whatsNew) && Objects.equals(aboutDev, manual.aboutDev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, aboutProjConstructor, whatsNew, aboutDev);
    }
}
