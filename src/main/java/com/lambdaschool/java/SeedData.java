package com.lambdaschool.java;

import com.lambdaschool.java.models.*;
import com.lambdaschool.java.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

    @Transactional
    @Override
    public void run(String[] args) throws
                                   Exception
    {
        userService.deleteAll();

        User u1 = new User("guest",
                           "password",
                           "5555555555");
        userService.save(u1);

        User u2 = new User("tarah",
            "password",
            "4444444444");
        userService.save(u2);

        User u3 = new User("george",
            "password!",
            "2222222222");
        userService.save(u3);

        User u4 = new User("jason",
            "password",
            "8888888888");
        userService.save(u4);
    }
}