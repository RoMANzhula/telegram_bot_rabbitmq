package org.romanzhula.management.services;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandleService {

    void processTextMessage(Update update);
    void processPhotoMessage(Update update);
    void processDocumentMessage(Update update);

}
