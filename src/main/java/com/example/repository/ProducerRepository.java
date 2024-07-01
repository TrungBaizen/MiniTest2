package com.example.repository;


import com.example.model.Producer;
import com.example.model.dto.ProducerDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerRepository extends JpaRepository<Producer,Long> {
    @Query(value = "select producer.name as name , count(car.producer_id) as quantity from producer left join car on producer.id = car.producer_id group by producer.id",nativeQuery = true)
    List<ProducerDTO> findQuantityInProducerByIdProducer();
    boolean existsProducerByName(String name);
    @Modifying
    @Query(value = "CALL delete_producer(:id);",nativeQuery = true)
    void deleteProducerById(@Param("id") Long id);
}
