package org.romanzhula.rest_dispatcher.services;

import org.springframework.http.ResponseEntity;

public interface ActivationUserService {

    ResponseEntity<?> handleActivationRequest(String codedUserId);

}
