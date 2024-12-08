package org.romanzhula.management.services.implementations;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.hashids.Hashids;
import org.json.JSONObject;
import lombok.extern.log4j.Log4j;
import org.romanzhula.data_jpa.models.BinaryJpaDataModule;
import org.romanzhula.data_jpa.models.DocumentJpaDataModule;
import org.romanzhula.data_jpa.models.PhotoJpaDataModule;
import org.romanzhula.data_jpa.repositories.BinaryJpaDataModuleRepository;
import org.romanzhula.data_jpa.repositories.DocumentJpaDataModuleRepository;
import org.romanzhula.data_jpa.repositories.PhotoJpaDataModuleRepository;
import org.romanzhula.management.enums.DownloadLinkType;
import org.romanzhula.management.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

@RequiredArgsConstructor
@Log4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${telegram.bot.token}")
    private String telegramToken;

    @Value("${telegram.bot.service.file_storage.uri}")
    private String telegramFileStorageUri;

    @Value("${telegram.bot.service.file_info.uri}")
    private String telegramFileInfoUri;

    @Value("${telegram.download_link.address}")
    private String downloadLinkAddress;


    private final DocumentJpaDataModuleRepository documentJpaDataModuleRepository;
    private final BinaryJpaDataModuleRepository binaryJpaDataModuleRepository;
    private final PhotoJpaDataModuleRepository photoJpaDataModuleRepository;
    private final Hashids hashids;


    @Override
    public DocumentJpaDataModule processDocument(Message inputFromTelegramMessage) {
        Document document = inputFromTelegramMessage.getDocument();
        String fileId = document.getFileId();
        ResponseEntity<String> responseEntity = getFilePath(fileId);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            BinaryJpaDataModule persistentBinaryData = getPersistentBinaryData(responseEntity);
            DocumentJpaDataModule transientDocumentJpaDataModule =
                    buildTransientDocumentJpaDataModule(document, persistentBinaryData);

            return documentJpaDataModuleRepository.save(transientDocumentJpaDataModule);
        } else {
            log.error("Bad response from file service: " + responseEntity);
            throw new RuntimeException("Bad response from file service: " + responseEntity);
        }
    }

    @Override
    public PhotoJpaDataModule processPhoto(Message inputFromTelegramMessage) {
        int photoSize = inputFromTelegramMessage.getPhoto().size();
        int indexOfPhoto = photoSize > 1 ? inputFromTelegramMessage.getPhoto().size() - 1 : 0;
        PhotoSize photoFromBot = inputFromTelegramMessage.getPhoto().get(indexOfPhoto);
        String fileId = photoFromBot.getFileId();

        ResponseEntity<String> responseEntity = getFilePath(fileId);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            BinaryJpaDataModule persistentBinaryData = getPersistentBinaryData(responseEntity);
            PhotoJpaDataModule transientPhotoJpaDataModule =
                    buildTransientPhotoJpaDataModule(photoFromBot, persistentBinaryData);

            return photoJpaDataModuleRepository.save(transientPhotoJpaDataModule);
        } else {
            log.error("Bad response from file service: " + responseEntity);
            throw new RuntimeException("Bad response from file service: " + responseEntity);
        }
    }

    @Override
    public String generateLink(Long fileId, DownloadLinkType downloadLinkType) {
        String hashId = hashids.encode(fileId);

        return downloadLinkAddress + "/api" + downloadLinkType + "?id=" + hashId;
    }

    private PhotoJpaDataModule buildTransientPhotoJpaDataModule(
            PhotoSize photoFromBot,
            BinaryJpaDataModule persistentBinaryData
    ) {
        return PhotoJpaDataModule.builder()
                .telegramId(photoFromBot.getFileId())
                .binaryData(persistentBinaryData)
                .fileLength(photoFromBot.getFileSize())
                .build()
        ;
    }

    private DocumentJpaDataModule buildTransientDocumentJpaDataModule(
            Document document,
            BinaryJpaDataModule persistentBinaryData
    ) {
        return DocumentJpaDataModule.builder()
                .telegramId(document.getFileId())
                .name(document.getFileName())
                .binaryData(persistentBinaryData)
                .mimeType(document.getMimeType())
                .fileSize(document.getFileSize())
                .build()
        ;
    }

    private BinaryJpaDataModule getPersistentBinaryData(ResponseEntity<String> responseEntity) {
        String filePath = getFilePathAsString(responseEntity);
        byte[] fileToByte = downloadFile(filePath);
        BinaryJpaDataModule transientBinaryContent = BinaryJpaDataModule.builder()
                .convertedFile(fileToByte)
                .build()
        ;

        return binaryJpaDataModuleRepository.save(transientBinaryContent);
    }

    private String getFilePathAsString(ResponseEntity<String> responseEntity) {
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());

        return String.valueOf(jsonObject
                .getJSONObject("result")
                .getString("file_path"))
        ;
    }


    private byte[] downloadFile(String filePath) {
        String fullUri = telegramFileStorageUri.replace("{token}", telegramToken)
                .replace("{filePath}", filePath);

        try {
            Resource resource = new UrlResource(fullUri);
            try (InputStream is = resource.getInputStream()) {
                return is.readAllBytes();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid URL: " + fullUri, e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file from: " + fullUri, e);
        }
    }



    private ResponseEntity<String> getFilePath(String fileId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<Object> request = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(
                telegramFileInfoUri,
                HttpMethod.GET,
                request,
                String.class,
                telegramToken,
                fileId
        );
    }

}
