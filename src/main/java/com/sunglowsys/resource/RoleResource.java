package com.sunglowsys.resource;

import com.sunglowsys.domain.Role;
import com.sunglowsys.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoleResource {

    private final Logger logger = LoggerFactory.getLogger(RoleResource.class);

    private final RoleService roleService;

    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        logger.debug("REST request create the role: {}", role);
        Role result = roleService.save(role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/roles")
    public ResponseEntity<Void> updateRole(@RequestBody Role role) {
        logger.debug("REST request update the role: {}", role);
        if (role.getId() == null) {
            throw new RuntimeException("id must not be null");
        }
        roleService.save(role);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Optional<Role>> findOne(@PathVariable Long id) {
        logger.debug("REST request findOneById role: {}", id);
        Optional<Role> result = roleService.findOne(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/roles")
    public ResponseEntity<Page<Role>> findAllRole(Pageable pageable) {
        logger.debug("REST request getAll role: {}", pageable);
        Page<Role> result = roleService.findAll(PageRequest.of(0, 2));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable Long id) {
        logger.debug("REST request deleteById role: {}", id);
        roleService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/search/roles")
    public ResponseEntity<Page<Role>> search(@RequestParam("Query") String query){
        logger.debug ("REST request to search role : {}",query);
        Page<Role> roleList = roleService.searchByRole(query,PageRequest.of(0,5));
        return ResponseEntity.status (HttpStatus.OK).body (roleList);
    }


}
