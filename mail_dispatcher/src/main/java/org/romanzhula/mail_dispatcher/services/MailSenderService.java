package org.romanzhula.mail_dispatcher.services;

import org.romanzhula.data_common.objects.MailDataJpaDataModule;

public interface MailSenderService {

    void send(MailDataJpaDataModule mailData);

}
