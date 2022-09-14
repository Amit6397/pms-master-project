package com.sunglowsys;

import com.sunglowsys.domain.Customer;
import com.sunglowsys.resource.CustomerResource;
import com.sunglowsys.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerResource.class)
class CustomerResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private static String EXPEXTED_RESULT = "{\n" +
            "\t\"id\": 1,\n" +
            "\t\"firstName\": \"SSSSS\",\n" +
            "\t\"lastName\": \"SSSSS\",\n" +
            "\t\"email\": \"SSSSS@gmail.com\",\n" +
            "\t\"mobile\": \"SSSSS\",\n" +
            "\t\"gender\": \"MALE\"}";


    private static Customer createCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("SSSSS");
        customer.setLastName("SSSSS");
        customer.setEmail("SSSSS@gmail.com");
        customer.setMobile("SSSSS");
        customer.setGender("MALE");

        return customer;
    }

    private static Customer createCustomer2() {
        Customer customer2 = new Customer();
        customer2.setFirstName("AAAAA");
        customer2.setLastName("AAAAA");
        customer2.setEmail("AAAAA@gmail.com");
        customer2.setMobile("AAAAA");
        customer2.setGender("MALE");

        return customer2;
    }

    // create customer
    @Test
    void createCustomerTest() throws Exception {
        Customer mockCustomer = createCustomer();
        mockCustomer.setId(1L);
        when(customerService.save(any())).thenReturn(mockCustomer);

        MvcResult result = mockMvc.perform(post("/api/customers")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockCustomer)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(EXPEXTED_RESULT, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }


    // update customer
    @Test
    void updateCustomerTest() throws Exception {
        Customer mockCustomer = createCustomer();
        mockCustomer.setId(1L);
        when(customerService.update(any())).thenReturn(mockCustomer);

        MvcResult result = mockMvc.perform(put("/api/customers")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockCustomer)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(EXPEXTED_RESULT, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);


    }

    // get one customer
    @Test
    void getCustomerTest() throws Exception {
        Customer mockCustomer = createCustomer();
        mockCustomer.setId(1L);
        when(customerService.findOne(anyLong())).thenReturn(Optional.of(mockCustomer));

        MvcResult result = mockMvc.perform(get("/api/customers/1")
                        .accept(TestUtil.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(EXPEXTED_RESULT, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }


    // get all customers
    @Test
    void getAllCustomersTest() throws Exception {
        Customer mockCustomers1 = createCustomer();
        mockCustomers1.setId(1L);
        Customer mockCustomer2 = createCustomer2();
        mockCustomer2.setId(2L);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(mockCustomers1);
        customerList.add(mockCustomer2);

        when(customerService.findAll(any()))
                .thenReturn(new PageImpl<>(customerList, PageRequest.of(0, 2), 2));

        MvcResult result = mockMvc.perform(get("/api/customers")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(customerList)))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        //  assertEquals(result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }


    // delete customer
    @Test
    void deleteCustomerTest() throws Exception {
        Customer mockCostomer = createCustomer();
        mockCostomer.setId(1L);

        doNothing().when(customerService).delete(anyLong());

        MvcResult result = mockMvc.perform(delete("/api/customers/1")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockCostomer)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    // search customers
    @Test
    void searchCustomerTest() throws Exception {
        Customer mockCustomer1 = createCustomer();
        mockCustomer1.setId(1L);
        Customer mocKCustomer2 = createCustomer2();
        mocKCustomer2.setId(2L);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(mockCustomer1);
        customerList.add(mocKCustomer2);

        when(customerService.search(any(), eq(Pageable.unpaged()))).thenReturn(new PageImpl<>(customerList, PageRequest.of(0,2),2));

        MvcResult result = mockMvc.perform(get("/api/search/customers?query=AAAA")
                .contentType(TestUtil.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(customerList)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());


    }

}
