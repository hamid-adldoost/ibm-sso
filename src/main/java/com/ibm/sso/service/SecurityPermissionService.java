package com.ibm.sso.service;

import com.aef3.data.api.GenericEntityDAO;
import com.ibm.sso.dao.SecurityPermissionDao;
import com.ibm.sso.dto.SecurityPermissionDto;
import com.ibm.sso.model.SecurityPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityPermissionService extends GeneralServiceImpl<SecurityPermissionDto, SecurityPermission, Long> {


    private final SecurityPermissionDao securityPermissionDao;

    @Autowired
    public SecurityPermissionService(SecurityPermissionDao securityPermissionDao) {
        this.securityPermissionDao = securityPermissionDao;
    }

    @Override
    protected GenericEntityDAO getDAO() {
        return securityPermissionDao;
    }

    @Override
    public SecurityPermissionDto getDtoInstance() {
        return new SecurityPermissionDto();
    }
}
