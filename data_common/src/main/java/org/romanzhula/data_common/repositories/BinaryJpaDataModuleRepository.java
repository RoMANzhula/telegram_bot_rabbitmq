package org.romanzhula.data_common.repositories;


import org.romanzhula.data_common.models.BinaryJpaDataModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("binaryJpaDataModuleRepository")
public interface BinaryJpaDataModuleRepository extends JpaRepository<BinaryJpaDataModule, Long> {

}
