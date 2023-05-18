package com.subrutin.catalog.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;

@Data
@Entity
@Table(name = "author")
// @DynamicUpdate
@SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ? ")
@Where(clause = "deleted=false")
public class Author extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    @SequenceGenerator(name = "author_generator", sequenceName = "author_id_seq")
    private Long id;

    @Column(name = "author_name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "birht_date", nullable = false)
    private LocalDate birhtDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Address> addresses;

}
