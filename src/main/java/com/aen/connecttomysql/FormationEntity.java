package com.aen.connecttomysql;

import jakarta.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "formation")
public class FormationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nom", nullable=false)
    private String nom;
    @Column(name="date_debut", nullable = false)
    private Date date_debut;

    @Column(name="date_fin", nullable = false)
    private Date date_fin;

    @Column(name="heure_debut", nullable = false)
    private Time heure_debut;

    @Column(name="heure_fin", nullable = false)
    private Time heure_fin;

    @Column(name="prix_formation", nullable = false)
    private float prix_formation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public Time getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(Time heure_debut) {
        this.heure_debut = heure_debut;
    }

    public Time getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(Time heure_fin) {
        this.heure_fin = heure_fin;
    }

    public float getPrix_formation() {
        return prix_formation;
    }

    public void setPrix_formation(float prix_formation) {
        this.prix_formation = prix_formation;
    }
}
