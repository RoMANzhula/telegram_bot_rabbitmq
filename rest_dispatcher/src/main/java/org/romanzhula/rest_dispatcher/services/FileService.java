package org.romanzhula.rest_dispatcher.services;

import org.romanzhula.data_jpa.models.BinaryJpaDataModule;
import org.romanzhula.data_jpa.models.DocumentJpaDataModule;
import org.romanzhula.data_jpa.models.PhotoJpaDataModule;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

public interface FileService {

    DocumentJpaDataModule getDocument(String documentId);

    PhotoJpaDataModule getPhoto(String photoId);

    FileSystemResource getFileSystemResource(BinaryJpaDataModule binaryData);

    ResponseEntity<?> prepareDocumentResponse(String documentId);

    ResponseEntity<?> preparePhotoResponse(String photoId);

}