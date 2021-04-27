package com.lambdaschool.java.services;

import com.lambdaschool.java.exceptions.ResourceNotFoundException;
import com.lambdaschool.java.models.Plant;
import com.lambdaschool.java.repositories.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class PlantServiceImpl implements PlantService {
    @Autowired
    private PlantRepository plantrepos;




    @Override
    public List<Plant> findAll() {
        return null;

    }
    @Transactional
    @Override
    public void delete(long id) {


    }
    @Transactional
    @Override
    public Plant update(long plantid, Plant plant) {
       return null;

    }
    @Transactional
    @Override
    public Plant save(long userid, Plant plant) {
        return null;
    }

    @Override
    public Plant findPlantById(long id) {
        return null;
    }
}
