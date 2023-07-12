package com.wicookin.connecttomysql;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

public class ProductsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="description", nullable=false)
    private String description;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal price;

    @Column(name="quantity")
    private int quantity;

    @Column(name="category", nullable=false)
    private String category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}
