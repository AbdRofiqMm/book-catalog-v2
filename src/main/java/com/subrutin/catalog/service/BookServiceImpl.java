package com.subrutin.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.subrutin.catalog.domain.Author;
import com.subrutin.catalog.domain.Book;
import com.subrutin.catalog.domain.Category;
import com.subrutin.catalog.domain.Publisher;
import com.subrutin.catalog.dto.BookCreateRequestDto;
import com.subrutin.catalog.dto.BookDetailResponseDto;
import com.subrutin.catalog.dto.BookListResponseDto;
import com.subrutin.catalog.dto.BookUpdateRequestDto;
import com.subrutin.catalog.dto.ResultPageResponseDto;
import com.subrutin.catalog.exception.BadRequestException;
import com.subrutin.catalog.repository.BookRepository;
import com.subrutin.catalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final CategoryService categoryService;

    private final PublisherService publisherService;

    @Override
    public BookDetailResponseDto findBookDetailById(String id) {
        Book book = bookRepository.findBySecureId(id).orElseThrow(() -> new BadRequestException("book_id.invalid"));
        BookDetailResponseDto dto = new BookDetailResponseDto();
        dto.setBookId(book.getSecureId());
        dto.setCategories(categoryService.constructDto(book.getCategories()));
        dto.setAuthors(authorService.constructDto(book.getAuthors()));
        dto.setPublisher(publisherService.constructDto(book.getPublisher()));
        dto.setBookTitle(book.getTitle());
        dto.setBookDescription(book.getDescription());
        return dto;
    }

    @Override
    public List<BookDetailResponseDto> findBookListDetail() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map((b) -> {
            BookDetailResponseDto dto = new BookDetailResponseDto();
            dto.setBookId(b.getSecureId());
            dto.setBookTitle(b.getTitle());
            dto.setBookDescription(b.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createNewBook(BookCreateRequestDto dto) {
        List<Author> authors = authorService.findAuthors(dto.getAuthorIdList());
        List<Category> categories = categoryService.findCategories(dto.getCategoryList());
        Publisher publisher = publisherService.findPublisher(dto.getPublisherId());
        Book book = new Book();
        book.setTitle(dto.getBookTitle());
        book.setAuthors(authors);
        book.setCategories(categories);
        book.setPublisher(publisher);
        book.setDescription(dto.getDescription());
        bookRepository.save(book);
    }

    @Override
    public void updateBook(Long id, BookUpdateRequestDto dto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BadRequestException("boo_id.invalid"));
        book.setTitle(dto.getBookTitle() == null ? book.getTitle() : dto.getBookTitle());
        book.setDescription(dto.getBookDescription() == null ? book.getDescription() : dto.getBookDescription());
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public ResultPageResponseDto<BookListResponseDto> findBookList(Integer page, Integer limit, String sortBy,
            String direction, String publisherName, String bookTitle, String authorName) {

        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Book> pageResult = bookRepository.findBookList(bookTitle, publisherName, authorName, pageable);
        List<BookListResponseDto> dtos = pageResult.stream().map(b -> {
            BookListResponseDto dto = new BookListResponseDto();
            dto.setAuthorNames(b.getAuthors().stream().map(a -> a.getName()).collect(Collectors.toList()));
            dto.setCategoryCodes(b.getCategories().stream().map(c -> c.getCode()).collect(Collectors.toList()));
            dto.setTitle(b.getTitle());
            dto.setPublisherName(b.getPublisher().getName());
            dto.setDescription(b.getDescription());
            dto.setId(b.getSecureId());
            return dto;
        }).collect(Collectors.toList());
        return PaginationUtil.createResultPageDto(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
    }

}
