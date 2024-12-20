package org.romanzhula.management.services;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {

    void consumeTextMessageUpdate(Update update);
    void consumeDocumentMessageUpdate(Update update);
    void consumePhotoMessageUpdate(Update update);

}
