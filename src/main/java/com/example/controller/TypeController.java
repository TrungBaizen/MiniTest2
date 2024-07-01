package com.example.controller;

import com.example.model.Type;
import com.example.model.dto.TypeDTO;
import com.example.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
@CrossOrigin("*")
public class TypeController {
    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<List<Type>> getAll() {
        return new ResponseEntity<>(typeService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Type> create(@Validated @RequestBody Type type, BindingResult bindingResult) {
        return new ResponseEntity<>(typeService.save(type, bindingResult), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Type> update(@Validated @PathVariable Long id, @RequestBody Type type, BindingResult bindingResult) {
        return new ResponseEntity<>(typeService.update(id, type, bindingResult), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Type> delete(@PathVariable Long id) {
        return new ResponseEntity<>(typeService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> findById(@PathVariable Long id) {
        return new ResponseEntity<>(typeService.findById(id).get(),HttpStatus.OK);
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<TypeDTO>> getStatistic() {
        return new ResponseEntity<>(typeService.findQuantityInTypeByIdType(), HttpStatus.OK);
    }
}
