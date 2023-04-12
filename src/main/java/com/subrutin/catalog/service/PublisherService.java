package com.subrutin.catalog.service;

import com.subrutin.catalog.domain.Publisher;
import com.subrutin.catalog.dto.PublisherCreateDto;
import com.subrutin.catalog.dto.PublisherListResponseDto;
import com.subrutin.catalog.dto.PublisherUpdateDto;
import com.subrutin.catalog.dto.ResultPageResponseDto;

public interface PublisherService {

    public void createPublisher(PublisherCreateDto dto);

    public void updatePublisher(String publisherId, PublisherUpdateDto dto);

    public ResultPageResponseDto<PublisherListResponseDto> findPublisherList(Integer pages, Integer limit,
            String sortBy, String direction, String publisherName);

    public Publisher findPublisher(String publisherId);
}
