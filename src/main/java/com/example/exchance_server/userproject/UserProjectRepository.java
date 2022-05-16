package com.example.exchance_server.userproject;

import com.example.exchance_server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    Optional<UserProject> findUserProjectByAppUser(AppUser user);
    Optional<UserProject> findUserProjectByProjectName(String name);

    @Override
    List<UserProject> findAll();
    //обновление проекта
}
