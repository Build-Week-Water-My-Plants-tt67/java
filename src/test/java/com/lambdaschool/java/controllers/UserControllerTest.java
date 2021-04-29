package com.lambdaschool.java.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.java.JavaApplicationTests;
import com.lambdaschool.java.models.Plant;
import com.lambdaschool.java.models.User;
import com.lambdaschool.java.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JavaApplicationTests.class)
@WithMockUser(username = "user", roles = {"ADMIN"})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    List<User> myUserList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        myUserList = new ArrayList<>();

        User u1 = new User("test","dogs","4349818441");
        User u2 = new User("test1","password","5555555555");




        Plant p1 = new Plant("TestPlant","testSpecies","once a week",u1);
        p1.setPlantid(11);

        Plant p2 = new Plant("Testing", "species", "twice a week", u1);
        p2.setPlantid(12);

        Plant p3 = new Plant("TestPlant2", "testingspecies", "once a day", u1);
        p3.setPlantid(13);

        Plant p4 = new Plant("Azaleas", "testing", "once a day", u2);
        p4.setPlantid(14);

        Plant p5 = new Plant("Fern", "species test", "once a week", u2);
        p5.setPlantid(15);

        myUserList.add(u1);
        myUserList.add(u2);

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void listAllUsers() throws Exception {

        String apiUrl = "http://localhost:2019/api/users";
        Mockito.when(userService.findAll())
                .thenReturn(myUserList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(myUserList);

        System.out.println(er);
        assertEquals(er, tr);
    }

    @Test
    public void getUserById() throws Exception{

        String apiUrl= "http://localhost:2019/api/users/1";
        Mockito.when(userService.findUserById(1))
                .thenReturn(myUserList.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(myUserList.get(1));

        System.out.println(tr);
        assertEquals(er, tr);
    }

    @Test
    public void getUserByName() throws Exception {

        String apiUrl = "http://localhost:2019/api/users/name/test";

        Mockito.when(userService.findByName("test"))
                .thenReturn(myUserList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(myUserList.get(0));

        System.out.println("Expect " + er);
        System.out.println("Actual " + tr);

        Assert.assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getUserLikeName() throws Exception {
        String apiUrl = "http://localhost:2019/api/users/name/like/test";

        Mockito.when(userService.findByNameContaining("test"))
                .thenReturn(myUserList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(myUserList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        Assert.assertEquals("Rest API Returns List", er, tr);

    }

    @Test
    public void updateUser() throws Exception {

        String apiUrl = "http://localhost:2019/api/users/3";
        String rest3Name = "Test test";

        User u3 = new User(rest3Name,"password","5555555555");
        u3.setUserid(3);

        Mockito.when(userService.update(u3, 3L))
                .thenReturn(u3);
        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u3);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void deleteUserById() throws Exception {
        String apiUrl = "http://localhost:2019/api/users/{id}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}