package com.fai.DigitalSignature.routes;

import com.fai.DigitalSignature.handlers.IdentityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.*;
import org.springframework.http.MediaType;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@CrossOrigin
public class RoutesMapper {

    @Bean
    public RouterFunction<ServerResponse> route(IdentityHandler identityHandler) {

        return RouterFunctions.route(POST("/signencryptdoc").and(accept(MediaType.APPLICATION_JSON)),identityHandler::signAndEncrypt)
                .andRoute(POST("/verifyandsigndoc").and(accept(MediaType.APPLICATION_JSON)),identityHandler::docSign)
                .andRoute(POST("/verifysign").and(accept(MediaType.APPLICATION_JSON)),identityHandler::verifySign)
                .andRoute(POST("/readverifysigndoc").and(accept(MediaType.APPLICATION_JSON)),identityHandler::verifySignDecryptDocIntegrityCheck)
                .andRoute(POST("/readdoc").and(accept(MediaType.APPLICATION_JSON)),identityHandler::verifySignAndDecrypt);
    }
}