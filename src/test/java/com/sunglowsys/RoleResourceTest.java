package com.sunglowsys;

import com.sunglowsys.domain.Role;
import com.sunglowsys.resource.RoleResource;
import com.sunglowsys.service.RoleService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = RoleResource.class)
class RoleResourceTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;


    private static String EXPECTED_RESPONCE = "{\n" +
            "\t\"id\": 1,\n" +
            "\t\"role\": \"AAAAA\"\n" +
            "}";


    private static Role createRole() {

        Role role = new Role();
        role.setRole("AAAAA");

        return role;
    }

    private static Role createRole2() {
        Role role1 = new Role();
        role1.setId(2L);
        role1.setRole("@@@@@");

        return role1;
    }

    // create role
    @Test
    void createRoleTest() throws Exception {

        Role mockRole = createRole();
        mockRole.setId(1L);
        when(roleService.save(any())).thenReturn(mockRole);

        MvcResult result = mockMvc.perform(post("/api/roles")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockRole)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(EXPECTED_RESPONCE, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);


    }

    //update role
    @Test
    void updateRoleTest() throws Exception {
        Role mockRole = createRole();
        mockRole.setId(1L);
        when(roleService.update(any())).thenReturn(mockRole);

        MvcResult result = mockMvc.perform(put("/api/roles")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockRole)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        //  assertEquals(EXPECTED_RESPONCE, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);


    }

    //get findOne role by id
    @Test
    void getRoleTest() throws Exception {

        Role mockRole = createRole();
        mockRole.setId(1L);

        when(roleService.findOne(anyLong())).thenReturn(Optional.of(mockRole));

        MvcResult result = mockMvc.perform(get("/api/roles/1")
                        .accept(TestUtil.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        //  assertEquals(EXPECTED_RESPONCE, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);

    }

    // get all role
    @Test
    void getAllRoleTest() throws Exception {
        Role mockRole = createRole();
        mockRole.setId(1L);
        Role mockRoles1 = createRole();
        mockRoles1.setId(2L);
        List<Role> roleList = new ArrayList<>();
        roleList.add(mockRole);
        roleList.add(mockRoles1);

        when(roleService.findAll(any()))
                .thenReturn(new PageImpl<>(roleList, PageRequest.of(0, 2), 2));

        MvcResult result = mockMvc.perform(get("/api/roles")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(roleList)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        // assertEquals(result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);


    }


    // delete role by id
    @Test
    void deleteRoleTest() throws Exception {
        Role mockRole = createRole();
        mockRole.setId(1L);

        doNothing().when(roleService).delete(anyLong());

        MvcResult result = mockMvc.perform(delete("/api/roles/1")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockRole)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }


}