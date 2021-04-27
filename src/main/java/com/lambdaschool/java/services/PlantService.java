package com.lambdaschool.java.services;

import com.lambdaschool.java.models.Plant;

import java.awt.*;
import java.util.List;

public interface PlantService {


   public List<Plant> findAll();

    void delete(long id);

    Plant update(long plantid ,Plant plant);

    Plant save(long userid ,Plant plant);

    Plant findPlantById(long id);











}
