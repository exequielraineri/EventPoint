/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

/**
 *
 * @author Exequiel
 */
@OpenAPIDefinition(
        info = @Info(
                title = "API Rest EventPoint",
                description = "API Rest para manejar toda la logica de la aplicacion de EventPoint",
                version = "1.0.0",
                contact = @Contact(
                        name = "Exequiel Raineri",
                        email = "exeraineridev@gmail.com",
                        url = "https://exeraineri.com"),
                license = @License(
                        name = "Standard",
                        url = "https://exeraineri.com"
                )),
        servers = {
            @Server(
                    description = "DEV SERVER",
                    url = "http://localhost:8080/api/v1/"
            ),
            @Server(
                    description = "PROD SERVER",
                    url = "http://localhost:8080/api/v1/"
            )
        },
        security = @SecurityRequirement(
                name = "Security Token"
        )
)
@SecurityScheme(
        name = "Security Token",
        description = "Token de acceso a la API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

}
