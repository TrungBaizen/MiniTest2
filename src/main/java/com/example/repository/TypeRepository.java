package com.example.repository;


import com.example.model.Type;
import com.example.model.dto.TypeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type,Long> {
    @Query(value = "select type.name as name , count(car.type_id) as quantity from type left join car on type.id = car.type_id group by type.id",nativeQuery = true)
    List<TypeDTO> findQuantityInTypeByIdType();
    @Modifying
    @Query(value = "CALL delete_type(:id);",nativeQuery = true)
    void deleteTypeById(@Param("id") Long id);
}
