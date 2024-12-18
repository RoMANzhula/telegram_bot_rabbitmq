package org.romanzhula.mail_dispatcher.services;

import org.romanzhula.data_common.objects.MailDataJpaDataModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public interface ConsumerService {

    @RabbitListener(queues = "${rabbitmq.queue.name.registration.mail}")
    void consumeMailRegister(MailDataJpaDataModule mailData);

}
