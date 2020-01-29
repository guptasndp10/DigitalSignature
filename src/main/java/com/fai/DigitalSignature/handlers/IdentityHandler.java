package com.fai.DigitalSignature.handlers;

import com.fai.DigitalSignature.service.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@CrossOrigin
public class IdentityHandler {

    private static Logger logger = LoggerFactory.getLogger(IdentityHandler.class);

    @Autowired
    IdentityService idmService;

    public Mono<ServerResponse> signAndEncrypt(ServerRequest request) {
        logger.debug("******************************SIGN AND ENCRYPT********************************");
        return idmService.handleDocSignAndEncrypt(request);
    }

    public Mono<ServerResponse> verifySignDecryptDocIntegrityCheck(ServerRequest request) {
        logger.debug("******************************VERIFY SIGN DECRYPT DOC INTEGRITY********************************");
        return idmService.handleVerifySigDecryptDocIntegrityCheck(request);
    }

    public Mono<ServerResponse> verifySignAndDecrypt(ServerRequest request) {
        logger.debug("******************************VERIFY SIGN DECRYPT DOC INTEGRITY********************************");
        return idmService.handleVerifySigAndDecrypt(request);
    }

    public Mono<ServerResponse> docSign(ServerRequest request) {
        logger.debug("******************************VERIFY SIGN DECRYPT DOC INTEGRITY********************************");
        return idmService.handleDocSign(request);
    }

    public Mono<ServerResponse> verifySign(ServerRequest request) {
        logger.debug("******************************VERIFY SIGN DECRYPT DOC INTEGRITY********************************");
        return idmService.handleVerifySig(request);
    }
}
