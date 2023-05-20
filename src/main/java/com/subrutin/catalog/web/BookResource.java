package com.subrutin.catalog.web;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subrutin.catalog.dto.BookCreateRequestDto;
import com.subrutin.catalog.dto.BookDetailResponseDto;
import com.subrutin.catalog.dto.BookListResponseDto;
import com.subrutin.catalog.dto.ResultPageResponseDto;
import com.subrutin.catalog.service.BookService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BookResource {

    private final BookService bookService;

    @PostMapping("/v1/book")
    public ResponseEntity<Void> createBook(@RequestBody BookCreateRequestDto dto) {
        bookService.createNewBook(dto);
        return ResponseEntity.created(URI.create("/v1/book")).build();
    }

    // Get book list ->
    // 1. judul buku
    // 2. nama penerbit //table publisher
    // 3. nama penulis //table author

    @GetMapping("/v2/book")
    public ResponseEntity<ResultPageResponseDto<BookListResponseDto>> findBookList(
            @RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = true, defaultValue = "title") String sortBy,
            @RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
            @RequestParam(name = "bookTitle", required = false, defaultValue = "") String bookTitle,
            @RequestParam(name = "publisherName", required = false, defaultValue = "") String publisherName,
            @RequestParam(name = "authorName", required = false, defaultValue = "") String authorName) {
        return ResponseEntity.ok()
                .body(bookService.findBookList(page, limit, sortBy, direction, publisherName, bookTitle, authorName));
    }

    @GetMapping("/v1/book")
    public ResponseEntity<List<BookDetailResponseDto>> findBookList() {
        return ResponseEntity.ok().body(bookService.findBookListDetail());
    }

    @GetMapping("/v1/book/{id}")
    public ResponseEntity<BookDetailResponseDto> findBookDetail(@PathVariable String id) {
        BookDetailResponseDto result = bookService.findBookDetailById(id);
        return ResponseEntity.ok(result);
    }

}
