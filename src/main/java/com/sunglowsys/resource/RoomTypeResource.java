package com.sunglowsys.resource;

import com.sunglowsys.domain.RoomType;
import com.sunglowsys.service.RoomTypeService;
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
public class RoomTypeResource {

    private final Logger log = LoggerFactory.getLogger (RoomTypeResource.class);

    private final RoomTypeService service;

    public RoomTypeResource(RoomTypeService service) {
        this.service = service;
    }

    @PostMapping("/room-types")
    public ResponseEntity<RoomType> createRoomType(@RequestBody RoomType roomType) {
        log.debug ("Rest request to create roomType: {}", roomType);
        RoomType result = service.save (roomType);
        return ResponseEntity
                .status (HttpStatus.CREATED)
                .body (result);
    }

    @PutMapping("/room-types")
    public ResponseEntity<RoomType> updateRoomType(@RequestBody RoomType roomType) {
        log.debug ("Rest request to update roomType: {}", roomType);
        if (roomType.getId () == null) {
            throw new RuntimeException ("id must not be null");
        }
        RoomType result = service.update (roomType);
        return ResponseEntity
                .status (HttpStatus.OK)
                .body (result);
    }

    @GetMapping("/room-types")
    public ResponseEntity<Page<RoomType>> getAllRoomType() {
        log.debug ("Rest request to getAll roomType: {}");
        Page<RoomType> result = service.findAll (PageRequest.of(1, 10));
        return ResponseEntity
                .status (HttpStatus.OK)
                .body (result);
    }

    @GetMapping("/room-types/{id}")
    public ResponseEntity<Optional<RoomType>> getOneRoomType(@PathVariable Long id) {
        log.debug ("Rest request to update roomType: {}", id);
        Optional<RoomType> result = service.findOne (id);
        return ResponseEntity
                .status (HttpStatus.OK)
                .body (result);

    }

    @DeleteMapping("/room-types/{id}")
    public ResponseEntity<Void> deleteRoomTypes(@PathVariable Long id) {
        log.debug ("Rest request to delete roomType roomType: {}", id);
        service.delete (id);
        return ResponseEntity
                .ok ()
                .build ();
    }

    @GetMapping("/room-types/search")
    public ResponseEntity<List<RoomType>> searchRoomTypes(@RequestParam("query") String query){
        log.debug ("Rest request to search roomTypes : {}",query);
        List<RoomType> result = service.searchRoomType (query);
        return ResponseEntity.status (HttpStatus.OK).body (result);
    }

}
