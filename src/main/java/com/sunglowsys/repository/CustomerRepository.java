package com.sunglowsys.repository;

import com.sunglowsys.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where lower(CONCAT(c.firstName, ' ', c.email, ' ', c.mobile, ' ',  c.gender)) LIKE lower (concat('%' ,?1, '%'))")
    Page<Customer> search(String query, Pageable pageable);

}
