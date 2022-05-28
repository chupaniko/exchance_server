package com.example.exchance_server.userproject;

import com.example.exchance_server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    //Optional<UserProject> findUserProjectByAppUser(AppUser user);
    Optional<UserProject> findUserProjectByName(String name);

    @Override
    List<UserProject> findAll();

    @Query("select p.appUsers from UserProject p where p.id = :projectId")
    Optional<List<AppUser>> findAppUsersByProject(Long projectId);

    @Transactional
    @Modifying
    @Query("update UserProject p set p.likesQty = p.likesQty + 1 where p.name = :name")
    int setLike(String name);

    /*@Transactional
    @Modifying
    @Query("update UserProject p set p.appUsers = :users where p.id = :projectId")
    int updateAppUsersInProject(Set<AppUser> users, Long projectId);*/

    /*@Query("select p from UserProject p")
    List<UserProject> getAllProjects();*/

    //обновление проекта
}
