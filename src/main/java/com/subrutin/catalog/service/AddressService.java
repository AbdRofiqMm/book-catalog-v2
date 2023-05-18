package com.subrutin.catalog.service;

import java.util.List;

import com.subrutin.catalog.domain.Address;
import com.subrutin.catalog.dto.AddressListResponseDto;

public interface AddressService {

    public List<AddressListResponseDto> constructDto(List<Address> addresses);
}
