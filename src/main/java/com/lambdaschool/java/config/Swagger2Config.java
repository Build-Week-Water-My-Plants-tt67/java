package com.lambdaschool.java.config;

import org.springframework.context.annotation.*;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configures the default Swagger Documentation
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config
{
    /**
     * Configures what to document using Swagger
     *
     * @return A Docket which is the primary interface for Swagger configuration
     */
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                              .basePackage("com.lambdaschool.java"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(apiEndPointsInfo());
    }

    /**
     * Configures some information related to the Application for Swagger
     *
     * @return ApiInfo a Swagger object containing identification information for this application
     */
    private ApiInfo apiEndPointsInfo()
    {
        return new ApiInfoBuilder().title("Water-My-Plants")
                .description("Water-My-Plants")
                .contact(new Contact("bw-tt-67",
                                     "https://github.com/Build-Week-Water-My-Plants-tt67/java",
                                     "noreply@lambdaschool.com"))
                .build();
    }
}