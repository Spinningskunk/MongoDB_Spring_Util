package com.ikun.cm.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
/**
 * @author: HeKun
 * @date: 2024/10/29 0:49
 * @description: Swagger configuration class for API documentation
 * This class configures Swagger for the application using the Knife4j tool,
 * enabling API documentation and providing meta information
 */
public class Knife4jConfiguration {

    @Bean(value = "dockerBean")
    public Docket dockerBean() {
        // Specify that we are using Swagger2 documentation type
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                //API description that supports Markdown syntax
                .description("#ikun的服务Swagger接口文档")
                //Contact me if u want
                .termsOfServiceUrl("2536684724@qq.com")
                .contact("2536684724@qq.com")
                .version("1.0")
                .build())
                //Group name
                .groupName("MongoDBLearning")
                .select()
                //Specify the package to scan for controllers
                .apis(RequestHandlerSelectors.basePackage("com.ikun.cm.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
