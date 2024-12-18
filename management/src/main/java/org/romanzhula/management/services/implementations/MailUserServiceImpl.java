package org.romanzhula.management.services.implementations;

import org.romanzhula.data_common.models.UserJpaDataModule;
import org.romanzhula.data_common.models.enums.UserState;
import org.romanzhula.data_common.objects.MailDataJpaDataModule;
import org.romanzhula.data_common.repositories.UserJpaDataModuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hashids.Hashids;
import org.romanzhula.management.services.MailUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Optional;


@Log4j
@RequiredArgsConstructor
@Service
public class MailUserServiceImpl implements MailUserService {

    @Value("${rabbitmq.queue.name.registration.mail}")
    private String registrationMailQueue;

    private final UserJpaDataModuleRepository userJpaDataModuleRepository;
    private final Hashids hashids;
    private final RabbitTemplate rabbitTemplate;


    @Override
    public String setUserEmail(UserJpaDataModule user, String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException exception) {
            return "Please, enter correct email. To cancel the command, enter /cancel";
        }

        Optional<UserJpaDataModule> optionalUser = userJpaDataModuleRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            user.setEmail(email);
            user.setUserState(UserState.COMMON_STATE);
            userJpaDataModuleRepository.save(user);

            String encodeUserId = hashids.encode(user.getId());
            sendRegistrationEmail(encodeUserId, email);

            return "We have send the letter to your email. Please go by link to confirm registration.";
        } else {
            return "This email exists. Please input correct email or to cancel the command, enter /cancel.";
        }
    }


    private void sendRegistrationEmail(String encodeUserId, String email) {
        MailDataJpaDataModule mailData = MailDataJpaDataModule.builder()
                .id(encodeUserId)
                .emailAddress(email)
                .build();

        rabbitTemplate.convertAndSend(registrationMailQueue, mailData);
    }


    @Override
    public String registerUser(UserJpaDataModule user) {
        if (user.getIsActive()) {
            return "You are already registered.";
        } else if (user.getEmail() != null) {
            return "You already received letter on your email. Please go by link to confirm registration.";
        }

        user.setUserState(UserState.WAIT_EMAIL_STATE);
        userJpaDataModuleRepository.save(user);

        return "Enter your email please:";
    }

}
