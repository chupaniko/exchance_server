package com.example.exchance_server.userproject;

import com.example.exchance_server.appuser.AppUser;
import com.example.exchance_server.appuser.AppUserRepository;
import com.example.exchance_server.appuser.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserProjectService {
    private final static String PROJECT_NOT_FOUND_MSG =
            "project with name %s not found";
    private final UserProjectRepository projectRepository;
    private final AppUserRepository userRepository;

    public String publishProject(String token, String name, String description, String field) {
        // TODO if project exists...
        AppUser user = userRepository.findByToken(token).orElse(new AppUser("", "", AppUserRole.USER));
        UserProject project = new UserProject(user, name, description, field);
        boolean projectExits = projectRepository
                .findUserProjectByProjectName(project.getProjectName())
                .isPresent();
        if (projectExits) {
            // TODO check of attributes are the same
            throw new IllegalStateException("project already exists");
        }
        projectRepository.save(project);
        return token;
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
