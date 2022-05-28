package com.example.exchance_server.admin;

import com.example.exchance_server.appuser.AppUserService;
import com.example.exchance_server.userproject.UserProjectService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
//@PreAuthorize("hasAuthority('ADMIN')")
// TODO: проверка во всех методах на role ADMIN
public class AdminController {
    private final ManualService manualService;
    private final AppUserService userService;
    private final UserProjectService projectService;

    @PostMapping("api/v1/admin/publishManual")
    public String publishManual(@RequestBody ManualRequest request) {
        return manualService.publishManual(request);
    }

    @PostMapping("api/v1/admin/deleteLastManual")
    public String deleteLastManual(@RequestParam String token) {
        return manualService.deleteManual(token);
    }

    //public String deleteProjectByName(@RequestParam String token, @RequestParam String name)
    //удаление проектов
    //disable, lock юзера
    //получение всех юзеров
    //получение юзера по имени
    //получение проектов юзера
}
