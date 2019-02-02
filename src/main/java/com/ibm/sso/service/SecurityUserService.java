package com.ibm.sso.service;

import com.aef3.data.api.GenericEntityDAO;
import com.ibm.sso.common.BusinessExceptionCode;
import com.ibm.sso.dao.SecurityUserDao;
import com.ibm.sso.dto.SecurityPermissionDto;
import com.ibm.sso.dto.SecurityRoleDto;
import com.ibm.sso.dto.SecurityUserDto;
import com.ibm.sso.model.SecurityPermission;
import com.ibm.sso.model.SecurityRole;
import com.ibm.sso.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityUserService extends GeneralServiceImpl<SecurityUserDto, SecurityUser, Long> {


    @Value("${password.regex}")
    private String passwordRegex;

    @Value("${username.regex}")
    private String usernameRegex;

    @Value("${email.regex}")
    private String emailRegex;

    private final SecurityUserDao securityUserDao;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityUserService(SecurityUserDao securityUserDao, PasswordEncoder passwordEncoder) {
        this.securityUserDao = securityUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected GenericEntityDAO getDAO() {
        return securityUserDao;
    }

    @Override
    public SecurityUserDto getDtoInstance() {
        return new SecurityUserDto();
    }

    @Transactional
    public SecurityUserDto validateAndSave(SecurityUserDto securityUser) {

        validateUserData(securityUser);

        if(securityUser.getId() != null) {
            SecurityUserDto oldVersion = findByPrimaryKey(securityUser.getId());
            if(oldVersion == null)
                throw new RuntimeException(BusinessExceptionCode.USER_NOT_FOUND.name());
            securityUser.setPassword(oldVersion.getPassword());
        } else {
            securityUser.setPassword(passwordEncoder.encode(securityUser.getUsername()
                    + securityUser.getPassword()));
        }

        SecurityUserDto existingUser = findUserByUsernameOrEmail(securityUser.getUsername(), securityUser.getEmail());

        if(securityUser.getId() == null) {

            if(existingUser != null) {
                throwSuitableRepeationError(securityUser, existingUser);
            }
        } else {
            if(existingUser != null){
                if(!existingUser.getId().equals(securityUser.getId())) {
                    throwSuitableRepeationError(securityUser, existingUser);
                }
            }
        }

        //initialization
        if(securityUser.getId() != null) {
            securityUser.setLastLogin(existingUser.getLastLogin());
            securityUser.setFailureCount(existingUser.getFailureCount());
            securityUser.setStatus(existingUser.getStatus());
            securityUser.setSuspensionCode(existingUser.getSuspensionCode());
            securityUser.setPermissionList(existingUser.getPermissionList());
            securityUser.setRoleList(existingUser.getRoleList());
            securityUser.setCreationDate(existingUser.getCreationDate());
        } else {
            securityUser.setCreationDate(new Date());
            securityUser.setFailureCount(0);
        }
        return save(securityUser);
    }

    private void throwSuitableRepeationError(SecurityUserDto securityUser, SecurityUserDto existingUser) {

        if (existingUser.getEmail().equalsIgnoreCase(securityUser.getEmail()))
            throw new RuntimeException(BusinessExceptionCode.EMAIL_IS_TAKEN_ALREADY.name());
        if (existingUser.getUsername().equalsIgnoreCase(securityUser.getUsername()))
            throw new RuntimeException(BusinessExceptionCode.USERNAME_IS_TAKEN_ALREADY.name());
    }

    private void validateUserData(SecurityUserDto securityUser) {

        if(securityUser.getUsername() == null || securityUser.getUsername().trim().isEmpty()) {
            throw new RuntimeException(BusinessExceptionCode.USERNAME_IS_REQUIRED.name());
        }
        if(securityUser.getPassword() == null || securityUser.getPassword().trim().isEmpty()) {
            throw new RuntimeException(BusinessExceptionCode.PASSWORD_IS_REQUIRED.name());
        }
        if(!securityUser.getUsername().matches(usernameRegex))
            throw new RuntimeException(BusinessExceptionCode.USERNAME_IS_INVALID.name());
        if(!securityUser.getPassword().matches(passwordRegex))
            throw new RuntimeException(BusinessExceptionCode.PASSWORD_IS_INVALID.name());
        if(!securityUser.getEmail().matches(emailRegex))
            throw new RuntimeException(BusinessExceptionCode.EMAIL_IS_INVALID.name());

    }

    public boolean checkUserIsExisting(SecurityUserDto securityUserDto) {
        SecurityUser securityUser = securityUserDao.findUserByUsernameOrEmail(
                securityUserDto.getUsername(),
                securityUserDto.getEmail());

        if(securityUser != null)
            return true;
        return false;

    }

    public SecurityUserDto findUserByUsernameOrEmail(String username, String email) {
        SecurityUser securityUser = securityUserDao.findUserByUsernameOrEmail(
                username,
                email);

        if(securityUser != null)
            return SecurityUserDto.toDto(securityUser);
        return null;
    }

    public List<SecurityPermissionDto> findUnAssignedPermissionsForUser(Long userId) {

        List<SecurityPermission> permissionList = securityUserDao.findUnAssignedPermissionsForUser(userId);
        if(permissionList == null)
            return null;
        return permissionList.stream().map(SecurityPermissionDto::toDto)
                .collect(Collectors.toList());
    }

    public List<SecurityRoleDto> findUnAssignedRolesForUser(Long userId) {

        List<SecurityRole> roleList = securityUserDao.findUnAssignedRolesForUser(userId);
        if(roleList == null)
            return null;
        return roleList.stream().map(SecurityRoleDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SecurityUserDto addPermissionsToUser(List<Long> permissionIdList, Long userId) {

        return SecurityUserDto.toDto(securityUserDao.addPermissionsToUser(permissionIdList, userId));
    }

    @Transactional
    public SecurityUserDto addRolesToUser(List<Long> roleIdList, Long userId) {

        return SecurityUserDto.toDto(securityUserDao.addRolesToUser(roleIdList, userId));
    }

    @Transactional
    public SecurityUserDto removePermissionFromUser(Long permissionId, Long userId) {

        return SecurityUserDto.toDto(securityUserDao.removePermissionFromUser(permissionId, userId));
        //        if(permissionId == null)
//            return  null;
//        if(userId == null)
//            throw new RuntimeException(BusinessExceptionCode.USER_NOT_FOUND.name());
//        SecurityUser user = securityUserDao.findByPrimaryKey(userId);
//        if(user == null)
//            throw new RuntimeException(BusinessExceptionCode.USER_NOT_FOUND.name());
//
//        final List<SecurityPermission> permission = new ArrayList<>();
//        if(user.getPermissionList() == null || user.getPermissionList().isEmpty())
//            throw new RuntimeException(BusinessExceptionCode.BAD_INPUT.name());
//        user.getPermissionList().stream().forEach(p -> {
//            if(p.getId().equals(permissionId))
//                permission.add(p);
//        });
//        if(permission.isEmpty())
//            throw new RuntimeException(BusinessExceptionCode.BAD_INPUT.name());
//        user.getPermissionList().remove(permission.get(0));
//        return SecurityUserDto.toDto(securityUserDao.save(user));
    }

    @Transactional
    public SecurityUserDto removeRoleFromUser(Long roleId, Long userId) {

        return SecurityUserDto.toDto(securityUserDao.removeRoleFromUser(roleId, userId));
        //        if(roleId == null)
//            return  null;
//        if(userId == null)
//            throw new RuntimeException(BusinessExceptionCode.USER_NOT_FOUND.name());
//        SecurityUser user = securityUserDao.findByPrimaryKey(userId);
//        if(user == null)
//            throw new RuntimeException(BusinessExceptionCode.USER_NOT_FOUND.name());
//
//        SecurityRole role =
//        user.getRoleList().remove(role);
//        return SecurityUserDto.toDto(securityUserDao.save(user));
    }
}
