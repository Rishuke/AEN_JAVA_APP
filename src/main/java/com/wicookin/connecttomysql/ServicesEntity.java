package com.wicookin.connecttomysql;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "services")
public class ServicesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoriesEntity category_id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MembersEntity member_id;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal price;

    @Column(name="description")
    private String description;

    @Column(name="name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoriesEntity getCategory_id() {
        return category_id;
    }

    public void setCategory_id(CategoriesEntity category_id) {
        this.category_id = category_id;
    }

    public MembersEntity getMember_id() {
        return member_id;
    }

    public void setMember_id(MembersEntity member_id) {
        this.member_id = member_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
