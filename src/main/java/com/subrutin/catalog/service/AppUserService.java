package com.subrutin.catalog.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.subrutin.catalog.dto.UserDetailResponseDto;

public interface AppUserService extends UserDetailsService {

    public UserDetailResponseDto findUserDetail();

}
