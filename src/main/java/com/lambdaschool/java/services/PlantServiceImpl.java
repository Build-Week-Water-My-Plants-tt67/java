package com.lambdaschool.java.services;

import com.lambdaschool.java.exceptions.ResourceNotFoundException;
import com.lambdaschool.java.models.Plant;
import com.lambdaschool.java.repositories.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Service(value = "plantService")
public class PlantServiceImpl implements PlantService {
    @Autowired
    private PlantRepository plantrepos;




    @Override
    public List<Plant> findAll() {
        List<Plant> list = new ArrayList<>();
        plantrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;

    }
    @Transactional
    @Override
    public void delete(long id){
        plantrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("plant id" + id + "not found"));
        plantrepos.deleteById(id);
    }

    @Override
    public void deleteAll() {
        plantrepos.deleteAll();

    }


    @Transactional
    @Override
    public Plant save(Plant plant) {
        Plant newPlant = new Plant();
        if(plant.getPlantid()!= 0){
            plantrepos.findById(plant.getPlantid())
                    .orElseThrow(() -> new ResourceNotFoundException("plant id" + plant.getPlantid() + "not found"));
         newPlant.setPlantid(plant.getPlantid());
        }
        newPlant.setNickname(plant.getNickname());
        newPlant.setSpecies(plant.getSpecies());
        newPlant.setH2ofrequency(plant.getH2ofrequency());

      return  plantrepos.save(newPlant);

    }

    @Override
    public Plant findPlantById(long id) throws ResourceNotFoundException {

        return plantrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("plant with"+ id + "not found"));
    }


    @Override
    public Plant update(long id, Plant plant) {
        Plant currentPlant = findPlantById(id);
        if (plant.getNickname() != null){
            currentPlant.setNickname(plant.getNickname().toLowerCase());
        }
        if(plant.getSpecies() != null){
            currentPlant.setSpecies(plant.getSpecies().toLowerCase());

        }
        if (plant.getH2ofrequency() != null){
            currentPlant.setH2ofrequency(plant.getH2ofrequency().toLowerCase());
        }

        return plantrepos.save(currentPlant);
    }
}
