package com.subrutin.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.subrutin.catalog.domain.Category;
import com.subrutin.catalog.dto.CategoryCreateAndUpdateRequestDto;
import com.subrutin.catalog.dto.CategoryListResponseDto;
import com.subrutin.catalog.dto.ResultPageResponseDto;
import com.subrutin.catalog.exception.BadRequestExcepton;
import com.subrutin.catalog.repository.CategoryRepository;
import com.subrutin.catalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void createAndUpdateCategory(CategoryCreateAndUpdateRequestDto dto) {
        Category category = categoryRepository.findByCode(dto.getCode().toLowerCase()).orElse(new Category());
        if (category.getCode() == null) {
            category.setCode(dto.getCode().toLowerCase());
        }
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        categoryRepository.save(category);

    }

    @Override
    public ResultPageResponseDto<CategoryListResponseDto> findCategoryList(Integer pages, Integer limit, String sortBy,
            String direction, String categoryName) {
        categoryName = StringUtils.isBlank(categoryName) ? "%" : categoryName + "%";
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(pages, limit, sort);
        Page<Category> pageResult = categoryRepository.findByNameLikeIgnoreCase(categoryName, pageable);
        List<CategoryListResponseDto> dtos = pageResult.stream().map((p) -> {
            CategoryListResponseDto dto = new CategoryListResponseDto();
            dto.setCode(p.getCode());
            dto.setName(p.getName());
            dto.setDescription(p.getDescription());
            return dto;
        }).collect(Collectors.toList());
        return PaginationUtil.createResultPageDto(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());

    }

    @Override
    public List<Category> findCategories(List<String> categoryCodeList) {
        List<Category> categories = categoryRepository.findByCodeIn(categoryCodeList);
        if (categories.isEmpty())
            throw new BadRequestExcepton("Category cant emptey");
        return categories;
    }

}
