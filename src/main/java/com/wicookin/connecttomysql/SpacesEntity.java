package com.wicookin.connecttomysql;

import jakarta.persistence.*;

@Entity
@Table(name = "spaces")
public class SpacesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressesEntity address_id;

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

    public AddressesEntity getAddress_id() {
        return address_id;
    }

    public void setAddress_id(AddressesEntity address_id) {
        this.address_id = address_id;
    }
}
