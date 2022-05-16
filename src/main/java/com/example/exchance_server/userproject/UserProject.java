package com.example.exchance_server.userproject;

import com.example.exchance_server.appuser.AppUser;
import com.example.exchance_server.appuser.AppUserRole;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_generator")
    @SequenceGenerator(name = "project_generator", sequenceName = "project_sequence", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;
    private String projectName;
    private String projectDescription;
    //@Enumerated(EnumType.STRING)
    private String projectField;

    public UserProject(AppUser appUser,
                       String projectName,
                       String projectDescription,
                       String projectField) {
        this.appUser = appUser;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectField = projectField;
    }

    /*//для публикации проекта
    public UserProject(String token,
                       String projectName,
                       String projectDescription,
                       String projectField) {
        AppUser user = userRepository.findByToken(token).orElse(new AppUser("", "", AppUserRole.USER));
    }*/

    public Long getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserProject userProject = (UserProject) o;

        return Objects.equals(id, userProject.id);
    }

    // TODO: сделать нормальный метод возвращеня хеш-кода
    @Override
    public int hashCode() {
        return 741337932;
    }
}
