package com.aen.connecttomysql;

import jakarta.persistence.*;


@Entity
@Table(name = "adhesion")
public class AdhesionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nom_adhesion")
    private String nom_adhesion;

    @Column(name="tarif_adhesion")
    private Float tarif_adhesion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_adhesion() {
        return nom_adhesion;
    }

    public void setNom_adhesion(String nom_adhesion) {
        this.nom_adhesion = nom_adhesion;
    }

    public Float getTarif_adhesion() {
        return tarif_adhesion;
    }

    public void setTarif_adhesion(Float tarif_adhesion) {
        this.tarif_adhesion = tarif_adhesion;
    }
}
