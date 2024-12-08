package org.romanzhula.data_jpa.repositories;

import org.romanzhula.data_jpa.models.BinaryJpaDataModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("binaryJpaDataModuleRepository")
public interface BinaryJpaDataModuleRepository extends JpaRepository<BinaryJpaDataModule, Long> {

}
