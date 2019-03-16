package com.ibm.sso.rest;

import com.ibm.sso.jwt.SecurityWrapper;
import com.ibm.sso.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
                                 @RequestParam(name = "password") String password,
                                 HttpServletResponse response) {


        SecurityWrapper securityWrapper = securityService.authenticate(username, password);
        response.setHeader(HttpHeaders.AUTHORIZATION, securityWrapper.getFreshToken());
        return securityWrapper;
    }

    @GetMapping(path = "/perms")
    public SecurityWrapper findPermissionsForUser(@RequestHeader(name = "jwt") String jwt) {
        return securityService.authenticate(jwt);
    }
}
