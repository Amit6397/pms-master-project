package com.sunglowsys.repository;

import com.sunglowsys.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    @Query
    ("SELECT u FROM User u WHERE lower(CONCAT(u.login,' ', u.password,' ', u.firstName,' ', u.lastName,' ', u.activated)) LIKE lower (concat('%',?1,'%'))")
    List<User> searchUser(String keyword);
}
