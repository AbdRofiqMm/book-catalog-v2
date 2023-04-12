package com.subrutin.catalog.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;

@Data
@Entity
@Table(name = "publisher")
@SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ? ")
@Where(clause = "deleted=false")
public class Publisher extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisher_generator")
    @SequenceGenerator(name = "publisher_generator", sequenceName = "publisher_id_seq")
    private Long id;

    @Column(name = "publisher_name", nullable = false)
    private String name;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    private String address;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

}
