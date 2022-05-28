package com.example.exchance_server.appuser;

import com.example.exchance_server.admin.Manual;
import com.example.exchance_server.admin.ManualService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
