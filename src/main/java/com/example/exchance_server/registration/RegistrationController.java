package com.example.exchance_server.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("api/v1/registerPerson")
    public String registerPerson(@RequestBody PersonRegRequest request) {
        //signup for Person
        return registrationService.registerPerson(request);
    }

    @PostMapping("api/v1/registerOrganization")
    public String registerOrganization(@RequestBody OrgRegRequest request){
        return registrationService.registerOrganization(request);
    }

    /*@PostMapping("api/v1/registration")
    public String register(@RequestBody RegistrationRequest request) {
        //отправка письма с верификацией пользователю
        return registrationService.register(request);
    }*/

    @GetMapping(path = "api/v1/registerPerson/confirm")
    public String confirmPerson(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping(path = "api/v1/registerOrganization/confirm")
    public String confirmOrganization(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
