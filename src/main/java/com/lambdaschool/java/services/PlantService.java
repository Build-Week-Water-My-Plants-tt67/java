package com.lambdaschool.java.services;

import com.lambdaschool.java.models.Plant;

import java.awt.*;
import java.util.List;

public interface PlantService {


   public List<Plant> findAll();

    void delete(long id);



    Plant save(long plantid ,Plant plant);

    Plant findPlantById(long id);


    Plant save(Plant newPlant);

    Plant update(long id, Plant plant);
}
