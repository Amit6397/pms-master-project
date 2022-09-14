package com.sunglowsys.service;

import com.sunglowsys.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleService {

    Role save(Role role);

    Role update(Role role);

    Optional<Role> findOne(Long id);

    Page<Role> findAll(Pageable pageable);

    void delete(Long id);

    Page<Role> searchByRole(String query, Pageable pageable);
}
