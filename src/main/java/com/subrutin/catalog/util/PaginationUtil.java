package com.subrutin.catalog.util;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.subrutin.catalog.dto.ResultPageResponseDto;

public class PaginationUtil {

    public static <T> ResultPageResponseDto<T> createResultPageDto(List<T> dtos, Long totalElements, Integer pages) {
        ResultPageResponseDto result = new ResultPageResponseDto<>();
        result.setPages(pages);
        result.setElements(totalElements);
        result.setResult(dtos);
        return result;
    }

    public static Sort.Direction getSortBy(String sortBy) {
        if (sortBy.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }
}