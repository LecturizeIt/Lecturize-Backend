package github.com.miralhas.lecturizebackend.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("partysync.fatec@gmail.com");
        contact.setName("Lecturize It");
        contact.setUrl("http://localhost:8080");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info().title("Lecturize It Management API").version("1.0").contact(contact).description("This API exposes endpoints to Lecturize It application.").termsOfService("http://localhost:8080").license(mitLicense);

        SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearer-key");
        
        return new OpenAPI().info(info)
                .components(
                        new Components().addSecuritySchemes("bearer-key", securityScheme)
                ).addSecurityItem(securityRequirement);
    }
}
