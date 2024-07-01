package com.example.service;

import com.example.model.Producer;
import com.example.model.dto.ProducerDTO;

import java.util.List;

public interface ProducerService extends IService<Producer>{
    List<ProducerDTO> findQuantityInProducerByIdProducer();
}
