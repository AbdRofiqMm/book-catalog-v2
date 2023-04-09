package com.subrutin.catalog.service;

import java.util.List;

import com.subrutin.catalog.dto.AuthorCreateRequestDto;
import com.subrutin.catalog.dto.AuthorResponseDto;
import com.subrutin.catalog.dto.AuthorUpdateRequestDto;

public interface AuthorService {

    public AuthorResponseDto findAuthorById(long id);

    public void createNewAuthor(List<AuthorCreateRequestDto> dto);

    public void updateAuthor(Long authorId, AuthorUpdateRequestDto dto);

    public void deleteAuthor(Long auhotrId);
}
