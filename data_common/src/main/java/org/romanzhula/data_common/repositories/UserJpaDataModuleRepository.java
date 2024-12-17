package org.romanzhula.data_common.repositories;

import org.romanzhula.data_common.models.UserJpaDataModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userJpaDataModuleRepository")
public interface UserJpaDataModuleRepository extends JpaRepository<UserJpaDataModule, Long> {

    Optional<UserJpaDataModule> findByTelegramId(Long id);

    Optional<UserJpaDataModule> findById(Long id);

    Optional<UserJpaDataModule> findByEmail(String email);

}
