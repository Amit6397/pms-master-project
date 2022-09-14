package com.sunglowsys.repository;

import com.sunglowsys.domain.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT p FROM Hotel p WHERE lower(CONCAT(p.name,' ', p.code,' ', p.type,' ', p.email)) LIKE lower (concat('%',?1,'%'))")
    List<Hotel> searchHotel(String keyword);
}
