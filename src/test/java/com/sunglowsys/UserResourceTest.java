package com.sunglowsys;

import com.sunglowsys.domain.User;
import com.sunglowsys.resource.UserResource;
import com.sunglowsys.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserResource.class)
class UserResourceTest {
    private static String expected = "{\n" +
            "\t\"id\": 1,\n" +
            "\t\"login\": \"AAAAAAAA\",\n" +
            "\t\"password\": \"AAAAAAAA\",\n" +
            "\t\"firstName\": \"AAAAAAAA\",\n" +
            "\t\"lastName\": \"AAAAAAAA\",\n" +
            "\t\"activated\": true\n" +
            "}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static User createUser() {
        User user = new User();
        user.setFirstName("AAAAAAAA");
        user.setLastName("AAAAAAAA");
        user.setLogin("AAAAAAAA");
        user.setPassword("AAAAAAAA");
        user.setActivated(Boolean.TRUE);
        return user;
    }

    @Test
    void createUserTest() throws Exception {
        User mockUser = createUser();
        mockUser.setId(1L);
        when(userService.save(any())).thenReturn(mockUser);

        // Create the Address
        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockUser)))
                .andReturn();


        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        //assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
     void getUserTest() throws Exception {
        User mockUser = createUser();
        mockUser.setId(1L);
        when(userService.findOne(anyLong())).thenReturn(Optional.of(mockUser));

        MvcResult result = mockMvc.perform(get("/api/users/1")
                        .accept(TestUtil.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(expected, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);

    }

    @Test
    void deleteUserResourceTest() throws Exception {
        User user = createUser();
        user.setId(1L);
        doNothing().when(userService).delete(anyLong());
        MvcResult mvcResult = mockMvc.perform(delete("/api/users/1")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(user)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllResourceTest() throws Exception {
        User user = createUser();
        user.setId(1L);
        User user1 = createUser();
        user1.setId(2L);

        List<User> userList = new ArrayList<User>();
        userList.add(user);
        userList.add(user1);

        when(userService.findAll(any())).thenReturn(new PageImpl<User>(userList, PageRequest.of(0,2),2));
        MvcResult mvcResult = mockMvc.perform(get("/api/users")
                .contentType(TestUtil.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(userList)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void updateUserResourceTest() throws Exception {
        User user = createUser();
        user.setId(1L);

        when(userService.update(any())).thenReturn(user);
        MvcResult mvcResult = mockMvc.perform(put("/api/users")
                .contentType(TestUtil.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(user)))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(expected, mvcResult.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }
}
