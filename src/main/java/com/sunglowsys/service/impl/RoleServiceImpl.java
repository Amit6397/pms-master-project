package com.sunglowsys.service.impl;

import com.sunglowsys.domain.Role;
import com.sunglowsys.repository.RoleRepository;
import com.sunglowsys.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        logger.debug("request save to role: {} ", role);
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        logger.debug("request to update role: {}", role);
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findOne(Long id) {
        logger.debug("request to findOne role: {}", id);
        return roleRepository.findById(id);
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        logger.debug("request to  getAll role: {}", pageable);
        return roleRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        logger.debug("request to delete role: {}", id);
        roleRepository.deleteById(id);
    }

    @Override
    public Page<Role> searchByRole(String query, Pageable pageable){
        logger.debug("request to search role: {}",query);
        return roleRepository.searchByRole(query, pageable);

    }
}
