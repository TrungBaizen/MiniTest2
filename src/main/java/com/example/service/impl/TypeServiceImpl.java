package com.example.service.impl;

import com.example.model.Type;
import com.example.model.dto.TypeDTO;
import com.example.repository.TypeRepository;
import com.example.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
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
        if (typeOptional.isPresent()) {
            type.setId(id);
            return typeRepository.save(type);
        }
        throw new IllegalArgumentException();
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
        return typeRepository.findById(id);
    }

    @Override
    public List<TypeDTO> findQuantityInTypeByIdType() {
        if (typeRepository.findQuantityInTypeByIdType().isEmpty()){
            throw new IllegalStateException();
        }
        return typeRepository.findQuantityInTypeByIdType();
    }
}
