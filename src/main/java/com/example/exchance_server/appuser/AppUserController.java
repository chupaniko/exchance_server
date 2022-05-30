package com.example.exchance_server.appuser;

import com.example.exchance_server.admin.Manual;
import com.example.exchance_server.admin.ManualService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AppUserController {
    private final AppUserService appUserService;
    private final ManualService manualService;

    @GetMapping("api/v1/getManual")
    public Manual getLatestManual() {
        return manualService.getLatestVersion();
    }

    @PostMapping("api/v1/getUserByToken")
    public AppUser getUserByToken(@RequestBody String token) {
        return appUserService.getUserByToken(token);
    }

    @PostMapping("api/v1/getFullUserByToken")
    public ResponseEntity<?> getFullUserByToken(@RequestBody String token) {
        AppUser user = appUserService.getUserByToken(token);
        if (user.getAppUserRole().equals(AppUserRole.ADMIN)) {
            return ResponseEntity.ok(user);
        } else if (user.getAppUserRole().equals(AppUserRole.ORGANIZATION)) {
            return ResponseEntity.ok(appUserService.getOrganizationByAppUser(user));
        } else {
            return ResponseEntity.ok(appUserService.getPersonByAppUser(user));
        }
    }
}
