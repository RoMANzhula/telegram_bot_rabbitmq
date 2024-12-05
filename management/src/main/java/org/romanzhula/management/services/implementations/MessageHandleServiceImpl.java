package org.romanzhula.management.services.implementations;

import org.romanzhula.data_jpa.models.UserJpaDataModule;
import org.romanzhula.data_jpa.models.enums.UserState;
import org.romanzhula.data_jpa.repositories.UserJpaDataModuleRepository;
import org.romanzhula.management.models.MessageData;
import org.romanzhula.management.repositories.MessageDataRepository;
import org.romanzhula.management.services.MessageHandleService;
import org.romanzhula.management.services.ProducerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;


@Service
public class MessageHandleServiceImpl implements MessageHandleService {

    private final MessageDataRepository messageDataRepository;
    private final ProducerService producerService;
    private final UserJpaDataModuleRepository userJpaDataModuleRepository;

    public MessageHandleServiceImpl(
            MessageDataRepository messageDataRepository,
            ProducerService producerService,
            UserJpaDataModuleRepository userJpaDataModuleRepository
    ) {
        this.messageDataRepository = messageDataRepository;
        this.producerService = producerService;
        this.userJpaDataModuleRepository = userJpaDataModuleRepository;
    }

    @Override
    public void processTextMessage(Update update) {
        saveMessageData(update);

        Message textMessage = update.getMessage();
        User telegramUser = textMessage.getFrom();
        UserJpaDataModule userFromJpaData = findOrSaveUser(telegramUser);

        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello from MANAGEMENT!");

        producerService.produceAnswer(sendMessage);
    }

    private void saveMessageData(Update update) {
        MessageData messageData = MessageData.builder()
                .event(update)
                .build()
        ;

        messageDataRepository.save(messageData);
    }

    private UserJpaDataModule findOrSaveUser(User telegramUser) {
        UserJpaDataModule persistentUserFromDataJpaModule = userJpaDataModuleRepository.findByTelegramId(telegramUser.getId());

        if (persistentUserFromDataJpaModule == null) {
            UserJpaDataModule transientUser = UserJpaDataModule.builder()
                    .telegramId(telegramUser.getId())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    .userName(telegramUser.getUserName())
                    // TODO change to false after added MailSender
                    .isActive(true)
                    .userState(UserState.COMMON_STATE)
                    .build()
            ;

            return userJpaDataModuleRepository.save(transientUser);
        }

        return persistentUserFromDataJpaModule;
    }

}
