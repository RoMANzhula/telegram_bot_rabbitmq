package org.romanzhula.mail_dispatcher.services.implementations;

import org.romanzhula.data_common.objects.MailDataJpaDataModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.romanzhula.mail_dispatcher.services.ConsumerService;
import org.romanzhula.mail_dispatcher.services.MailSenderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Log4j
@RequiredArgsConstructor
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final MailSenderService mailSenderService;

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.name.registration.mail}")
    public void consumeMailRegister(MailDataJpaDataModule mailData) {
        mailSenderService.send(mailData);
    }

}
