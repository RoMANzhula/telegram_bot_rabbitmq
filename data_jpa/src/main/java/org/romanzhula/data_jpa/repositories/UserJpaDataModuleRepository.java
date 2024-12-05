package org.romanzhula.data_jpa.repositories;

import org.romanzhula.data_jpa.models.UserJpaDataModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaDataModuleRepository extends JpaRepository<UserJpaDataModule, Long> {

    UserJpaDataModule findByTelegramId(Long id);

}
