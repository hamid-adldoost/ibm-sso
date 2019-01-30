package com.ibm.sso.rest;

import com.ibm.sso.jwt.SecurityWrapper;
import com.ibm.sso.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class LoginRestService {

    private final SecurityService securityService;

    @Autowired
    public LoginRestService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping(path = "/login")
    public SecurityWrapper login(@RequestParam(name = "username") String username,
                                 @RequestParam(name = "password") String password) {

        return securityService.authenticate(username, password);
    }

    @GetMapping(path = "/perms/{jwt}")
    public SecurityWrapper findPermissionsForUser(@PathVariable(name = "jwt") String jwt) {
        return securityService.authenticate(jwt);
    }
}
