package org.romanzhula.rest_dispatcher.services.implementations;

import org.romanzhula.data_common.models.BinaryJpaDataModule;
import org.romanzhula.data_common.models.DocumentJpaDataModule;
import org.romanzhula.data_common.models.PhotoJpaDataModule;
import org.romanzhula.data_common.repositories.DocumentJpaDataModuleRepository;
import org.romanzhula.data_common.repositories.PhotoJpaDataModuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.romanzhula.rest_dispatcher.components.HashidsDecoder;
import org.romanzhula.rest_dispatcher.exceptions.InternalServerException;
import org.romanzhula.rest_dispatcher.exceptions.ResourceNotFoundException;
import org.romanzhula.rest_dispatcher.services.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Log4j
@RequiredArgsConstructor
@Service("fileServiceRestDispatcher")
public class FileServiceImpl implements FileService {

    private final DocumentJpaDataModuleRepository documentJpaDataModuleRepository;
    private final PhotoJpaDataModuleRepository photoJpaDataModuleRepository;
    private final HashidsDecoder hashidsDecoder;


    @Override
    public DocumentJpaDataModule getDocument(String documentId) {
        Long id = hashidsDecoder.getIdFromString(documentId);
        // checking Hashids decoding
        log.info("Decoded documentId: " + documentId + " to ID: " + id);

        if (id == null) {
            return null;
        }

        return documentJpaDataModuleRepository.findById(id).orElse(null);
    }


    @Override
    public PhotoJpaDataModule getPhoto(String photoId) {
        Long id = hashidsDecoder.getIdFromString(photoId);
        // checking Hashids decoding
        log.info("Decoded photoId: " + photoId + " to ID: " + id);

        if (id == null) {
            return null;
        }

        return photoJpaDataModuleRepository.findById(id).orElse(null);
    }


    @Override
    public FileSystemResource getFileSystemResource(BinaryJpaDataModule binaryData) {
        try {
            String uniqueFileName = "temporaryFile_" + UUID.randomUUID() + ".bin";

            File temporary = new File(System.getProperty("java.io.tmpdir"), uniqueFileName);

            temporary.deleteOnExit();

            FileUtils.writeByteArrayToFile(temporary, binaryData.getConvertedFile());

            return new FileSystemResource(temporary);
        } catch (IOException exception) {
            log.error("Error! Problem with file: {}", exception);

            return null;
        }
    }


    @Override
    public ResponseEntity<?> prepareDocumentResponse(String documentId) {
        DocumentJpaDataModule document = getDocument(documentId);
        if (document == null) {
            log.error("Document by id not found!");
            throw new ResourceNotFoundException("Document not found by id: " + documentId);
        }

        BinaryJpaDataModule binaryData = document.getBinaryData();
        if (binaryData == null) {
            log.error("Binary data not found for document id: " + documentId);
            throw new InternalServerException("Binary data not found for document id: " + documentId);
        }

        FileSystemResource fileSystemResource = getFileSystemResource(binaryData);
        if (fileSystemResource == null) {
            log.error("File system resource not found for document id: " + documentId);
            throw new InternalServerException("File system resource not found for document id: " + documentId);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getMimeType()))
                .header("Content-Disposition", "attachment; filename=" + document.getName())
                .body(fileSystemResource);
    }


    @Override
    public ResponseEntity<?> preparePhotoResponse(String photoId) {
        PhotoJpaDataModule photo = getPhoto(photoId);
        if (photo == null) {
            log.error("Photo not found by id: " + null);
            throw new ResourceNotFoundException("Photo not found by id: " + null);
        }

        BinaryJpaDataModule binaryData = photo.getBinaryData();
        if (binaryData == null) {
            log.error("Binary data not found for photo id: " + photoId);
            throw new InternalServerException("Binary data not found for photo id: " + photoId);
        }

        FileSystemResource fileSystemResource = getFileSystemResource(binaryData);
        if (fileSystemResource == null) {
            log.error("File system resource not found for photo id: " + photoId);
            throw new InternalServerException("File system resource not found for photo id: " + photoId);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header("Content-Disposition", "attachment;")
                .body(fileSystemResource)
        ;
    }

}
