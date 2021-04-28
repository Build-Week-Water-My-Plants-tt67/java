package com.lambdaschool.java.controllers;

import com.lambdaschool.java.models.Plant;
import com.lambdaschool.java.services.PlantService;
import org.h2.table.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlantController {
    @Autowired
    private PlantService plantService;

    @GetMapping(value = "/plants/{plantId}",produces = "application/json")
    public ResponseEntity<?> getPlantById(@PathVariable Long plantId)
    {
        Plant p = plantService.findPlantById(plantId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping(value = "/plants",produces = "application/json")
    public ResponseEntity<?> listAllPlants()
    {
        List<Plant> myPlants = plantService.findAll();
        return new ResponseEntity<>(myPlants,HttpStatus.OK);
    }

    @PostMapping(value = "/plants",consumes = "application/json")
    public ResponseEntity<?> addNewPlant(@Valid @RequestBody Plant newPlant) throws URISyntaxException
    {
        newPlant.setPlantid(0);
        newPlant = plantService.save(newPlant);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPlantURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{plantid}")
                .buildAndExpand(newPlant.getPlantid())
                .toUri();
        responseHeaders.setLocation(newPlantURI);

        return new ResponseEntity<>(null,responseHeaders,HttpStatus.CREATED);
    }

    @PutMapping(value = "/plants/{plantid}",consumes = "application/json")
    public ResponseEntity<?> updateFullPlant(@Valid@RequestBody Plant updatePlant , @PathVariable long plantid)
    {
        updatePlant.setPlantid(plantid);
        plantService.save(updatePlant);

        return new ResponseEntity<>(updatePlant, HttpStatus.OK);
    }

    @PatchMapping(value = "/plants/{id}",
        consumes = "application/json")
    public ResponseEntity<?> updatePlant(
        @RequestBody
            Plant updatePlant,
        @PathVariable
            long id)
    {
        plantService.update(id, updatePlant);
        return new ResponseEntity<>(updatePlant, HttpStatus.OK);
    }

    @DeleteMapping(value = "/plants/{plantid}")
    public ResponseEntity<?> deletePlantById(@PathVariable long plantid)
    {
        plantService.delete(plantid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
