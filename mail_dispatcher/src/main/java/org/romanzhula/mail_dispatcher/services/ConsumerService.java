package org.romanzhula.mail_dispatcher.services;

import org.romanzhula.data_jpa.objects.MailDataJpaDataModule;

public interface ConsumerService {

    void consumeMailRegister(MailDataJpaDataModule mailData);

}
