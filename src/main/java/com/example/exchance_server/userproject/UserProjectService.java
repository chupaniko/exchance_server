package com.example.exchance_server.userproject;

import com.example.exchance_server.appuser.AppUser;
import com.example.exchance_server.appuser.AppUserRepository;
import com.example.exchance_server.appuser.AppUserRole;
import com.example.exchance_server.appuser.AppUserService;
import com.example.exchance_server.projectconstructor.AddUsersToProjectRequest;
import com.example.exchance_server.projectconstructor.ConstructorRequest;
import com.example.exchance_server.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserProjectService {
    private final static String PROJECT_NOT_FOUND_MSG =
            "project with name %s not found";
    private final UserProjectRepository projectRepository;
    private final AppUserService userService;

    public String publishProject(ConstructorRequest request) {
        // TODO if project exists...

        AppUser user = userService.getUserByToken(request.getToken());
        Set<AppUser> users = new HashSet<>();
        users.add(user);
        UserProject project = new UserProject(users,
                request.getName(),
                request.getLaunchReason(),
                request.getCreatedAt(),
                request.getNeed(),
                request.getGoalsResCriteria(),
                request.getTasks(),
                request.getOrgBorders(),
                request.getBudget(),
                request.getTime(),
                request.getRestrictions(),
                request.getRisks(),
                request.getCustomer(),
                request.getLeader());
        boolean projectExits = projectRepository
                .findUserProjectByName(project.getName())
                .isPresent();
        if (projectExits) {
            // TODO check of attributes are the same
            throw new IllegalStateException("project already exists");
        }
        projectRepository.save(project);
        return "Project: \"" + request.getName() + "\" for user " + user.getName() + " saved successfully.";
    }

    public UserProject getProjectByName(String name) {
        return projectRepository.findUserProjectByName(name).orElseThrow(() ->
                new IllegalStateException("project not found"));
    }

    public List<AppUser> getProjectOwners(String name) {
        return projectRepository.findAppUsersByProject(getProjectByName(name).getId()).orElseThrow(() ->
                new IllegalStateException("users not found"));
    }

    public String addUserToProject(AddUsersToProjectRequest request) {
        AppUser user = userService.getUserByToken(request.getToken());
        UserProject project = getProjectByName(request.getName());
        List<AppUser> users = getProjectOwners(request.getName());

        if (!users.contains(user))
            throw new IllegalStateException("access denied");

        project.getAppUsers().add(userService.getUserByEmail(request.getUserEmail()));
        projectRepository.save(project);
        //нужно ли обновление поля для AppUser?
        //projectRepository.updateAppUsersInProject(users, project.getId());
        return "User added";
        //return users.toString();
    }

    public String setLike(String name) {
        projectRepository.setLike(name);
        return "Like sent";
    }

    public List<UserProject> getAllProjects() {
        return projectRepository.findAll();
    }

    //TODO исправить костыль
    public UserProject getLastProject() {
        List<UserProject> list = new ArrayList<>(projectRepository.findAll());
        return list.get(list.size() - 1);
    }
}
