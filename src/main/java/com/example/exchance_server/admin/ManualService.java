package com.example.exchance_server.admin;

import com.example.exchance_server.appuser.AppUserRole;
import com.example.exchance_server.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ManualService {
    private final ManualRepository manualRepository;
    private final AppUserService userService;

    public List<Manual> getUpdateLog() {
        return manualRepository.findAll();
    }

    public Manual getLatestVersion() {
        return manualRepository.findFirstByOrderByIdDesc().orElseThrow(
                () -> new IllegalStateException("Manual not found")
        );
    }

    public String deleteManual(String token) {
        if (!userService.getUserByToken(token).getAppUserRole().equals(AppUserRole.ADMIN))
            return "Access denied";
        manualRepository.delete(getLatestVersion());
        return "Last manual deleted";
    }

    public String publishManual(ManualRequest request) {
        if (!userService.getUserByToken(request.getToken()).getAppUserRole().equals(AppUserRole.ADMIN))
            return "Access denied";
        manualRepository.save(new Manual(
                request.getVersion(),
                request.getAboutProjConstructor(),
                request.getWhatsNew(),
                request.getAboutDev()));
        return "Manual saved";
    }
}
