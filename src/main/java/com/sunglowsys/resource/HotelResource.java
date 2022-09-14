package com.sunglowsys.resource;

import com.sunglowsys.domain.Hotel;
import com.sunglowsys.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HotelResource {

    private final Logger logger = LoggerFactory.getLogger(HotelResource.class);

    private final HotelService hotelService;

    public HotelResource(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/hotels")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        logger.debug("REST request to create the hotel : {}", hotel);
        Hotel result = hotelService.save(hotel);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/hotels")
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) {
        logger.debug("REST request to update the hotel :{}", hotel);
        Hotel result = hotelService.update(hotel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/hotels")
    public ResponseEntity<Page<Hotel>> getAllHotel() {
        logger.debug("REST request to getAll hotels :");
        Page<Hotel> result = hotelService.findAll(PageRequest.of(0, 20));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<Optional<Hotel>> getOneHotel(@PathVariable Long id) {
        logger.debug("REST request to getOne hotel : {}", id);
        Optional<Hotel> result = hotelService.findOne(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        logger.debug("REST request to delete hotel : {}", id);
        hotelService.delete(id);
        return ResponseEntity
                .ok().build();
    }

    @GetMapping("/hotels/search")
    public ResponseEntity<List<Hotel>> searchHotel(@RequestParam("keyword") String keyword) {
        logger.debug("REST request to searchHotel hotel :{}",keyword);
        List<Hotel> hotelList = hotelService.searchHotel(keyword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelList);
    }
}
