package com.aen.connecttomysql;

import jakarta.persistence.*;


@Entity
@Table(name = "cotisation")
public class CotisationEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nom_cotisation")
    private String nom_cotisation;

    @Column(name="tarif_cotisation")
    private Float tarif_cotisation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_cotisation() {
        return nom_cotisation;
    }

    public void setNom_cotisation(String nom_cotisation) {
        this.nom_cotisation = nom_cotisation;
    }

    public Float getTarif_cotisation() {
        return tarif_cotisation;
    }

    public void setTarif_cotisation(Float tarif_cotisation) {
        this.tarif_cotisation = tarif_cotisation;
    }
}
