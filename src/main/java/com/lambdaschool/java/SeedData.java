package com.lambdaschool.java;

import com.lambdaschool.java.models.*;

import com.lambdaschool.java.services.PlantService;

import com.lambdaschool.java.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CommandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Component
public class SeedData
    implements CommandLineRunner
{
    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    @Autowired
    PlantService plantService;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */

    @Transactional
    @Override
    public void run(String[] args) throws
                                   Exception
    {
        userService.deleteAll();
        plantService.deleteAll();


        User u1 = new User("guest",
                           "password",
                           "5555555555");
        u1.getPlants()
          .add(new Plant("plant","asper","Every 3 days", u1));
        userService.save(u1);

        User u2 = new User("tarah",
            "password",
            "4444444444");
        u2.getPlants()
          .add(new Plant("jibjab","affinis","once a week", u2));
        userService.save(u2);

        User u3 = new User("george",
            "password!",
            "2222222222");
        u3.getPlants()
          .add(new Plant("test","clinata","twice a week", u3));
        userService.save(u3);

        User u4 = new User("jason",
            "password",
            "8888888888");
        u4.getPlants()
            .add(new Plant("testPlant","testSpecies","twice a week", u4));
        userService.save(u4);
    }
}