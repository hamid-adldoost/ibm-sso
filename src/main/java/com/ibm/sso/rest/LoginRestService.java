package com.ibm.sso.rest;

import com.ibm.sso.common.BusinessExceptionCode;
import com.ibm.sso.common.SecurityServiceException;
import com.ibm.sso.jwt.SecurityWrapper;
import com.ibm.sso.security.PermissionManagerService;
import com.ibm.sso.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/auth")
public class LoginRestService {

    private final SecurityService securityService;
    private final PermissionManagerService permissionManagerService;

    @Autowired
    public LoginRestService(SecurityService securityService, PermissionManagerService permissionManagerService) {
        this.securityService = securityService;
        this.permissionManagerService = permissionManagerService;
    }

    @PostMapping(path = "/login")
    public SecurityWrapper login(@RequestParam(name = "username") String username,
                                 @RequestParam(name = "password") String password,
                                 HttpServletResponse response) {

//        SecurityWrapper securityWrapper = securityService.authenticate(loginDto.getUsername(), loginDto.getPassword());
        SecurityWrapper securityWrapper = securityService.authenticate(username, password);
        response.setHeader(HttpHeaders.AUTHORIZATION, securityWrapper.getFreshToken());
        return securityWrapper;
    }

    @GetMapping(path = "/perms")
    public SecurityWrapper findPermissionsForUser(@RequestHeader(name = "jwt") String jwt) {
        return securityService.authenticate(jwt);
    }

    @GetMapping(path = "/{roleName}")
    public Set<String> findPermissionsForRoleName(
            @RequestHeader(name = "jwt") String jwt,
            @PathVariable(name = "roleName")String roleName) {
        SecurityWrapper securityWrapper = securityService.authenticate(jwt);
        if(!securityWrapper.getRoles().contains(roleName))
            throw new SecurityServiceException(BusinessExceptionCode.ACCESS_DENIED.name());
        Map<String, Set<String>> globalPermsMap = permissionManagerService.findPermissionsOfRoles();
        return globalPermsMap.get(roleName);
    }

    @GetMapping(path = "/update-admin")
    public void updateAdmin() {
        permissionManagerService.updateAdminRole();
    }
}
