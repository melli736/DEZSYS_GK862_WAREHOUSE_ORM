package com.example.accessingdatamysql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Random;

@Entity // This tells Hibernate to make a table out of this class
public class ProductEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;
    private String category;
    private String amount;
    private String unit;

    @ManyToOne
    private WarehouseEntity warehouse;

    public ProductEntity() {
        String[][] products = {
                {"Bio Orangensaft", "Getränk", "1L Pakung"},
                {"Bio Apfelsaft", "Getränk", "1L Pakung"},
                {"Ariel Waschmittel Color", "Waschmittel", "Packung 3Kg"},
                {"Persil Discs Color", "Waschmittel", "Packung 700g"}
        };

        // Generierung der ID als Integer
        this.id = generateRandomId();

        int rInt = new Random().nextInt(3);
        this.name = products[rInt][0];
        this.category = products[rInt][1];
        this.amount = String.valueOf(new Random().nextInt(5000));
        this.unit = products[rInt][2];
    }

    // Hilfsmethode zur Generierung einer zufälligen Produkt-ID
    private int generateRandomId() {
        return new Random().nextInt(1000000) + 1000000; // Stellt sicher, dass die ID mindestens 7-stellig ist
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }
}
