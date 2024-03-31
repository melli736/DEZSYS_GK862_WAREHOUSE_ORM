package com.example.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

public interface WarehouseRepository extends CrudRepository<WarehouseEntity, Integer> {
}
