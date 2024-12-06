package org.romanzhula.management.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.romanzhula.data_jpa.models.UserJpaDataModule;
import org.romanzhula.data_jpa.models.enums.UserState;
import org.romanzhula.data_jpa.repositories.UserJpaDataModuleRepository;
import org.romanzhula.management.enums.TelegramCommands;
import org.romanzhula.management.models.MessageData;
import org.romanzhula.management.repositories.MessageDataRepository;
import org.romanzhula.management.services.MessageHandleService;
import org.romanzhula.management.services.ProducerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.facilities.filedownloader.DownloadFileException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.romanzhula.data_jpa.models.enums.UserState.COMMON_STATE;
import static org.romanzhula.management.enums.TelegramCommands.*;


@Slf4j
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

        UserJpaDataModule userFromJpaData = findOrSaveUser(update);
        UserState userState = userFromJpaData.getUserState();
        String text = update.getMessage().getText();
        String output = "";

        if (text == null || userState == null) {
            log.error("Input data cannot be null!");
            throw new IllegalArgumentException("Input data cannot be null!");
        }

        TelegramCommands telegramCommand = fromCommand(text);

        if (CANCEL.equals(telegramCommand)) {
            output = processStop(userFromJpaData);
        } else {
            switch (userState) {
                case COMMON_STATE -> {
                    output = processTelegramCommand(userFromJpaData, text);
                }
                case WAIT_EMAIL_STATE -> {
                    log.info("You need to add your email!"); // TODO: change to method for MailSender
                }
                default -> {
                    log.error("Unknown command: {}", userState);
                    output = "Unknown command! Please input '/cancel' and try again. Use '/help'.";
                }
            }
        }

        Long chatId = update.getMessage().getChatId();

        sendAnswer(output, chatId);

    }

    @Override
    public void processPhotoMessage(Update update) {
        saveMessageData(update);

        UserJpaDataModule userFromJpaData = findOrSaveUser(update);
        Long chatId = update.getMessage().getChatId();

        if (checkAllowContent(chatId, userFromJpaData)) return;

        // TODO: add photo loading method
        String answer = "Photo has been successfully uploaded. Download link: ...";
        sendAnswer(answer, chatId);
    }

    @Override
    public void processDocumentMessage(Update update) {
        saveMessageData(update);

        UserJpaDataModule userFromJpaData = findOrSaveUser(update);
        Long chatId = update.getMessage().getChatId();

        if (checkAllowContent(chatId, userFromJpaData)) return;

        try {
            // TODO: add document loading method
            String answer = "Document has been successfully uploaded. Download link: ...";

            sendAnswer(answer, chatId);
        } catch (DownloadFileException exception) {
            String errorMessage = "Error! Download fatal. Try again later.";
            log.error(errorMessage);

            sendAnswer(errorMessage, chatId);
        }
    }

    private boolean checkAllowContent(Long chatId, UserJpaDataModule userFromJpaData) {
        if (isNotAllowToBeSent(chatId, userFromJpaData)) {
            log.error("Does not allow content to be sent.");
            return true;
        }
        return false;
    }

    private boolean isNotAllowToBeSent(Long chatId, UserJpaDataModule userFromJpaData) {
        UserState userState = userFromJpaData.getUserState();

        if (!userFromJpaData.getIsActive()) {
            String errorMessage = "Register or activate your account to download content.";

            sendAnswer(errorMessage, chatId);

            return true;
        } else if (!COMMON_STATE.equals(userState)) {
            String errorMessage = "Cancel the current command with '/cancel' to send the files.";

            sendAnswer(errorMessage, chatId);

            return true;
        }

        return false;
    }

    private String processTelegramCommand(UserJpaDataModule userFromJpaData, String command) {
        TelegramCommands telegramCommand = fromCommand(command);

        if (REGISTRATION.equals(telegramCommand)) {
            log.info("Sorry. Currently not work!");
            return "Sorry. Currently not work!";
        } else if (HELP.equals(telegramCommand)) {
            log.info("User entered the command '/help'");
            return commandHelp();
        } else if (START.equals(telegramCommand)) {
            log.info("User entered the command '/start'");
            return "Hello in our Bot. Please input '/help' to look for all common commands.";
        } else {
            log.warn("Received unknown command: {}", telegramCommand);
            return "Unknown command! Please input '/cancel' and try again. Or use '/help'.";
        }

    }

    private String commandHelp() {
        return "List of all commands:\n" +
                "/start - greeting message;\n" +
                "/cancel - cancel the current command;\n" +
                "/registration - register new user"
        ;
    }

    private String processStop(UserJpaDataModule userFromJpaData) {
        userFromJpaData.setUserState(COMMON_STATE);
        userJpaDataModuleRepository.save(userFromJpaData);

        return "Success! The command is canceled.";
    }

    private void sendAnswer(String output, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);

        producerService.produceAnswer(sendMessage);
    }

    private void saveMessageData(Update update) {
        MessageData messageData = MessageData.builder()
                .event(update)
                .build()
        ;

        messageDataRepository.save(messageData);
    }

    private UserJpaDataModule findOrSaveUser(Update update) {
        User telegramUser = update.getMessage().getFrom();

        UserJpaDataModule persistentUserFromDataJpaModule =
                userJpaDataModuleRepository.findByTelegramId(telegramUser.getId());

        if (persistentUserFromDataJpaModule == null) {
            UserJpaDataModule transientUser = UserJpaDataModule.builder()
                    .telegramId(telegramUser.getId())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    .userName(telegramUser.getUserName())
                    // TODO: change to false after added MailSender
                    .isActive(true)
                    .userState(COMMON_STATE)
                    .build()
            ;

            return userJpaDataModuleRepository.save(transientUser);
        }

        return persistentUserFromDataJpaModule;
    }

}
