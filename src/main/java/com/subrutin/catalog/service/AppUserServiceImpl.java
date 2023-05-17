package com.subrutin.catalog.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.subrutin.catalog.exception.ResourceNotFoundException;
import com.subrutin.catalog.repository.AppUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("invalid.username"));
    }

}
