package com.example.exchance_server.projectconstructor;

import com.example.exchance_server.appuser.AppUserService;
import com.example.exchance_server.userproject.UserProject;
import com.example.exchance_server.userproject.UserProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ConstructorController {
    private final UserProjectService projectService;
    private final AppUserService userService;

    @RequestMapping(value = "api/v1/projectconstructor/publishProject", method = RequestMethod.POST)
    public String publishProject(@RequestBody ConstructorRequest request) {
        return projectService.publishProject(request);
    }

    @PostMapping("api/v1/projectconstructor/addUserToProject")
    public String addUserToProject(@RequestBody AddUsersToProjectRequest request) {
        return projectService.addUserToProject(request);
    }

    @RequestMapping(value = "api/v1/projectconstructor/getLastProject", method = RequestMethod.POST)
    public List<UserProject> getLastProject(@RequestBody ConstructorRequest request) {
        UserProject project = projectService.getLastProject();
        List<UserProject> list = projectService.getAllProjects();
        return list;
        //return "рандомный проект";
        //responseentity возвращет
        /*return project != null
                ? new ResponseEntity<>(project, HttpStatus.OK).toString()
                : new ResponseEntity<>(HttpStatus.NOT_FOUND).toString();*/
    }

    /*@RequestMapping(value = "api/v1/projectconstructor/getAllProjects", method = RequestMethod.GET)*/
    @GetMapping("api/v1/projectconstructor/getAllProjects")
    public ResponseEntity<List<UserProject>> getAllProjects() {
        List<UserProject> list = projectService.getAllProjects();
        return ResponseEntity.ok(list);
    }

    @PostMapping("api/v1/projectconstructor/subscribeToProject")
    public String subscribeToProject(@RequestParam String token, @RequestParam String name) {
        return userService.subscribeToProject(token, name);
    }

    @PostMapping("api/v1/projectconstructor/getProjectSubscriptions")
    public ResponseEntity<List<UserProject>> getProjectSubscriptions(@RequestParam String token) {
        return ResponseEntity.ok(userService.getProjectSubscriptions(token));
    }

    @PostMapping("api/v1/projectconstructor/setLikeToProject")
    public String setLikeToProject(@RequestParam String name) {
        return projectService.setLike(name);
    }
}
