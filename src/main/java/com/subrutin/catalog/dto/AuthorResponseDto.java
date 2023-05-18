package com.subrutin.catalog.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.subrutin.catalog.domain.Address;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthorResponseDto {

    private String name;

    private Long birthDate;

    private List<Address> addresses;

    public void setAddresses(List<AddressListResponseDto> constructDto) {
    }
}
