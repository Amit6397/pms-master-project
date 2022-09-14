package com.sunglowsys;

import com.sunglowsys.domain.Hotel;
import com.sunglowsys.resource.HotelResource;
import com.sunglowsys.service.HotelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = HotelResource.class)
class HotelResourceTest {


    private static String EXPECTED_RESPONSE = "{\"id\": 1," +
            " \"name\": \"SSS-Hotel\"," +
            " \"code\": \"SS-123\"," +
            " \"type\": \"5-star\"," +
            " \"email\": \"ss231@gmail.com\"," +
            " \"mobile\": \"7888677876\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    private static Hotel createHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("SSS-Hotel");
        hotel.setCode("SS-123");
        hotel.setType("5-star");
        hotel.setEmail("ss231@gmail.com");
        hotel.setMobile("7888677876");
        return hotel;
    }

    @Test
    void createHotelTest() throws Exception {
        Hotel mockHotel = createHotel();
        mockHotel.setId(1L);
        when(hotelService.save(any())).thenReturn((mockHotel));

        //create the Hotel
        MvcResult result = mockMvc.perform(post("/api/hotels")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockHotel)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(EXPECTED_RESPONSE, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void getHotelResource() throws Exception {
        Hotel mockHotel = createHotel();
        mockHotel.setId(1L);
        when(hotelService.findOne(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockHotel));

        // get hotel by id
        MvcResult result = mockMvc.perform(get("/api/hotels/1")
                        .accept(TestUtil.APPLICATION_JSON))
                .andReturn();
        System.out.println(result.getResponse());

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(EXPECTED_RESPONSE, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void getAllHotelResourceTest() throws Exception {
        Hotel hotel = createHotel();
        hotel.setId(1L);
        Hotel hotel1 = createHotel();
        hotel.setId(2L);

        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(hotel);
        hotelList.add(hotel1);
        when(hotelService.findAll(any())).thenReturn(new PageImpl<>(hotelList, PageRequest.of(0, 2), 2));
        MvcResult mvcResult = mockMvc.perform(get("/api/hotels")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(hotelList)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        // assertEquals(mvcResult.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void updateHotelResourceTest() throws Exception {
        Hotel hotel = createHotel();
        hotel.setId(1L);
        when(hotelService.update(any())).thenReturn(hotel);
        MvcResult mvcResult = mockMvc.perform(put("/api/hotels")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(hotel)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(EXPECTED_RESPONSE, mvcResult.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void deleteHotelResourceTest() throws Exception {
        Hotel hotel = createHotel();
        hotel.setId(1L);
        doNothing().when(hotelService).delete(anyLong());
        MvcResult mvcResult = mockMvc.perform(delete("/api/hotels/1")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(hotel)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

}
