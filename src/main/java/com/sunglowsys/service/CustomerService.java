package com.sunglowsys.service;


import com.sunglowsys.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerService {

    Customer save(Customer customer);

    Customer update(Customer customer);

    Optional<Customer> findOne(Long id);

    Page<Customer> findAll(Pageable pageable);

    void delete(Long id);

    Page<Customer> search(String query , Pageable pageable);
}
