package com.sunglowsys.service.impl;

import com.sunglowsys.domain.Customer;
import com.sunglowsys.repository.CustomerRepository;
import com.sunglowsys.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        logger.debug("Request to save customer :{}",customer);
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        logger.debug("Request to update  customer :{}",customer);
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findOne(Long id) {
        logger.debug("Request to findOne customer :{}",id);
        return customerRepository.findById(id);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        logger.debug("Request to findAll customers :{}",pageable);
        return customerRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request to delete customer :{}" ,id);
        customerRepository.deleteById(id);
    }

    @Override
    public Page<Customer> search(String query, Pageable pageable) {
        logger.debug("Request to search customer :{}" , query);
      return customerRepository.search(query,pageable);
    }
}
