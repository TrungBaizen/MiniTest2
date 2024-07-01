package com.example.controller;

import com.example.model.Producer;
import com.example.model.dto.ProducerDTO;
import com.example.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producers")
@CrossOrigin("*")
public class ProducerController {
    private final ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    public ResponseEntity<List<Producer>> getAll() {
        List<Producer> producers =  producerService.findAll();
        if (producers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(producers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Producer> create(@Validated @RequestBody Producer producer, BindingResult bindingResult) {
        return new ResponseEntity<>(producerService.save(producer , bindingResult), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producer> update(@Validated @PathVariable Long id, @RequestBody Producer producer, BindingResult bindingResult) {
        Producer existingProducer = producerService.findById(id).orElse(null);
        if (existingProducer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        producer.setId(id);
        return new ResponseEntity<>(producerService.update(id,producer,bindingResult), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Producer> delete(@PathVariable Long id) {
        Optional<Producer> producer = producerService.findById(id);
        if (producer.isPresent()) {
            return new ResponseEntity<>(producerService.delete(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<ProducerDTO>> getStatistic() {
        List<ProducerDTO> producerDTOList = producerService.findQuantityInProducerByIdProducer();
        if (producerDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(producerDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producer> findById(@PathVariable Long id) {
        return new ResponseEntity<>(producerService.findById(id).get(),HttpStatus.OK);
    }
}
