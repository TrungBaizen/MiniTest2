package com.example.service.impl;

import com.example.controller.ExceptionController;
import com.example.model.Producer;
import com.example.model.Type;
import com.example.model.dto.TypeDTO;
import com.example.repository.TypeRepository;
import com.example.service.TypeService;
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
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }
    @Override
    public Type save(Type type , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = ExceptionController.getMessageError(bindingResult);
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
        List<String> errors = ExceptionController.getMessageError(bindingResult);
        if (typeRepository.existsTypeByName(type.getName())) {
            errors.add("name: Tên đã tồn tại");
        }
        if (errors.size()>0){
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
        return typeRepository.save(type);
    }

    @Override
    public Type delete(Long id) {
        Optional<Type> type = typeRepository.findById(id);
        if (type.isPresent()) {
            typeRepository.deleteTypeById(id);
            return type.get();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Type update(Long id, Type type,BindingResult bindingResult) {
        Optional<Type> typeOptional = typeRepository.findById(id);
        if (bindingResult.hasErrors()) {
            List<String> errors = ExceptionController.getMessageError(bindingResult);
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
        List<String> errors = ExceptionController.getMessageError(bindingResult);
        if (typeRepository.existsTypeByName(type.getName()) && !typeOptional.get().getName().equals(type.getName())) {
            errors.add("name: Tên đã tồn tại");
        }
        if (errors.size()>0){
            throw new ValidationException(errors.stream().collect(Collectors.joining("; ")));
        }
        type.setId(id);
        return typeRepository.save(type);
    }

    @Override
    public List<Type> findAll() {
        if (typeRepository.findAll().isEmpty()){
            throw new IllegalStateException();
        }
        return typeRepository.findAll();
    }

    @Override
    public Optional<Type> findById(Long id) {
        Optional<Type> typeOptional = typeRepository.findById(id);
        if (typeOptional.isPresent()) {
            return typeOptional;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<TypeDTO> findQuantityInTypeByIdType() {
        if (typeRepository.findQuantityInTypeByIdType().isEmpty()){
            throw new IllegalStateException();
        }
        return typeRepository.findQuantityInTypeByIdType();
    }
}
