package com.example.service;

import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface IService<T> {
    T save(T t , BindingResult bindingResult);
    T delete(Long id);
    T update(Long id,T t,BindingResult bindingResult);
    List<T> findAll();
    Optional<T> findById(Long id);
}
