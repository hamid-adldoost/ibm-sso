package com.ibm.sso.service;

import com.aef3.data.api.qbe.StringSearchType;
import com.ibm.sso.common.BusinessExceptionCode;
import com.ibm.sso.common.SecurityServiceException;
import com.ibm.sso.dto.SecurityPermissionDto;
import com.ibm.sso.dto.SecurityRoleDto;
import com.ibm.sso.dto.SecurityUserDto;
import com.ibm.sso.jwt.JWTUserDetails;
import com.ibm.sso.jwt.JWTUtil;
import com.ibm.sso.jwt.SecurityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityService {

    private final SecurityUserService userService;
    private final SecurityRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityService(SecurityUserService userService, SecurityRoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

//    public SecurityWrapper authenticate(String username, String password) {
//
//        //Authentication should be done here...
//        //Sample Authentication
//        if(username.equalsIgnoreCase("admin")
//                && password.equalsIgnoreCase("admin")) {
//            SecurityWrapper securityWrapper = new SecurityWrapper();
//            securityWrapper.setSecure(true);
//            securityWrapper.setUsername("admin");
//            securityWrapper.setPermissions(new ArrayList<>());
//            securityWrapper.setRoles(Collections.singletonList("user"));
//            return securityWrapper;
//        }
//        else
//            throw new SecurityServiceException(BusinessExceptionCode.ACCESS_DENIED.name());
//
//
//    }

    public SecurityWrapper authenticate(String username, String password) {

        SecurityUserDto user = new SecurityUserDto();
        user.setUsername(username);
        user = userService.findSingleByExample(user, StringSearchType.EXACT);
        if(user == null)
            throw new SecurityServiceException(BusinessExceptionCode.ACCESS_DENIED.name());
        if(user.getUsername().equalsIgnoreCase(username)) {
            if(!passwordEncoder.matches(user.getUsername() + password, user.getPassword())) {
                throw new SecurityServiceException(BusinessExceptionCode.ACCESS_DENIED.name());
            }
        } else {
            throw new SecurityServiceException(BusinessExceptionCode.ACCESS_DENIED.name());
        }

        List<String> roles = null;
        if(user.getRoleList() != null && !user.getRoleList().isEmpty())
            roles = user.getRoleList().stream().map(SecurityRoleDto::getName).collect(Collectors.toList());
        List<String> permissions = null;
        if(user.getPermissionList() != null && !user.getPermissionList().isEmpty())
            permissions = user.getPermissionList().stream().map(SecurityPermissionDto::getName).collect(Collectors.toList());

        return new SecurityWrapper(username, permissions, roles, null, true);
    }

    public SecurityWrapper authenticate(String jwt) {
        return JWTUtil.getSecurityWrapperFromToken(jwt);
    }

}
