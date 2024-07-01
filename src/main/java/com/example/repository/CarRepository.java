package com.example.repository;

import com.example.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findByNameContaining(String name);
    boolean existsCarByName(String name);
    boolean existsCarByFrameCode(String frameCode);
    boolean existsCarByMachineCode(String machineCode);
}
