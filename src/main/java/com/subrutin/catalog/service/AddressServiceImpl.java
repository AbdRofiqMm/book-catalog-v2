package com.subrutin.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.subrutin.catalog.domain.Address;
import com.subrutin.catalog.dto.AddressListResponseDto;
import com.subrutin.catalog.repository.AddressRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<AddressListResponseDto> constructDto(List<Address> addresses) {
        return addresses.stream().map((a) -> {
            AddressListResponseDto dto = new AddressListResponseDto();
            dto.setStreetName(a.getStreetName());
            dto.setCityName(a.getCityName());
            dto.setZipCode(a.getZipCode());
            return dto;
        }).collect(Collectors.toList());
    }

}
