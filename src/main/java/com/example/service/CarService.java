package com.example.service;

import com.example.model.Car;

import java.util.List;

public interface CarService extends IService<Car>{
    List<Car> findByName(String name);
}
