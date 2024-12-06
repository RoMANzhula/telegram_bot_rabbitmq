package org.romanzhula.management.services;

import org.romanzhula.data_jpa.models.DocumentJpaDataModule;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileService {

    DocumentJpaDataModule processDocument(Message inputFromTelegramMessage);

}
