package com.subrutin.catalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subrutin.catalog.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    public List<Author> findByNameLike(String name);

    public Optional<Author> findByIdAndDeletedFalse(Long id);

}