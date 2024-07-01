package com.example.service.impl;

import com.example.controller.ExceptionController;
import com.example.model.Producer;
import com.example.model.dto.ProducerDTO;
import com.example.repository.ProducerRepository;
import com.example.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProducerServiceImpl implements ProducerService {
    private final ProducerRepository producerRepository;

    @Autowired
    public ProducerServiceImpl(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @Override
    public Producer update(Long id, Producer producer, BindingResult bindingResult) {
        Optional<Producer> producerOptional = producerRepository.findById(id);
        if (bindingResult.hasErrors()) {
            List<String> errors = ExceptionController.getMessageError(bindingResult);
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
        List<String> errors = ExceptionController.getMessageError(bindingResult);
        if (producerRepository.existsProducerByName(producer.getName()) && !producerOptional.get().getName().equals(producer.getName())) {
            errors.add("name: Tên đã tồn tại");
        }
        if (errors.size() > 0) {
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
        producer.setId(id);
        return producerRepository.save(producer);
    }

    @Override
    public Producer save(Producer producer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = ExceptionController.getMessageError(bindingResult);
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
        List<String> errors = ExceptionController.getMessageError(bindingResult);
        if (producerRepository.existsProducerByName(producer.getName())) {
            errors.add("name: Tên đã tồn tại");
        }
        if (errors.size() > 0) {
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
        return producerRepository.save(producer);
    }

    @Override
    public Producer delete(Long id) {
        Optional<Producer> producerOptional = findById(id);
        producerRepository.deleteProducerById(id);
        return producerOptional.get();
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
        Optional<Producer> producerOptional = producerRepository.findById(id);
        if (producerOptional.isPresent()) {
            return producerRepository.findById(id);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<ProducerDTO> findQuantityInProducerByIdProducer() {
        if (producerRepository.findQuantityInProducerByIdProducer().isEmpty()) {
            throw new IllegalArgumentException();
        }
        return producerRepository.findQuantityInProducerByIdProducer();
    }
}
