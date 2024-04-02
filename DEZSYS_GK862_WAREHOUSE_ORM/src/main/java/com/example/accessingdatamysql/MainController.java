// MainController.java
package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/warehouse")
public class MainController {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewWarehouse (@RequestParam String warehouseName,
                                                 @RequestParam String street,
                                                 @RequestParam String city,
                                                 @RequestParam String country,
                                                 @RequestParam String plz) {
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setWarehouseName(warehouseName);
        warehouse.setStreet(street);
        warehouse.setCity(city);
        warehouse.setCountry(country);
        warehouse.setPlz(plz);
        warehouseRepository.save(warehouse);
        return "Warehouse saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<WarehouseEntity> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @PostMapping(path="/{id}/products/add")
    public @ResponseBody String addProductToWarehouse (@RequestParam String name,
                                                       @RequestParam String category,
                                                       @RequestParam String amount,
                                                       @RequestParam String unit,
                                                       @RequestParam Integer warehouseId) {
        Optional<WarehouseEntity> warehouseOptional = warehouseRepository.findById(warehouseId);
        if (warehouseOptional.isPresent()) {
            ProductEntity product = new ProductEntity();
            product.setName(name);
            product.setCategory(category);
            product.setAmount(amount);
            product.setUnit(unit);
            product.setWarehouse(warehouseOptional.get());
            productRepository.save(product);
            return "Product saved";
        } else {
            return "Warehouse not found";
        }
    }

    @GetMapping(path="/products/all")
    public @ResponseBody Iterable<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    // Angepasste Funktionalitäten gemäß den Anforderungen

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<WarehouseEntity> getWarehouseById(@PathVariable Integer id) {
        return warehouseRepository.findById(id);
    }

    @GetMapping(path="/{id}/products")
    public @ResponseBody List<ProductEntity> getAllProductsByWarehouseId(@PathVariable Integer id) {
        Optional<WarehouseEntity> warehouse = warehouseRepository.findById(id);
        if (warehouse.isPresent()) {
            return warehouse.get().getProducts();
        }
        return null;
    }

    @GetMapping(path="/{id}/products/{productId}")
    public @ResponseBody Optional<ProductEntity> getProductByIdAndWarehouseId(@PathVariable Integer id, @PathVariable Integer productId) {
        Optional<WarehouseEntity> warehouse = warehouseRepository.findById(id);
        if (warehouse.isPresent()) {
            return warehouse.get().getProducts().stream()
                    .filter(product -> product.getId().equals(productId))
                    .findFirst();
        }
        return Optional.empty();
    }

    @PutMapping(path="/{id}")
    public @ResponseBody String updateWarehouse(@PathVariable Integer id, @RequestParam String warehouseName,
                                                @RequestParam String street, @RequestParam String city,
                                                @RequestParam String country, @RequestParam String plz) {
        Optional<WarehouseEntity> warehouseOptional = warehouseRepository.findById(id);
        if (warehouseOptional.isPresent()) {
            WarehouseEntity warehouse = warehouseOptional.get();
            warehouse.setWarehouseName(warehouseName);
            warehouse.setStreet(street);
            warehouse.setCity(city);
            warehouse.setCountry(country);
            warehouse.setPlz(plz);
            warehouseRepository.save(warehouse);
            return "Warehouse updated";
        } else {
            return "Warehouse not found";
        }
    }
}
