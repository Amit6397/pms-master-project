package com.sunglowsys;

import com.sunglowsys.domain.Address;
import com.sunglowsys.resource.AddressResource;
import com.sunglowsys.service.AddressService;
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
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AddressResource.class)
class AddressResourceTest {

    private static String EXPECTED_RESPONSE = "{\"id\": 1," +
            "\"addressLine1\": \"Haricot\"," +
            "\"addressLine2\": \"Banglore\"," +
            "\"city\": \"Banglore\"," +
            "\"state\": \"Karnatka\"," +
            "\"country\": \"India\"," +
            "\"zipcode\": \"675723\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    private static Address createAddress() {
        Address address = new Address();
        address.setAddressLine1("Haricot");
        address.setAddressLine2("Banglore");
        address.setCity("Banglore");
        address.setState("Karnatka");
        address.setCountry("India");
        address.setZipcode("675723");
        return address;
    }

    @Test
    void createAddressTest() throws Exception {
        Address mockAddress = createAddress();
        mockAddress.setId(1L);
        when(addressService.findOne(anyLong())).thenReturn(Optional.of(mockAddress));

        MvcResult result = mockMvc.perform(post("/api/addresses")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockAddress)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

    }

    @Test
    void getAddressTest() throws Exception {
        Address mockAddress = createAddress();
        mockAddress.setId(1L);
        when(addressService.findOne(anyLong())).thenReturn(Optional.of(mockAddress));
        MvcResult result = mockMvc.perform(get("/api/addresses/1")
                        .accept(TestUtil.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(EXPECTED_RESPONSE, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);


    }

    @Test
    void getAllAddressResourceTest() throws Exception {
        Address address = createAddress();
        address.setId(1L);
        Address address1 = createAddress();
        address.setId(2L);

        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        addressList.add(address1);
        when(addressService.findAll(any())).thenReturn(new PageImpl<Address>(addressList, PageRequest.of(0, 2), 2));
        MvcResult mvcResult = mockMvc.perform(get("/api/addresses")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(addressList)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        // assertEquals(mvcResult.getResponse().getContentAsString(), JSONCompareMode.LENIENT);

    }

    @Test
    void updateAddressTest() throws Exception {
        Address address = createAddress();
        address.setId(1L);
        when(addressService.update(any())).thenReturn(address);
        MvcResult mvcResult = mockMvc.perform(put("/api/addresses")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(address)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        // assertEquals(mvcResult.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void deleteAddresstest() throws Exception {
        Address address = createAddress();
        address.setId(1L);
        doNothing().when(addressService).delete(anyLong());
        MvcResult mvcResult = mockMvc.perform(delete("/api/addresses/1")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(address)))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

}
