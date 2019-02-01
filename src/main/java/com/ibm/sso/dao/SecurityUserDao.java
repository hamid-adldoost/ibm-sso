package com.ibm.sso.dao;

import com.aef3.data.impl.AbstractDAOImpl;
import com.ibm.sso.common.BusinessExceptionCode;
import com.ibm.sso.model.SecurityPermission;
import com.ibm.sso.model.SecurityRole;
import com.ibm.sso.model.SecurityUser;
import org.springframework.stereotype.Repository;

import java.util.List;


/* Generated By AEF Generator ( Powered by Dr.Adldoost :D ) */

@Repository
public class SecurityUserDao extends AbstractDAOImpl<SecurityUser, Long> {

    public SecurityUserDao() {
        super(SecurityUser.class);
    }

    public SecurityUser findUserByUsernameOrEmail(String username, String email) {

        List<SecurityUser> securityUserList = getEntityManager().createQuery("select su from SecurityUser su where " +
                "su.username = :username or su.email = :email", SecurityUser.class)
                .setParameter("username", username)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList();
        if(securityUserList != null && !securityUserList.isEmpty()) {
            return securityUserList.get(0);
        }
        return null;
    }

    public List<SecurityPermission> findUnAssignedPermissionsForUser(Long userId) {

        SecurityUser securityUser = findByPrimaryKey(userId);

        if(securityUser == null)
            throw new RuntimeException(BusinessExceptionCode.USER_NOT_FOUND.name());

        if(securityUser.getPermissionList() != null && !securityUser.getPermissionList().isEmpty())
            return getEntityManager().createQuery("select p from SecurityPermission p where " +
                "p not in :permissionList", SecurityPermission.class)
                .setParameter("permissionList", securityUser.getPermissionList())
                .getResultList();
        else
            return getEntityManager().createQuery("select p from SecurityPermission p", SecurityPermission.class)
            .getResultList();
    }

    public List<SecurityRole> findUnAssignedRolesForUser(Long userId) {

        SecurityUser securityUser = findByPrimaryKey(userId);

        if(securityUser == null)
            throw new RuntimeException(BusinessExceptionCode.USER_NOT_FOUND.name());

        if(securityUser.getRoleList() != null && !securityUser.getRoleList().isEmpty())
            return getEntityManager().createQuery("select r from SecurityRole r where " +
                    "r not in :roleList", SecurityRole.class)
                    .setParameter("roleList", securityUser.getRoleList())
                    .getResultList();
        else
            return getEntityManager().createQuery("select r from SecurityRole r", SecurityRole.class)
                    .getResultList();
    }
}