package com.sunglowsys.resource;

import com.sunglowsys.domain.Customer;
import com.sunglowsys.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerResource {

    private final Logger logger = LoggerFactory.getLogger(CustomerResource.class);

    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        logger.debug("REST request to create customer :{}",customer);
        Customer result = customerService.save(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/customers")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        logger.debug("REST request to update customer :{}",customer);
        if (customer.getId()==null) {
            throw new RuntimeException("id must not be null");
        }
        Customer result = customerService.update(customer);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Optional<Customer>> getCustomer(@PathVariable Long id) {
        logger.debug("REST request to get customer :{}",id);
        Optional<Customer> result = customerService.findOne(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/customers")
    public ResponseEntity<Page<Customer>> getAllCustomers() {
        logger.debug("REST request to get customers : ");
       Page<Customer> result = customerService.findAll(PageRequest.of(0,5));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        logger.debug("REST request to delete customer :{}" ,id);
        customerService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/search/customers")
    public ResponseEntity<Page<Customer>> searchCustomers(@RequestParam("query") String query) {
        logger.debug("REST request to search customers :{}", query);
        Page<Customer> result =customerService.search(query,PageRequest.of(0,5));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
