package com.lambdaschool.java.services;

import com.lambdaschool.java.models.Plant;

import java.util.List;

public interface PlantService {


   public List<Plant> findAll();

    void delete(long id);

    void deleteAll();



    Plant findPlantById(long id);


    Plant save(Plant newPlant);

    Plant update(long id, Plant plant);
}
