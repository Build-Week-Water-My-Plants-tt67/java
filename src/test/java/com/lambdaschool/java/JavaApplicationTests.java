package com.lambdaschool.java;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class JavaApplicationTests {

  @Autowired
  private static Environment env;

  /**
   * If an environment variable is not found, set this to true
   */
  private static boolean stop = false;

  /**
   * If an application relies on an environment variable, check to make sure that environment variable is available!
   * If the environment variable is not available, you could set a default value, or as is done here, stop execution of the program
   *
   * @param envvar The system environment where environment variable live
   */
  private static void checkEnvironmentVariable(String envvar) {
    if (System.getenv(envvar) == null) {
      stop = true;
      System.out.println("***** Environment Variable " + envvar + " Not Found *****");
    }
  }

  /**
   * Main method to start the application.
   *
   * @param args Not used in this application.
   */
  public static void main(String[] args) {
    // Check to see if the environment variables exists. If they do not, stop execution of application.
    checkEnvironmentVariable("OAUTHCLIENTID");
    checkEnvironmentVariable("OAUTHCLIENTSECRET");

    if (!stop) {
      // so run the application!
      SpringApplication.run(JavaApplicationTests.class,
              args);
    }
  }
}
