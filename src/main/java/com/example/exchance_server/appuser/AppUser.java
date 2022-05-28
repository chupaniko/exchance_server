package com.example.exchance_server.appuser;

import com.example.exchance_server.userproject.UserProject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {

    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence"
    )
    private Long id;


    // TODO: проверка на уникальность, если пользователь - организация
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked = false;
    private Boolean enabled = false;
    private String website;
    private String about;
    @Column(unique = true, nullable = false)
    private String phone;
    private String region;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] avatar;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "projects_users",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_project_id"))
    private Set<UserProject> projects;

    @JsonIgnore
    @OneToMany
    private Set<UserProject> projectSubscriptions;
    // для регистрации
    public AppUser(String name, String email,
                   String password,
                   AppUserRole appUserRole,
                   String website,
                   String about,
                   String phone,
                   String region,
                   byte[] avatar) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.website = website;
        this.about = about;
        this.phone = phone;
        this.region = region;
        this.avatar = avatar;
    }

    // для входа
    public AppUser(String email,
                   String password,
                   AppUserRole appUserRole) {
        this.name = "";
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.website = "";
        this.about = "";
        this.phone = "";
        this.region = "";
        this.avatar = new byte[]{0};
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public String getAbout() {
        return about;
    }

    public String getPhone() {
        return phone;
    }

    public String getRegion() {
        return region;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public Set<UserProject> getProjects() {
        return projects;
    }

    public Set<UserProject> getProjectSubscriptions() {
        return projectSubscriptions;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser appUser = (AppUser) o;

        return Objects.equals(id, appUser.id);
    }

    // TODO: сделать нормальный метод возвращеня хеш-кода
    @Override
    public int hashCode() {
        //return Objects.hash(id, token, firstName, lastName, ...);
        return 741337932;
    }
}
