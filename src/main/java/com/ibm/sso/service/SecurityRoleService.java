package com.ibm.sso.service;

import com.aef3.data.api.GenericEntityDAO;
import com.ibm.sso.dao.SecurityRoleDao;
import com.ibm.sso.dto.SecurityPermissionDto;
import com.ibm.sso.dto.SecurityRoleDto;
import com.ibm.sso.model.SecurityPermission;
import com.ibm.sso.model.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityRoleService extends GeneralServiceImpl<SecurityRoleDto, SecurityRole, Long> {


    private final SecurityRoleDao securityRoleDao;

    @Autowired
    public SecurityRoleService(SecurityRoleDao securityRoleDao) {
        this.securityRoleDao = securityRoleDao;
    }

    @Override
    protected GenericEntityDAO getDAO() {
        return securityRoleDao;
    }

    @Override
    public SecurityRoleDto getDtoInstance() {
        return new SecurityRoleDto();
    }

    public List<SecurityPermissionDto> findUnAssignedPermissionsForRole(Long roleId) {

        List<SecurityPermission> permissionList = securityRoleDao.findUnAssinedPermissions(roleId);
        if(permissionList == null || permissionList.isEmpty())
            return null;
        return permissionList.stream().map(SecurityPermissionDto::toDto).collect(Collectors.toList());
    }
}
