package com.sunglowsys.service.impl;

import com.sunglowsys.domain.Hotel;
import com.sunglowsys.repository.HotelRepository;
import com.sunglowsys.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel save(Hotel hotel) {
        logger.debug("Request to save Hotel Booking: {}",hotel);
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Hotel hotel) {
        logger.debug("Request to update Hotel Booking: {}",hotel);
        return hotelRepository.save(hotel);
    }

    @Override
    public Page<Hotel> findAll(Pageable pageable) {
        logger.debug("Request to getAll hotels : {}",pageable);
        return hotelRepository.findAll(pageable);
    }

    @Override
    public Optional<Hotel> findOne(Long id) {
        logger.debug("Request to getOne hotel : {}",id);
        return hotelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Hotel : {}", id);
        hotelRepository.deleteById(id);
    }

    @Override
    public List<Hotel> searchHotel(String keyword) {
        logger.debug("Request to search Hotel : {}", keyword);
        return hotelRepository.searchHotel(keyword);
    }


}
