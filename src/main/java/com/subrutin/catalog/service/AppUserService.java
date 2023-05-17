package com.subrutin.catalog.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AppUserService extends UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
