package com.example.repository;

import com.example.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findByNameContaining(String name);
    boolean existsByFrameCode(String frameCode);
    boolean existsByMachineCode(String machineCode);
    boolean existsByName(String name);
}
