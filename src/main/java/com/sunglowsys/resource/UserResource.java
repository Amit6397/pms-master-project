package com.sunglowsys.resource;

import com.sunglowsys.domain.User;
import com.sunglowsys.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User result = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User result = userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<User>> getAllUser() {
        Page<User> result = userService.findAll(PageRequest.of(0, 20));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> getOneUser(@PathVariable Long id) {
        Optional<User> result = userService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("keyword") String keyword) {
        List<User> userList = userService.searchUser(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }


}
