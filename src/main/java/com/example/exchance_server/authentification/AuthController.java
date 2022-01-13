package com.example.exchance_server.authentification;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/authentification")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public String auth(@RequestBody AuthRequest request) {
        return authService.auth(request);
    }
}
