package com.subrutin.catalog.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookDetailResponseDto {

    private String bookId;

    private List<AuthorResponseDto> authors;

    private List<CategoryListResponseDto> categories;

    private PublisherResponseDto publisher;

    private String bookTitle;

    private String bookDescription;

}
