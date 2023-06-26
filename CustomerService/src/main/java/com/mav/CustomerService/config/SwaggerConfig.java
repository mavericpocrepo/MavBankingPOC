package com.mav.CustomerService.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/*@OpenAPIDefinition(
        info = @Info(
                title = "CUSTOMER-SERVICE",
                version = "v1.0"
        )
)*/

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {
    // Additional configuration or customization can be added here
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }
    @Bean
    public ApiInfo apiInfo() {
        final ApiInfoBuilder builder = new ApiInfoBuilder();
        return builder.title("CUSTOMER-SERVICE")
                .description("Controller for CUSTOMER-SERVICE")
                .version("1.0.0")
                .license("MIT Licence")
                .licenseUrl("https://your-license-url.com").build();
        //return builder.build();
    }
}

