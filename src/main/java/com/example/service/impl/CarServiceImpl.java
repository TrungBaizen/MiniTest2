package com.example.service.impl;

import com.example.controller.ExceptionController;
import com.example.model.Car;
import com.example.repository.CarRepository;
import com.example.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final EntityManager entityManager;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, @Qualifier("entityManager") EntityManager entityManager) {
        this.carRepository = carRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Car> findByName(String name) {
        if (carRepository.findByNameContaining(name).isEmpty()) {
            throw new IllegalArgumentException();
        }
        return carRepository.findByNameContaining(name);
    }

    @Override
    public Car save(Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = ExceptionController.getMessageError(bindingResult);
            if (car.getProducer().getId() == null) {
                errors.add("producer: Không được để trống");
            }
            if (car.getType().getId() == null) {
                errors.add("type: Không được để trống");
            }
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
        List<String> errors = ExceptionController.getMessageError(bindingResult);
        if (carRepository.existsCarByName(car.getName())) {
            errors.add("name: Tên đã tồn tại");
        }
        if (carRepository.existsCarByFrameCode(car.getFrameCode())) {
            errors.add("frameCode: Mã khung đã tồn tại");
        }
        if (carRepository.existsCarByMachineCode(car.getMachineCode())) {
            errors.add("machineCode: Mã máy tồn tại");
        }
        if (errors.size() > 0) {
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }

        return carRepository.save(car);
    }

    @Override
    public Car delete(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);
        carRepository.deleteById(id);
        return carOptional.get();
    }

    @Override
    public Car update(Long id, Car car, BindingResult bindingResult) {
        Optional<Car> carOptional = findById(id);
        if (bindingResult.hasErrors()) {
            List<String> errors = ExceptionController.getMessageError(bindingResult);
            if (car.getProducer().getId() == null) {
                errors.add("producer: Không được để trống");
            }
            if (car.getType().getId() == null) {
                errors.add("type: Không được để trống");
            }
            if (errors.size() > 0) {
                throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
            }
        }
        List<String> errors = ExceptionController.getMessageError(bindingResult);
        if (carRepository.existsCarByName(car.getName()) && !carOptional.get().getName().equals(car.getName())) {
            errors.add("name: Tên đã tồn tại");
        }
        if (carRepository.existsCarByFrameCode(car.getFrameCode()) && !carOptional.get().getFrameCode().equals(car.getFrameCode())) {
            errors.add("frameCode: Mã khung đã tồn tại");
        }
        if (carRepository.existsCarByMachineCode(car.getMachineCode()) && !carOptional.get().getMachineCode().equals(car.getMachineCode())) {
            errors.add("machineCode: Mã máy tồn tại");
        }
        if (errors.size() > 0) {
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
        car.setId(id);
        return carRepository.save(car);
    }

    @Override
    public List<Car> findAll() {
        if (carRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException();
        }
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            return carRepository.findById(id);
        }
        throw new IllegalArgumentException();
    }
}
