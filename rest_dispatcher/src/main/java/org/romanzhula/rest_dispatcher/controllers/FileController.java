package org.romanzhula.rest_dispatcher.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.romanzhula.data_jpa.models.BinaryJpaDataModule;
import org.romanzhula.data_jpa.models.DocumentJpaDataModule;
import org.romanzhula.data_jpa.models.PhotoJpaDataModule;
import org.romanzhula.rest_dispatcher.services.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j
@RequiredArgsConstructor
@RequestMapping("/api/file")
@RestControllerAdvice
@RestController
public class FileController {

    private final FileService fileService;


    @GetMapping("/get-document")
    public ResponseEntity<?> getDocument(@RequestParam String documentId) {
        return fileService.prepareDocumentResponse(documentId);
    }

    @GetMapping("/get-photo")
    public ResponseEntity<?> getPhoto(@RequestParam String photoId) {
        return fileService.preparePhotoResponse(photoId);
    }

}
