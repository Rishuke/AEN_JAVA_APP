package com.wicookin.connecttomysql;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class RoomsEntiy {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="capacity")
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private SpacesEntity space_id;

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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public SpacesEntity getSpace_id() {
        return space_id;
    }

    public void setSpace_id(SpacesEntity space_id) {
        this.space_id = space_id;
    }
}
