package com.example.service.impl;

import com.example.model.Producer;
import com.example.model.dto.ProducerDTO;
import com.example.repository.ProducerRepository;
import com.example.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProducerServiceImpl implements ProducerService {
    private final ProducerRepository producerRepository;

    @Autowired
    public ProducerServiceImpl(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @Override
    public Producer update(Long id, Producer producer,BindingResult bindingResult) {
        Optional<Producer> producerOptional = producerRepository.findById(id);
        if (producerOptional.isPresent()) {
            producer.setId(id);
            return producerRepository.save(producer);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Producer save(Producer producer , BindingResult bindingResult) {
        return producerRepository.save(producer);
    }

    @Override
    public Producer delete(Long id) {
        Optional<Producer> producerOptional = findById(id);
        if (producerOptional.isPresent()) {
            producerRepository.deleteProducerById(id);
            return producerOptional.get();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<Producer> findAll() {
        if (producerRepository.findAll().isEmpty()) {
            throw new IllegalStateException();
        }
        return producerRepository.findAll();
    }

    @Override
    public Optional<Producer> findById(Long id) {
        return producerRepository.findById(id);
    }

    @Override
    public List<ProducerDTO> findQuantityInProducerByIdProducer() {
        if (producerRepository.findQuantityInProducerByIdProducer().isEmpty()){
            throw new IllegalArgumentException();
        }
        return producerRepository.findQuantityInProducerByIdProducer();
    }
}
