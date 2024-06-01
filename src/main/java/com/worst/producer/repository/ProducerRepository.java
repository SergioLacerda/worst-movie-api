package com.worst.producer.repository;

import com.worst.producer.domain.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerEntity, Long> {
}
