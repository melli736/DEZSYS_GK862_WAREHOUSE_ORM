package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/")
public class MainController {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(path="/warehouse/add")
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

    @GetMapping(path="/warehouse/all")
    public @ResponseBody Iterable<WarehouseEntity> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @PostMapping(path="/product/add")
    public @ResponseBody String addNewProduct (@RequestParam String name,
                                               @RequestParam String category,
                                               @RequestParam String amount,
                                               @RequestParam String unit,
                                               @RequestParam Integer warehouseId) {
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setCategory(category);
        product.setAmount(amount);
        product.setUnit(unit);

        // Sucht das Lager anhand der ID und setzt es für das Produkt
        WarehouseEntity warehouse = warehouseRepository.findById(warehouseId).orElse(null);
        if (warehouse != null) {
            product.setWarehouse(warehouse);
            productRepository.save(product);
            return "Product saved";
        } else {
            return "Warehouse not found";
        }
    }

    @GetMapping(path="/product/all")
    public @ResponseBody Iterable<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(path="/warehouse/{id}/product/all")
    public @ResponseBody Iterable<ProductEntity> getAllProductsByWarehouseId(@PathVariable Integer id) {
        WarehouseEntity warehouse = warehouseRepository.findById(id).orElse(null);
        if (warehouse == null) {
            return new ArrayList<>();
        }
        return warehouse.getProducts();
    }
}
