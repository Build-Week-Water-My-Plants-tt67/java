package com.lambdaschool.java.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.java.JavaApplicationTests;
import com.lambdaschool.java.models.Plant;
import com.lambdaschool.java.models.User;
import com.lambdaschool.java.repositories.PlantRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavaApplicationTests.class,
properties = {"command.line.runner.enabled = false"})
public class PlantServiceImplTest {

    @Autowired
    private PlantService plantService;

    @MockBean
    private PlantRepository plantrepos;

    List<Plant> plantList;

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

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findAll() {
        Mockito.when(plantrepos.findAll())
                .thenReturn(plantList);
                assertEquals(4,plantService.findAll().size());


    }

    @Test
    public void delete() {
        Mockito.when(plantrepos.findById(4L))
                .thenReturn(Optional.of(plantList.get(0)));
        Mockito.doNothing()
                .when(plantrepos)
                .deleteById(4L);
    }

    @Test
    public void deleteAll() {
        Mockito.doNothing()
                .when(plantrepos)
                .deleteAll();
              plantService.deleteAll();
              assertEquals(4,plantList.size());
    }

    @Test
    public void save() {
        User u1 = new User("test", "test", "4349818441");
        Plant p1 = new Plant("test", "species", "once a day", u1);

                plantList.add(p1);
            Mockito.when(plantrepos.save(any(Plant.class)))
                    .thenReturn(p1);
            Plant addPlant = plantService.save(p1);
            assertNotNull(addPlant);
            assertEquals("test",addPlant.getNickname());
    }

    @Test
    public void findPlantById() {
        Mockito.when(plantrepos.findById(14L))
                .thenReturn(Optional.of(plantList.get(0)));
        assertEquals("TestPlant",plantService.findPlantById(14).getNickname());
    }

    @Test
    public void update() throws JsonProcessingException {
        User u1 = new User("test","test","1234567890");
        String plantName = "test";
        Plant p2 = new Plant(plantName,
                "Test",
                "Test",
                u1);
        p2.setPlantid(10);



        ObjectMapper objectMapper = new ObjectMapper();
        Plant p3 = objectMapper
                .readValue(objectMapper.writeValueAsString(p2), Plant.class);

        Mockito.when(plantrepos.findById(10L))
                .thenReturn(Optional.of(p2));


        Mockito.when(plantrepos.save(any(Plant.class)))
                .thenReturn(p2);

        Plant addPlant = plantService.update(10,p2);

        assertNotNull(addPlant);
        assertEquals(plantName,
                addPlant.getNickname());

    }
}