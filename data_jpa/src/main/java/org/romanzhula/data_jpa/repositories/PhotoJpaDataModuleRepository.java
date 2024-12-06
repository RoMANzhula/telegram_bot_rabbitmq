package org.romanzhula.data_jpa.repositories;

import org.romanzhula.data_jpa.models.PhotoJpaDataModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhotoJpaDataModuleRepository extends JpaRepository<PhotoJpaDataModule, Long> {

}
