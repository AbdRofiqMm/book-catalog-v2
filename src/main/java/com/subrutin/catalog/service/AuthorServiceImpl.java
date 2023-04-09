package com.subrutin.catalog.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.subrutin.catalog.domain.Author;
import com.subrutin.catalog.dto.AuthorCreateRequestDto;
import com.subrutin.catalog.dto.AuthorResponseDto;
import com.subrutin.catalog.dto.AuthorUpdateRequestDto;
import com.subrutin.catalog.exception.BadRequestExcepton;
import com.subrutin.catalog.repository.AuthorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponseDto findAuthorById(long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new BadRequestExcepton("invalid.authorId"));
        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setName(author.getName());
        dto.setBirthDate(author.getBirhtDate().toEpochDay());
        return dto;
    }

    @Override
    public void createNewAuthor(List<AuthorCreateRequestDto> dtos) {
        List<Author> authors = dtos.stream().map((dto) -> {
            Author author = new Author();
            author.setName(dto.getAuthorName());
            author.setBirhtDate(LocalDate.ofEpochDay(dto.getBirthDate()));
            return author;
        }).collect(Collectors.toList());
        authorRepository.saveAll(authors);
    }

    @Override
    public void updateAuthor(Long authorId, AuthorUpdateRequestDto dto) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new BadRequestExcepton("invalid.authorId"));
        author.setName(dto.getAuthorName() == null ? author.getName() : dto.getAuthorName());
        author.setBirhtDate(
                dto.getBirthDate() == null ? author.getBirhtDate() : LocalDate.ofEpochDay(dto.getBirthDate()));
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long auhotrId) {
        authorRepository.deleteById(auhotrId);

        // Author author = authorRepository.findByIdAndDeletedFalse(auhotrId)
        // .orElseThrow(() -> new BadRequestExcepton("invlid.authorId"));
        // author.setDeleted(Boolean.TRUE);
        // authorRepository.save(author);
    }

}
