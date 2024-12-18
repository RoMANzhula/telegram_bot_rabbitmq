package org.romanzhula.management.services;

import org.romanzhula.data_common.models.DocumentJpaDataModule;
import org.romanzhula.data_common.models.PhotoJpaDataModule;
import org.romanzhula.management.enums.DownloadLinkType;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileService {

    DocumentJpaDataModule processDocument(Message inputFromTelegramMessage);

    PhotoJpaDataModule processPhoto(Message inputFromTelegramMessage);

    String generateLink(Long fileId, DownloadLinkType downloadLinkType);

}
