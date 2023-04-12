package com.subrutin.catalog.service;

import java.util.List;

import com.subrutin.catalog.domain.Category;
import com.subrutin.catalog.dto.CategoryCreateAndUpdateRequestDto;
import com.subrutin.catalog.dto.CategoryListResponseDto;
import com.subrutin.catalog.dto.ResultPageResponseDto;

public interface CategoryService {

    public void createAndUpdateCategory(CategoryCreateAndUpdateRequestDto dto);

    public ResultPageResponseDto<CategoryListResponseDto> findCategoryList(Integer pages, Integer limit, String sortBy,
            String direction, String categoryName);

    public List<Category> findCategories(List<String> categoryCodeList);

}
