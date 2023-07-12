package com.wicookin.connecttomysql;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

public class OrdersEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MembersEntity member_id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductsEntity product_id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date order_date;

    @Column(name="quantity")
    private int quantity;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal total_amount;

    @Column(name="status", nullable=false)
    private String status;

    @Column(name="delivery_adress", nullable=false)
    private String delivery_adress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MembersEntity getMember_id() {
        return member_id;
    }

    public void setMember_id(MembersEntity member_id) {
        this.member_id = member_id;
    }

    public ProductsEntity getProduct_id() {
        return product_id;
    }

    public void setProduct_id(ProductsEntity product_id) {
        this.product_id = product_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery_adress() {
        return delivery_adress;
    }

    public void setDelivery_adress(String delivery_adress) {
        this.delivery_adress = delivery_adress;
    }
}
