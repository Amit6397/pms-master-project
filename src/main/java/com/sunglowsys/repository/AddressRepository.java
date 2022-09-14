package com.sunglowsys.repository;

import com.sunglowsys.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select c from Address c where lower(CONCAT(c.addressLine1, ' ', c.addressLine2, ' ', c.city, ' ',  c.state,' ', c.country,' ', c.zipcode)) LIKE lower (concat('%',?1,'%'))")
    List<Address> search(String query);
}
