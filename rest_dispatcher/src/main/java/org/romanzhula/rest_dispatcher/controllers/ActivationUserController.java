package org.romanzhula.rest_dispatcher.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.rest_dispatcher.services.ActivationUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ActivationUserController {

    private final ActivationUserService activationUserService;

    @GetMapping("/activation-user")
    public ResponseEntity<?> activationUser(@RequestParam String id) {
        return activationUserService.handleActivationRequest(id);
    }

}
