package com.sunglowsys.repository;

import com.sunglowsys.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    @Query("SELECT c FROM Role c WHERE " + "c.role LIKE CONCAT('%',:query, '%')")
    Page<Role> searchByRole(String query, Pageable pageable);
}
