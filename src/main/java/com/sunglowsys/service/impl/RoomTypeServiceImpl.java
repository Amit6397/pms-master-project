package com.sunglowsys.service.impl;

import com.sunglowsys.domain.RoomType;
import com.sunglowsys.repository.RoomTypeRepository;
import com.sunglowsys.service.RoomTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    private final Logger log = LoggerFactory.getLogger (RoomTypeServiceImpl.class);

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public RoomType save(RoomType roomType) {
        log.debug ("Request to save roomType : {}", roomType);
        return roomTypeRepository.save (roomType);
    }

    @Override
    public RoomType update(RoomType roomType) {
        log.debug ("Request to update roomType : {}", roomType);
        return roomTypeRepository.save (roomType);
    }

    @Override
    public Page<RoomType> findAll(Pageable pageable) {
        log.debug ("Request to findAll roomType : {}", pageable);
        return roomTypeRepository.findAll (pageable);
    }

    @Override
    public Optional<RoomType> findOne(Long id) {
        log.debug ("Request to findOne roomType : {}", id);
        return roomTypeRepository.findById (id);
    }

    @Override
    public void delete(Long id) {
        log.debug ("Request to delete roomType : {}", id);
        roomTypeRepository.deleteById (id);
    }

    @Override
    public List<RoomType> searchRoomType(String query) {
        List<RoomType> roomTypeList = roomTypeRepository.searchRoomType (query);
        return roomTypeList;
    }

}
