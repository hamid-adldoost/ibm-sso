package com.ibm.sso.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtAuthenticationToken implements Authentication {


    private SecurityWrapper securityWrapper;

    public JwtAuthenticationToken(SecurityWrapper securityWrapper) {
        this.securityWrapper = securityWrapper;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(securityWrapper.getPermissions() != null && !securityWrapper.getPermissions().isEmpty()) {
            securityWrapper.getPermissions().stream().forEach(p -> {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(p);
                authorities.add(authority);
            });
        }
        if(securityWrapper.getRoles() != null && !securityWrapper.getRoles().isEmpty()) {
            securityWrapper.getRoles().stream().forEach(r -> {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(r);
                authorities.add(authority);
            });
        }
        return authorities;
    }

    @Override
    public SecurityWrapper getCredentials() {
        return securityWrapper;
    }

    @Override
    public SecurityWrapper getDetails() {
        return securityWrapper;
    }

    @Override
    public Object getPrincipal() {
        return securityWrapper.getUsername();
    }

    @Override
    public boolean isAuthenticated() {

        return securityWrapper.isSecure();
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

        securityWrapper.setSecure(b);
    }

    @Override
    public String getName() {
        return securityWrapper.getUsername();
    }
}
