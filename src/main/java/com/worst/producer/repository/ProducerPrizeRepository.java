package com.worst.producer.repository;

import com.worst.producer.domain.ProducerPrizesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerPrizeRepository extends JpaRepository<ProducerPrizesEntity, Long> {
}
