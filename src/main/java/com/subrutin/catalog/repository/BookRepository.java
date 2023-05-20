package com.subrutin.catalog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.subrutin.catalog.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    public Optional<Book> findBySecureId(String id);

    @Query("SELECT DISTINCT b FROM Book b "
            + "INNER JOIN Publisher p ON p.id = b.publisher.id "
            + "JOIN b.authors ba "
            + "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%',:publisherName,'%'))"
            + " AND LOWER(b.title) LIKE LOWER(CONCAT('%',:bookTitle,'%'))"
            + " AND LOWER(ba.name) LIKE LOWER(CONCAT('%',:authorName,'%'))")
    public Page<Book> findBookList(String bookTitle, String publisherName, String authorName, Pageable pageable);
}
