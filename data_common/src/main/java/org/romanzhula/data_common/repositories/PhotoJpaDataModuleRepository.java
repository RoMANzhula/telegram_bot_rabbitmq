package org.romanzhula.data_common.repositories;

import org.romanzhula.data_common.models.PhotoJpaDataModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("photoJpaDataModuleRepository")
public interface PhotoJpaDataModuleRepository extends JpaRepository<PhotoJpaDataModule, Long> {

}
