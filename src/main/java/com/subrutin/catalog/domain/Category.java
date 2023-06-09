package com.subrutin.catalog.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;

@Data
@Entity
@Table(name = "category")
@SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ? ")
@Where(clause = "deleted=false")
public class Category extends AbstractBaseEntity {

    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToMany(mappedBy = "categories")
    private List<Book> books;

}
