package com.subrutin.catalog.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryCreateAndUpdateRequestDto {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String description;

}
