package com.example.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WarehouseRepository extends CrudRepository<WarehouseEntity, Integer> {
    // Funktionen zur Erweiterung des Repositories
    List<WarehouseEntity> findByID(int id);
}
