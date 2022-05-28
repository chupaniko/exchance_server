package com.example.exchance_server.userproject;

import com.example.exchance_server.appuser.AppUser;
import com.example.exchance_server.appuser.AppUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_generator")
    @SequenceGenerator(name = "project_generator", sequenceName = "project_sequence", allocationSize = 1)
    private Long id;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "projects_users",
            joinColumns = @JoinColumn(name = "user_project_id"),
            inverseJoinColumns = @JoinColumn(name = "app_user_id"))
    private Set<AppUser> appUsers;
    @Column(unique = true, nullable = false)
    private String name;
    private String launchReason;
    private LocalDateTime createdAt;
    private String need;
    private String goalsResCriteria;
    private String tasks;
    private String orgBorders;
    private String budget;
    private String time;
    private String restrictions;
    private String risks;
    private String customer;
    private String leader;
    private Long likesQty = 0L;

    public String getName() {
        return name;
    }

    public String getLaunchReason() {
        return launchReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getNeed() {
        return need;
    }

    public String getGoalsResCriteria() {
        return goalsResCriteria;
    }

    public String getTasks() {
        return tasks;
    }

    public String getOrgBorders() {
        return orgBorders;
    }

    public String getBudget() {
        return budget;
    }

    public String getTime() {
        return time;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public String getRisks() {
        return risks;
    }

    public String getCustomer() {
        return customer;
    }

    public String getLeader() {
        return leader;
    }

    public Long getLikesQty() {
        return likesQty;
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

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public UserProject(Set<AppUser> appUsers,
                       String name,
                       String launchReason,
                       LocalDateTime createdAt,
                       String need,
                       String goalsResCriteria,
                       String tasks,
                       String orgBorders,
                       String budget,
                       String time,
                       String restrictions,
                       String risks,
                       String customer,
                       String leader) {

        this.appUsers = appUsers;
        this.name = name;
        this.launchReason = launchReason;
        this.createdAt = createdAt;
        this.need = need;
        this.goalsResCriteria = goalsResCriteria;
        this.tasks = tasks;
        this.orgBorders = orgBorders;
        this.budget = budget;
        this.time = time;
        this.restrictions = restrictions;
        this.risks = risks;
        this.customer = customer;
        this.leader = leader;
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
