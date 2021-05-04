package com.lambdaschool.java.repositories;

import com.lambdaschool.java.models.Plant;
import org.springframework.data.repository.CrudRepository;

public interface PlantRepository extends CrudRepository<Plant,Long> {
}
