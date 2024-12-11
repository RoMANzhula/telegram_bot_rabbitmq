package org.romanzhula.rest_dispatcher.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.romanzhula.data_jpa.models.UserJpaDataModule;
import org.romanzhula.data_jpa.repositories.UserJpaDataModuleRepository;
import org.romanzhula.rest_dispatcher.components.HashidsDecoder;
import org.romanzhula.rest_dispatcher.exceptions.InvalidUserIdException;
import org.romanzhula.rest_dispatcher.services.ActivationUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Log4j
@Service
public class ActivationUserServiceImpl implements ActivationUserService {

    private final UserJpaDataModuleRepository userJpaDataModuleRepository;
    private final HashidsDecoder hashidsDecoder;


    @Override
    public ResponseEntity<?> handleActivationRequest(String codedUserId) {
        Long userIdFromCoded = hashidsDecoder.getIdFromString(codedUserId);
        log.debug(String.format("User activation with id=%s", userIdFromCoded));

        if (userIdFromCoded == null) {
            log.error("The provided user ID is invalid.");
            throw new InvalidUserIdException("The provided user ID is invalid.");
        }

        Optional<UserJpaDataModule> optionalUser = userJpaDataModuleRepository.findById(userIdFromCoded);

        if (optionalUser.isPresent()) {
            UserJpaDataModule userJpaDataModule = optionalUser.get();

            userJpaDataModule.setIsActive(true);
            userJpaDataModuleRepository.save(userJpaDataModule);

            return ResponseEntity.ok().body("Successfully user activation.");
        }

        return ResponseEntity.badRequest().body("Incorrect link.");
    }

}
