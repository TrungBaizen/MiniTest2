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
        try {
            Car carSave = carRepository.save(car);
            entityManager.flush();
            return carSave;
        } catch (DataIntegrityViolationException ex) {
            List<String> errors = ExceptionController.getMessageError(bindingResult);
            if (carRepository.existsByName(car.getName())) {
                errors.add("name: Tên xe đã tồn tại");
            }
            if (carRepository.existsByFrameCode(car.getFrameCode())) {
                errors.add("frameCode: Mã khung xe đã tồn tại");
            }
            if (carRepository.existsByMachineCode(car.getMachineCode())) {
                errors.add("machineCode: Mã máy xe đã tồn tại");
            }
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
}

@Override
public Car delete(Long id) {
    Optional<Car> carOptional = carRepository.findById(id);
    carRepository.deleteById(id);
    return carOptional.get();
}

@Override
public Car update(Long id, Car car, BindingResult bindingResult) {
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
    findById(id);
    car.setId(id);
    try {
        Car updateCar = carRepository.save(car);
        entityManager.flush();
        return updateCar;
    } catch (PersistenceException ex) {
        throw new IllegalArgumentException("Không thể cập nhật xe đã tồn tại");
    }

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
