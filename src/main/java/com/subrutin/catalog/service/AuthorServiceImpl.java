package com.subrutin.catalog.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.subrutin.catalog.domain.Address;
import com.subrutin.catalog.domain.Author;
import com.subrutin.catalog.dto.AuthorCreateRequestDto;
import com.subrutin.catalog.dto.AuthorResponseDto;
import com.subrutin.catalog.dto.AuthorUpdateRequestDto;
import com.subrutin.catalog.exception.BadRequestException;
import com.subrutin.catalog.repository.AuthorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AddressService addressService;

    @Override
    public AuthorResponseDto findAuthorById(String id) {
        Author author = authorRepository.findBySecureId(id)
                .orElseThrow(() -> new BadRequestException("invalid.authorId"));
        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setName(author.getName());
        dto.setBirthDate(author.getBirhtDate().toEpochDay());
        dto.setAddresses(addressService.constructDto(author.getAddresses()));
        return dto;
    }

    @Override
    public void createNewAuthor(List<AuthorCreateRequestDto> dtos) {
        List<Author> authors = dtos.stream().map((dto) -> {
            Author author = new Author();
            author.setName(dto.getAuthorName());
            author.setBirhtDate(LocalDate.ofEpochDay(dto.getBirthDate()));
            List<Address> addresses = dto.getAddresses().stream().map(a -> {
                Address address = new Address();
                address.setStreetName(a.getStreetName());
                address.setCityName(a.getCityName());
                address.setZipCode(a.getZipCode());
                address.setAuthor(author);
                return address;
            }).collect(Collectors.toList());
            author.setAddresses(addresses);
            return author;
        }).collect(Collectors.toList());
        authorRepository.saveAll(authors);
    }

    @Override
    public void updateAuthor(String authorId, AuthorUpdateRequestDto dto) {
        Author author = authorRepository.findBySecureId(authorId)
                .orElseThrow(() -> new BadRequestException("invalid.authorId"));
        Map<Long, Address> addressMap = author.getAddresses().stream().map(a -> a)
                .collect(Collectors.toMap(Address::getId, Function.identity()));
        List<Address> addresses = dto.getAddresses().stream().map(a -> {
            Address address = addressMap.get(a.getAddressId());
            address.setStreetName(a.getStreetName());
            address.setCityName(a.getCityName());
            address.setZipCode(a.getZipCode());
            return address;
        }).collect(Collectors.toList());
        author.setAddresses(addresses);
        author.setName(dto.getAuthorName() == null ? author.getName() : dto.getAuthorName());
        author.setBirhtDate(
                dto.getBirthDate() == null ? author.getBirhtDate() : LocalDate.ofEpochDay(dto.getBirthDate()));
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(String auhotrId) {
        Author author = authorRepository.findBySecureId(auhotrId)
                .orElseThrow(() -> new BadRequestException("invlid.authorId"));
        authorRepository.delete(author);

        // authorRepository.deleteById(auhotrId);

        // Author author = authorRepository.findByIdAndDeletedFalse(auhotrId)
        // .orElseThrow(() -> new BadRequestExcepton("invlid.authorId"));
        // author.setDeleted(Boolean.TRUE);
        // authorRepository.save(author);
    }

    @Override
    public List<Author> findAuthors(List<String> authorIdList) {
        List<Author> authors = authorRepository.findBySecureIdIn(authorIdList);
        if (authors.isEmpty())
            throw new BadRequestException("Author cant empty");
        return authors;
    }

    @Override
    public List<AuthorResponseDto> constructDto(List<Author> author) {
        return author.stream().map((a) -> {
            AuthorResponseDto dto = new AuthorResponseDto();
            dto.setName(a.getName());
            dto.setBirthDate(a.getBirhtDate().toEpochDay());
            return dto;
        }).collect(Collectors.toList());
    }

}
