package com.ibm.sso.dto;

import com.aef3.data.api.DomainDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.sso.model.SecurityPermission;
import java.util.Date;


/* Generated By AEF Generator ( Powered by Dr.Adldoost :D ) */

public class SecurityPermissionDto implements DomainDto<SecurityPermission, SecurityPermissionDto> {


    private String name;
    private Long id;
    private String title;
 

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }



    public static SecurityPermissionDto toDto(SecurityPermission securityPermission) {

        if(securityPermission == null)
            return null; 
        SecurityPermissionDto dto = new SecurityPermissionDto();
        dto.setName(securityPermission.getName());
        dto.setId(securityPermission.getId());
        dto.setTitle(securityPermission.getTitle());
        return dto;
  }


    public static SecurityPermission toEntity(SecurityPermissionDto dto) {

        if(dto == null)
            return null; 
        SecurityPermission securityPermission = new SecurityPermission();
        securityPermission.setName(dto.getName());
        securityPermission.setId(dto.getId());
        securityPermission.setTitle(dto.getTitle());
        return securityPermission;
  }
    @Override
    public SecurityPermission toEntity() {
        return SecurityPermissionDto.toEntity(this);
    }

    @JsonIgnore
    @Override
    public SecurityPermissionDto getInstance(SecurityPermission securityPermission) {
        return SecurityPermissionDto.toDto(securityPermission);
    }

    @JsonIgnore
    @Override
    public SecurityPermissionDto getInstance() {
        return new SecurityPermissionDto();
    }
}