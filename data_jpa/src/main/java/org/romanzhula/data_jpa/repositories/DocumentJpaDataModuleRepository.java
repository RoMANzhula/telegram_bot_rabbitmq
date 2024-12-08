package org.romanzhula.data_jpa.repositories;

import org.romanzhula.data_jpa.models.DocumentJpaDataModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("documentJpaDataModuleRepository")
public interface DocumentJpaDataModuleRepository extends JpaRepository<DocumentJpaDataModule, Long> {

}
