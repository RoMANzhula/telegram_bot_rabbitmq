package org.romanzhula.data_common.repositories;

import org.romanzhula.data_common.models.DocumentJpaDataModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("documentJpaDataModuleRepository")
public interface DocumentJpaDataModuleRepository extends JpaRepository<DocumentJpaDataModule, Long> {

}
