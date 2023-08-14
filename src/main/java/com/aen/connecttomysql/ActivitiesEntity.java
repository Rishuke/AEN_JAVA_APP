package com.aen.connecttomysql;


import jakarta.persistence.*;

@Entity
@Table(name = "activite")
public class ActivitiesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nom_activite", nullable = false)
    private String nom_activite;

    @Column(name="prix_activite", nullable = false)
    private float prix_activite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_activite() {
        return nom_activite;
    }

    public void setNom_activite(String nom_activite) {
        this.nom_activite = nom_activite;
    }

    public float getPrix_activite() {
        return prix_activite;
    }

    public void setPrix_activite(float prix_activite) {
        this.prix_activite = prix_activite;
    }
}
