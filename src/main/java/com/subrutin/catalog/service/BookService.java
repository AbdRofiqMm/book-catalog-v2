package com.subrutin.catalog.service;

import java.util.List;

import com.subrutin.catalog.dto.BookCreateRequestDto;
import com.subrutin.catalog.dto.BookDetailResponseDto;
import com.subrutin.catalog.dto.BookListResponseDto;
import com.subrutin.catalog.dto.BookUpdateRequestDto;
import com.subrutin.catalog.dto.ResultPageResponseDto;

public interface BookService {

    public BookDetailResponseDto findBookDetailById(String id);

    public List<BookDetailResponseDto> findBookListDetail();

    public void createNewBook(BookCreateRequestDto dto);

    public void updateBook(Long id, BookUpdateRequestDto dto);

    public void deleteBook(Long id);

    public ResultPageResponseDto<BookListResponseDto> findBookList(Integer page, Integer limit, String sortBy,
            String direction, String bookTitle, String publisherName, String authorName);
}
