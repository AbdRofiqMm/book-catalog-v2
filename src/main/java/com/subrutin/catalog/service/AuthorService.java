package com.subrutin.catalog.service;

import java.util.List;

import com.subrutin.catalog.domain.Author;
import com.subrutin.catalog.dto.AuthorCreateRequestDto;
import com.subrutin.catalog.dto.AuthorResponseDto;
import com.subrutin.catalog.dto.AuthorUpdateRequestDto;

public interface AuthorService {

    public AuthorResponseDto findAuthorById(String id);

    public void createNewAuthor(List<AuthorCreateRequestDto> dto);

    public void updateAuthor(String authorId, AuthorUpdateRequestDto dto);

    public void deleteAuthor(String auhotrId);

    public List<Author> findAuthors(List<String> authorIdList);
}
