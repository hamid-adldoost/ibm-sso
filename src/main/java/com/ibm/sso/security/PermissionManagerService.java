package com.ibm.sso.security;

import com.ibm.sso.dao.SecurityPermissionDao;
import com.ibm.sso.dao.SecurityRoleDao;
import com.ibm.sso.dto.SecurityRoleDto;
import com.ibm.sso.dto.SecurityUserDto;
import com.ibm.sso.model.SecurityPermission;
import com.ibm.sso.model.SecurityRole;
import com.ibm.sso.service.SecurityPermissionService;
import com.ibm.sso.service.SecurityUserService;
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
    private SecurityUserService securityUserService;
    private SecurityPermissionService securityPermissionService;

    @Autowired
    public PermissionManagerService(SecurityRoleDao roleDao, SecurityPermissionDao permissionDao, SecurityUserService securityUserService, SecurityPermissionService securityPermissionService){
        this.roleDao = roleDao;
        this.permissionDao = permissionDao;
        this.securityUserService = securityUserService;
        this.securityPermissionService = securityPermissionService;
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
    public SecurityUserDto createAdminUser() {

        SecurityUserDto userDto = new SecurityUserDto();
        userDto.setUsername("admin");
        SecurityUserDto existingAdmin = securityUserService.findSingleByExample(userDto);
        if(existingAdmin != null)
            return existingAdmin;

        SecurityRole securityRole = new SecurityRole();
        securityRole.setName("ADMIN_ROLE");

        if(roleDao.findSingleByExample(securityRole) == null) {
            securityRole.setTitle("ادمین");
            securityRole = roleDao.save(securityRole);
        }


        userDto.setPassword("Admin@2019");
        userDto.setCreationDate(new Date());
        userDto.setEmail("admin@itfreezone.com");
        userDto.setMobile("09143471899");
        SecurityRoleDto roleDto = SecurityRoleDto.toDto(securityRole);
        userDto.setRoleList(Collections.singletonList(roleDto));
        return securityUserService.validateAndSave(userDto);
    }

    @Transactional
    public SecurityUserDto createSystemUser() {

        SecurityUserDto userDto = new SecurityUserDto();
        userDto.setUsername("system");
        SecurityUserDto existingAdmin = securityUserService.findSingleByExample(userDto);
        if(existingAdmin != null)
            return existingAdmin;

        SecurityRole securityRole = new SecurityRole();
        securityRole.setName("ADMIN_ROLE");

        if(roleDao.findSingleByExample(securityRole) == null) {
            securityRole.setTitle("ادمین");
            securityRole = roleDao.save(securityRole);
        }


        userDto.setPassword("System@2019");
        userDto.setCreationDate(new Date());
        userDto.setEmail("system@itfreezone.com");
        userDto.setMobile("09143471899");
        SecurityRoleDto roleDto = SecurityRoleDto.toDto(securityRole);
        userDto.setRoleList(Collections.singletonList(roleDto));
        return securityUserService.validateAndSave(userDto);
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

//    @Transactional
//    public Collection initializePermissionsList(){
//        List<String> permissions = new ArrayList<>();
//        Field[] fields = AccessRoles.class.getDeclaredFields();
//        Arrays.stream(fields).forEach(f -> {
//            if(f.getName().startsWith("ROLE"))
//                permissions.add(f.getName());
//        });
//        permissions.stream().forEach(p -> {
//            securityPermissionService.ad
//        });
//    }
}
