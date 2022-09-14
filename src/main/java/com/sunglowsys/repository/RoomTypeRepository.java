package com.sunglowsys.repository;

import com.sunglowsys.domain.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    @Query("SELECT r FROM RoomType r WHERE " +
            "r.name LIKE CONCAT('%',:query, '%')")
    List<RoomType> searchRoomType(String query);
}
