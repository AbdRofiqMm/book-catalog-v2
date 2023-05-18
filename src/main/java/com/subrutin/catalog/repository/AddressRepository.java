package com.subrutin.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subrutin.catalog.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
