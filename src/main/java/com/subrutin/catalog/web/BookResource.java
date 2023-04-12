package com.subrutin.catalog.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subrutin.catalog.dto.BookCreateRequestDto;
import com.subrutin.catalog.service.BookService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/book")
public class BookResource {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody BookCreateRequestDto dto) {
        bookService.createNewBook(dto);
        return ResponseEntity.created(URI.create("/v1/book")).build();
    }

}
