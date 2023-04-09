package com.subrutin.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.subrutin.catalog.domain.Book;
import com.subrutin.catalog.dto.BookCreateDto;
import com.subrutin.catalog.dto.BookDetailDto;
import com.subrutin.catalog.dto.BookUpdateRequestDto;
import com.subrutin.catalog.exception.BadRequestExcepton;
import com.subrutin.catalog.repository.BookRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDetailDto findBookDetailById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BadRequestExcepton("book_id.invalid"));
        BookDetailDto dto = new BookDetailDto();
        dto.setBookId(book.getId());
        dto.setBookTitle(book.getTitle());
        dto.setBookDescription(book.getDescription());
        return dto;
    }

    @Override
    public List<BookDetailDto> findBookListDetail() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map((b) -> {
            BookDetailDto dto = new BookDetailDto();
            dto.setBookId(b.getId());
            dto.setBookTitle(b.getTitle());
            dto.setBookDescription(b.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createNewBook(BookCreateDto dto) {
        Book book = new Book();
        book.setTitle(dto.getBookTitle());
        book.setDescription(dto.getBookDescription());
        bookRepository.save(book);
    }

    @Override
    public void updateBook(Long id, BookUpdateRequestDto dto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BadRequestExcepton("boo_id.invalid"));
        book.setTitle(dto.getBookTitle() == null ? book.getTitle() : dto.getBookTitle());
        book.setDescription(dto.getBookDescription() == null ? book.getDescription() : dto.getBookDescription());
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
