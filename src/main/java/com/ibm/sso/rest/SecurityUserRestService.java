package com.ibm.sso.rest;

import com.aef3.data.SortUtil;
import com.aef3.data.api.qbe.SortObject;
import com.aef3.data.api.qbe.StringSearchType;
import com.ibm.sso.dto.SecurityPermissionDto;
import com.ibm.sso.dto.SecurityRoleDto;
import com.ibm.sso.dto.SecurityUserDto;
import com.ibm.sso.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ibm.sso.service.PagedResult;import java.util.Date;

import org.springframework.security.access.annotation.Secured;
import com.ibm.sso.security.AccessRoles;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;


/* Generated By AEF Generator ( Powered by Dr.Adldoost :D ) */

@RestController
@RequestMapping("/security-user")
public class SecurityUserRestService {

    private final SecurityUserService securityUserService;

    @Autowired
    public SecurityUserRestService(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @Secured(AccessRoles.ROLE_FIND_SECURITY_USER)
    @GetMapping("/{id}")
    public SecurityUserDto findById(@PathVariable(name = "id")Long id) {
        return securityUserService.findByPrimaryKey(id);
    }

    @Secured(AccessRoles.ROLE_SEARCH_SECURITY_USER)
    @GetMapping("/search")
    public PagedResult search(
                                      @RequestParam(value = "lastLogin", required = false) Date lastLogin,
                                      @RequestParam(value = "password", required = false) String password,
                                      @RequestParam(value = "suspensionCode", required = false) String suspensionCode,
                                      @RequestParam(value = "mobile", required = false) String mobile,
                                      @RequestParam(value = "id", required = false) Long id,
                                      @RequestParam(value = "failureCount", required = false) Integer failureCount,
                                      @RequestParam(value = "email", required = false) String email,
                                      @RequestParam(value = "username", required = false) String username,
                                      @RequestParam(value = "status", required = false) Integer status,
                                      @RequestParam(value = "firstIndex", required = false) Integer firstIndex,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "sortField", required = false) String sortField,
                                      @RequestParam(value = "sortOrder", required = false) String sortOrder) {

            SortObject sortObject = SortUtil.generateSortObject(sortField, sortOrder);
            List<SortObject> sortObjectList = null;
            if(sortObject != null)
               sortObjectList = Collections.singletonList(sortObject);

            if(firstIndex == null)
               firstIndex = 0;
            if(pageSize == null)
               pageSize = Integer.MAX_VALUE;
            SecurityUserDto securityUser = new SecurityUserDto();
            securityUser.setLastLogin(lastLogin); 
            securityUser.setPassword(password); 
            securityUser.setSuspensionCode(suspensionCode); 
            securityUser.setMobile(mobile); 
            securityUser.setId(id); 
            securityUser.setFailureCount(failureCount); 
            securityUser.setEmail(email); 
            securityUser.setUsername(username); 
            securityUser.setStatus(status); 

            return securityUserService.findPagedByExample(securityUser,
                   sortObjectList,
                   firstIndex,
                   pageSize,
                   StringSearchType.EXPAND_BOTH_SIDES,
                   null,
                   null
                   );
    }


    @Secured(AccessRoles.ROLE_SAVE_SECURITY_USER)
    @PostMapping(path = "/save")
    public SecurityUserDto save(@RequestBody SecurityUserDto securityUser) {
        return securityUserService.validateAndSave(securityUser);
    }


    @Secured(AccessRoles.ROLE_REMOVE_SECURITY_USER)
    @DeleteMapping(path = "/remove/{id}")
    public void remove(@PathVariable(name = "id")Long id) {
        securityUserService.remove(id);
    }

    @GetMapping(path = "/unassigned-permissions/{userId}")
    public List<SecurityPermissionDto> findUnAssignedPermissionsForUser(@PathVariable(name = "userId") Long userId) {
        return securityUserService.findUnAssignedPermissionsForUser(userId);
    }

    @GetMapping(path = "/unassigned-roles/{userId}")
    public List<SecurityRoleDto> findUnAssignedRolesForUser(@PathVariable(name = "userId") Long userId) {
        return securityUserService.findUnAssignedRolesForUser(userId);
    }

    @PostMapping(path = "/add-permission/{userId}")
    public SecurityUserDto addPermissionToUser(@RequestBody List<Long> permissionIdList,@PathVariable(name = "userId") Long userId) {
        return securityUserService.addPermissionsToUser(permissionIdList, userId);
    }

    @PostMapping(path = "/add-role/{userId}")
    public SecurityUserDto addRoleToUser(@RequestBody List<Long> roleIdList,@PathVariable(name = "userId") Long userId) {
        return securityUserService.addRolesToUser(roleIdList, userId);
    }

    @DeleteMapping(path = "/remove-permission/{userId}/{id}")
    public SecurityUserDto removePermissionFromUser(@PathVariable(name = "id") Long permissionId,@PathVariable(name = "userId") Long userId) {
        return securityUserService.removePermissionFromUser(permissionId, userId);
    }

    @DeleteMapping(path = "/remove-role/{userId}/{id}")
    public SecurityUserDto removeRoleFromUser(@PathVariable(name = "id") Long roleId,@PathVariable(name = "userId") Long userId) {
        return securityUserService.removeRoleFromUser(roleId, userId);
    }
}