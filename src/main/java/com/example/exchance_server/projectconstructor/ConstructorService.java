package com.example.exchance_server.projectconstructor;

import com.example.exchance_server.userproject.UserProject;
import com.example.exchance_server.userproject.UserProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConstructorService {
    private final UserProjectService projectService;

    public String publishProject(ConstructorRequest request) {
        return projectService.publishProject(request.getToken(),
                request.getProjectName(),
                request.getProjectDescription(),
                request.getProjectField());
    }

    public String getAllProjects() {
        return projectService.getAllProjects().toString();
    }

    public UserProject getLastProject() {
        return projectService.getLastProject();
    }

}
