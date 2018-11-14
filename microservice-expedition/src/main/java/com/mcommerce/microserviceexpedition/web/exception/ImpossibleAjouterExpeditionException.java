package com.mcommerce.microserviceexpedition.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ImpossibleAjouterExpeditionException extends RuntimeException {

    public ImpossibleAjouterExpeditionException(String message) {
        super(message);
    }
}
