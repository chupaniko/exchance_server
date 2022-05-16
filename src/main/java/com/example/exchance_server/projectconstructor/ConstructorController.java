package com.example.exchance_server.projectconstructor;

import com.example.exchance_server.userproject.UserProject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ConstructorController {
    private final ConstructorService constructorService;

    @RequestMapping(value = "api/v1/projectconstructor/publishproject", method = RequestMethod.POST)
    public String publishProject(@RequestBody ConstructorRequest request) {
        return constructorService.publishProject(request);
    }

    @RequestMapping(value = "api/v1/projectconstructor/getLastProject", method = RequestMethod.POST)
    public String getLastProject(@RequestBody ConstructorRequest request) {
        UserProject project = constructorService.getLastProject();
        //return "рандомный проект";
        //responseentity возвращет
        return project != null
                ? new ResponseEntity<>(project, HttpStatus.OK).toString()
                : new ResponseEntity<>(HttpStatus.NOT_FOUND).toString();
    }
}
