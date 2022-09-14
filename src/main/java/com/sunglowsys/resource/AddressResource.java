package com.sunglowsys.resource;

import com.sunglowsys.domain.Address;
import com.sunglowsys.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AddressResource{
    private final Logger logger = LoggerFactory.getLogger(AddressResource.class);
    private final AddressService addressService;

    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/addresses")
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        logger.debug("REST request to create address :{}",address);
        Address result = addressService.save(address);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result) ;
    }

    @PutMapping("/addresses")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
        logger.debug("REST request to update address :{}",address);
        if(address.getId()==null){
            throw new RuntimeException("id must not be null");
        }
        Address result = addressService.update(address);
        return ResponseEntity
                .status(HttpStatus.OK).
                body(result);
    }

    @GetMapping("/addresses")
    public ResponseEntity<Page<Address>> getAll(){
       logger.debug("REST request to get address");
       Page<Address> result  = addressService.findAll(PageRequest.of(0,20));
       return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getaddress(@PathVariable Long id) {
        logger.debug("REST request to get address :{}",id);
        Address result = addressService.findOne(id).get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
  }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        logger.debug("REST request to delete address :{}" ,id);
        addressService.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/search/address")
    public ResponseEntity<List<Address>> searchAddresses(@RequestParam("query") String query) {
        logger.debug("REST request to search address :{}",query);
        List<Address> addressList = addressService.search(query);
        return ResponseEntity.status(HttpStatus.OK).body(addressList);
    }
}
