package com.ibm.sso.service;

import com.aef3.data.api.GenericEntityDAO;
import com.ibm.sso.dao.SecurityUserDao;
import com.ibm.sso.dto.SecurityUserDto;
import com.ibm.sso.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserService extends GeneralServiceImpl<SecurityUserDto, SecurityUser, Long> {


    private final SecurityUserDao securityUserDao;

    @Autowired
    public SecurityUserService(SecurityUserDao securityUserDao) {
        this.securityUserDao = securityUserDao;
    }

    @Override
    protected GenericEntityDAO getDAO() {
        return securityUserDao;
    }

    @Override
    public SecurityUserDto getDtoInstance() {
        return new SecurityUserDto();
    }
}
