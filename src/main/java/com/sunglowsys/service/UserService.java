package com.sunglowsys.service;

import com.sunglowsys.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    User update(User user);
    Page<User> findAll(Pageable pageable);
    Optional<User> findOne(Long id);
    void delete(Long id);
    List<User> searchUser(String keyword);
}
