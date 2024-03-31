package com.example.accessingdatamysql;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Random;

@Entity // This tells Hibernate to make a table out of this class
public class WarehouseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String warehouseName;
    private String street;
    private String city;
    private String country;
    private String plz;

    @OneToMany(mappedBy = "warehouse")
    private ArrayList<ProductEntity> products;


    public ArrayList<ProductEntity> getProductData() {
        return this.products;
    }

    public void addProductData(ProductEntity product) {
        this.products.add(product);
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }


    public String getStreet() {
        return this.street;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getPlz() {
        return this.plz;
    }


    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }


    /**
     * Methoden
     */
    @Override
    public String toString() {

        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductEntity> getProducts() {
        return products;
    }
}