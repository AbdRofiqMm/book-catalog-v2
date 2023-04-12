package com.subrutin.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.subrutin.catalog.domain.Publisher;
import com.subrutin.catalog.dto.PublisherCreateDto;
import com.subrutin.catalog.dto.PublisherListResponseDto;
import com.subrutin.catalog.dto.PublisherResponseDto;
import com.subrutin.catalog.dto.PublisherUpdateDto;
import com.subrutin.catalog.dto.ResultPageResponseDto;
import com.subrutin.catalog.exception.BadRequestException;
import com.subrutin.catalog.repository.PublisherRepository;
import com.subrutin.catalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public void createPublisher(PublisherCreateDto dto) {
        Publisher publisher = new Publisher();
        publisher.setName(dto.getPublisherName());
        publisher.setCompanyName(dto.getCompanyName());
        publisher.setAddress(dto.getAddress());
        publisherRepository.save(publisher);
    }

    @Override
    public void updatePublisher(String publisherId, PublisherUpdateDto dto) {
        Publisher publisher = publisherRepository.findBySecureId(publisherId)
                .orElseThrow(() -> new BadRequestException("invalid.publisherId"));
        publisher.setName(dto.getPublisherName() == null || dto.getPublisherName().isBlank() ? publisher.getName()
                : dto.getPublisherName());
        publisher.setCompanyName(
                dto.getCompanyName() == null || dto.getCompanyName().isBlank() ? publisher.getCompanyName()
                        : dto.getCompanyName());
        publisher.setAddress(dto.getAddress());
        publisherRepository.save(publisher);
    }

    @Override
    public ResultPageResponseDto<PublisherListResponseDto> findPublisherList(Integer pages, Integer limit,
            String sortBy, String direction, String publisherName) {
        publisherName = StringUtils.isBlank(publisherName) ? "%" : publisherName + "%";
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(pages, limit, sort);
        publisherRepository.findByNameLikeIgnoreCase(publisherName, pageable);
        Page<Publisher> pageResult = publisherRepository.findByNameLikeIgnoreCase(publisherName, pageable);
        List<PublisherListResponseDto> dtos = pageResult.stream().map((p) -> {
            PublisherListResponseDto dto = new PublisherListResponseDto();
            dto.setPublisherId(p.getSecureId());
            dto.setPublisherName(p.getName());
            dto.setCompanyName(p.getCompanyName());
            return dto;
        }).collect(Collectors.toList());
        return PaginationUtil.createResultPageDto(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
    }

    @Override
    public Publisher findPublisher(String publisherId) {
        return publisherRepository.findBySecureId(publisherId)
                .orElseThrow(() -> new BadRequestException("invlid.publisherId"));
    }

    @Override
    public PublisherResponseDto constructDto(Publisher publisher) {
        PublisherResponseDto dto = new PublisherResponseDto();
        dto.setPublisherId(publisher.getSecureId());
        dto.setPublisherName(publisher.getName());
        return dto;
    }

}
