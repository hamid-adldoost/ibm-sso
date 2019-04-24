package com.ibm.sso.security;

import com.ibm.sso.dao.SecurityPermissionDao;
import com.ibm.sso.dao.SecurityRoleDao;
import com.ibm.sso.model.SecurityPermission;
import com.ibm.sso.model.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionManagerService {

    private SecurityRoleDao roleDao;
    private SecurityPermissionDao permissionDao;

    @Autowired
    public PermissionManagerService(SecurityRoleDao roleDao, SecurityPermissionDao permissionDao){
        this.roleDao = roleDao;
        this.permissionDao = permissionDao;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Transactional
    public Map<String, Set<String>> findPermissionsOfRoles() {

        List<SecurityRole> roles = roleDao.findByExample(new SecurityRole());
        if(roles == null || roles.isEmpty())
            return null;

        Map<String, Set<String>> permissonMap = new HashMap<>();

        roles.stream().forEach(r -> {
            if(r.getPermissionList() == null || r.getPermissionList().isEmpty())
                permissonMap.put(r.getName(), new HashSet<>());
            else
                permissonMap.put(r.getName(), r.getPermissionList().stream().map(SecurityPermission::getName).collect(Collectors.toSet()));
        });
        return permissonMap;
    }

    @Transactional
    public void updateAdminRole() {
        Set<String> adminRoles = findPermissionsOfRoles().get("ADMIN_ROLE");
        List<SecurityPermission> notAssignedRoles = new ArrayList<>();
        List<SecurityPermission> permissionList = permissionDao.findByExample(new SecurityPermission());
        if(permissionList == null || permissionList.isEmpty())
            return;
        permissionList.stream().forEach(p -> {
            if(!adminRoles.contains(p.getName()))
                notAssignedRoles.add(p);
        });
        SecurityRole securityRole = new SecurityRole();
        securityRole.setName("ADMIN_ROLE");
        securityRole = roleDao.findSingleByExample(securityRole);
        securityRole.getPermissionList().addAll(notAssignedRoles);
        roleDao.save(securityRole);
    }
}
