package com.subrutin.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.subrutin.catalog.domain.Author;
import com.subrutin.catalog.domain.Book;
import com.subrutin.catalog.domain.Category;
import com.subrutin.catalog.domain.Publisher;
import com.subrutin.catalog.dto.BookCreateRequestDto;
import com.subrutin.catalog.dto.BookDetailDto;
import com.subrutin.catalog.dto.BookUpdateRequestDto;
import com.subrutin.catalog.exception.BadRequestExcepton;
import com.subrutin.catalog.repository.BookRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final CategoryService categoryService;

    private final PublisherService publisherService;

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
