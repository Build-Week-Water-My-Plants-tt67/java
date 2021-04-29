package com.lambdaschool.java.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.java.JavaApplicationTests;
import com.lambdaschool.java.models.Plant;
import com.lambdaschool.java.models.User;
import com.lambdaschool.java.services.PlantService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes= JavaApplicationTests.class,
        properties ={"command.line.runner.enabled=false"})
@WithMockUser(username = "admin",
        roles = {"USER", "ADMIN"})
@AutoConfigureMockMvc
public class PlantControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;



    @MockBean
    private PlantService plantService;
    private List<Plant> plantList;

    @Before
    public void setUp() throws Exception {
        plantList = new ArrayList<>();

        User u1 = new User("test","dogs","4349818441");
        User u2 = new User("test1","password","5555555555");




        Plant p1 = new Plant("TestPlant","testSpecies","once a week",u1);
        p1.setPlantid(11);

        plantList.add(p1);

        Plant p2 = new Plant("Testing", "species", "twice a week", u1);
        p2.setPlantid(12);

        plantList.add(p2);

        Plant p3 = new Plant("TestPlant2", "testingspecies", "once a day", u1);
        p3.setPlantid(13);

        plantList.add(p3);

        Plant p4 = new Plant("Azaleas", "testing", "once a day", u2);
        p4.setPlantid(14);
        plantList.add(p4);

        Plant p5 = new Plant("Fern", "species test", "once a week", u2);
        p5.setPlantid(15);
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPlantById() throws Exception {
        String apiUrl = "/api/plants/15";
        Mockito.when(plantService.findPlantById(15))
                .thenReturn(plantList.get(0));
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();
        ObjectMapper mapper = new ObjectMapper();

        String er =mapper.writeValueAsString(plantList.get(0));

        assertEquals(er,tr);
    }

    @Test
    public void listAllPlants() throws Exception {
        String apiUrl = "/api/plants";
        Mockito.when(plantService.findAll())
                .thenReturn(plantList);
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(plantList);
        System.out.println(er);
        assertEquals(er,tr);
    }

    @Test
    public void addNewPlant() throws Exception {

        User u1 = new User("username","password","4349818441");

        String apiUrl = "/api/plants";
      Plant p6 = new Plant("testing", "speciestest", "once a day",u1);
       ObjectMapper mapper = new ObjectMapper();
        String plantString = mapper.writeValueAsString(p6);
       Mockito.when(plantService.save(any(Plant.class)))
                .thenReturn(p6);
        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
               .accept(MediaType.APPLICATION_JSON)
              .contentType(MediaType.APPLICATION_JSON)
               .content(plantString);

       mockMvc.perform(rb)
              .andExpect(status().isCreated())
              .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void updateFullPlant() throws Exception {
        String plantName = "fern";
        String apiUrl = "/api/plants/{plantid}";
        User u1 = new User("username","password","4349818441");
        Plant p1 = new Plant(plantName,"test","once a day",u1);
        p1.setPlantid(100);

        Mockito.when(plantService.update(100,p1))
                .thenReturn(p1);


        ObjectMapper mapper = new ObjectMapper();
        String plantString = mapper.writeValueAsString(p1);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl,100)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(plantString);
        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }



    @Test
    public void deletePlantById() throws Exception {
        String apiUrl = "/api/plants/{plantid}";
        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl,"3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}