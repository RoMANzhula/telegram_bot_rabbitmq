package org.romanzhula.management.repositories;


import org.romanzhula.management.models.MessageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDataRepository extends JpaRepository<MessageData, Long> {

}
