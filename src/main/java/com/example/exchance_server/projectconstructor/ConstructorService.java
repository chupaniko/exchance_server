package com.example.exchance_server.projectconstructor;

import com.example.exchance_server.userproject.UserProject;
import com.example.exchance_server.userproject.UserProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<UserProject> getAllProjects() {
        return projectService.getAllProjects();
    }

    public UserProject getLastProject() {
        return projectService.getLastProject();
    }

}
