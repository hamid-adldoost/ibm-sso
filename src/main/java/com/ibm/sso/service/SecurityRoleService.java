package com.ibm.sso.service;

import com.aef3.data.api.GenericEntityDAO;
import com.ibm.sso.dao.SecurityRoleDao;
import com.ibm.sso.dto.SecurityRoleDto;
import com.ibm.sso.model.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
