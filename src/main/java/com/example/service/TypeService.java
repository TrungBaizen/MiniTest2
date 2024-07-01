package com.example.service;

import com.example.model.Type;
import com.example.model.dto.ProducerDTO;
import com.example.model.dto.TypeDTO;

import java.util.List;

public interface TypeService extends IService<Type>{
    List<TypeDTO> findQuantityInTypeByIdType();
}
