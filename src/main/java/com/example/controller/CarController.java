package com.example.controller;

import com.example.model.Car;
import com.example.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/cars")
@CrossOrigin("*")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("")
    public ResponseEntity<List<Car>> findAll() {
        return new ResponseEntity<>(carService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Car> create(@Validated @RequestBody Car car, BindingResult bindingResult) {
        return new ResponseEntity<>(carService.save(car, bindingResult), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> update(@Valid @PathVariable Long id, @Validated @RequestBody Car car, BindingResult bindingResult) {
        return new ResponseEntity<>(carService.update(id, car, bindingResult), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> delete(@PathVariable Long id) {
        return new ResponseEntity<>(carService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Car>> findByName(@RequestParam String name) {
        return new ResponseEntity<>(carService.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable Long id) {
        return new ResponseEntity<>(carService.findById(id).get(), HttpStatus.OK);
    }

}
