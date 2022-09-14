package com.sunglowsys;

import com.sunglowsys.domain.RoomType;
import com.sunglowsys.resource.RoomTypeResource;
import com.sunglowsys.service.RoomTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = RoomTypeResource.class)
class RoomTypeTest {

    private static String EXPECTED_RESPONCE = "{\n" +
            "\t\"id\": 1,\n" +
            "\t\"name\": \"AC\"\n" +
            "}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomTypeService roomTypeService;


    private static RoomType createRoomType() {
        RoomType roomType = new RoomType();
        roomType.setName("AC");
        return roomType;
    }

    @Test
    void createRoomTypeTest() throws Exception {
        RoomType roomType = createRoomType();
        roomType.setId(1L);
        when(roomTypeService.save(any())).thenReturn(roomType);
        MvcResult result = mockMvc.perform(post("/api/room-types")

                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(roomType)))
                .andReturn();


        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(EXPECTED_RESPONCE, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void getRoomTypesResourceTest() throws Exception {
        RoomType roomType = createRoomType();
        roomType.setId(1L);
        when(roomTypeService.findOne(anyLong())).thenReturn(Optional.of(roomType));
        MvcResult mvcResult = mockMvc.perform(get("/api/room-types/1")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(roomType)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(EXPECTED_RESPONCE, mvcResult.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void getAllRoomTypeResourceTest() throws Exception {

        RoomType roomType = createRoomType();
        roomType.setId(1L);
        RoomType roomType1 = createRoomType();
        roomType1.setId(2L);

        List<RoomType> roomTypeList = new ArrayList<>();
        roomTypeList.add(roomType);
        roomTypeList.add(roomType1);

        when(roomTypeService.findAll(any())).thenReturn(new PageImpl<>(roomTypeList, PageRequest.of(0, 2), 2));

        MvcResult mvcResult = mockMvc.perform(get("/api/room-types")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(roomTypeList)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        // assertEquals ( mvcResult.getResponse ().getContentAsString (), JSONCompareMode.LENIENT);


    }

    @Test
    void updateRoomType() throws Exception {
        RoomType roomType = createRoomType();
        roomType.setId(1L);
        when(roomTypeService.update(any())).thenReturn(roomType);
        MvcResult mvcResult = mockMvc.perform(put("/api/room-types")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(roomType)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(EXPECTED_RESPONCE, mvcResult.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void deleteRoomTypeTest() throws Exception {
        RoomType roomType = createRoomType();
        roomType.setId(1L);
        doNothing().when(roomTypeService).delete(anyLong());

        MvcResult mvcResult = mockMvc.perform(delete("/api/room-types/1")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(roomType)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

}


















