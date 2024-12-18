package org.romanzhula.management.services;


import org.romanzhula.data_common.models.UserJpaDataModule;

public interface MailUserService {

    String setUserEmail(UserJpaDataModule userJpaDataModule, String email);

    String registerUser(UserJpaDataModule userJpaDataModule);

}
