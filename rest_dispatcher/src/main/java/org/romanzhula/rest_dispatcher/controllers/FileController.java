package org.romanzhula.rest_dispatcher.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.rest_dispatcher.services.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/file")
@RestController
public class FileController {

    private final FileService fileService;


    @GetMapping("/get-document")
    public ResponseEntity<?> getDocument(@RequestParam String id) {
        return fileService.prepareDocumentResponse(id);
    }

    @GetMapping("/get-photo")
    public ResponseEntity<?> getPhoto(@RequestParam String id) {
        return fileService.preparePhotoResponse(id);
    }

}
