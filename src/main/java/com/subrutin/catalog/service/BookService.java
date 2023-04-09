package com.subrutin.catalog.service;

import java.util.List;

import com.subrutin.catalog.dto.BookCreateDto;
import com.subrutin.catalog.dto.BookDetailDto;
import com.subrutin.catalog.dto.BookUpdateRequestDto;

public interface BookService {

    public BookDetailDto findBookDetailById(Long id);

    public List<BookDetailDto> findBookListDetail();

    public void createNewBook(BookCreateDto dto);

    public void updateBook(Long id, BookUpdateRequestDto dto);

    public void deleteBook(Long id);
}
