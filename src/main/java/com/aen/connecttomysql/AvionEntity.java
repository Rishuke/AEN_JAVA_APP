package com.aen.connecttomysql;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "avion")
public class AvionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nom", nullable=false)
    private String nom;

    @Column(name="type", nullable=false)
    private String type;

    @Column(name = "tarif_solo", nullable = false)
    private float tarif_solo;

    @Column(name = "tarif_instruction", nullable = false)
    private float tarif_instruction;

    @Column(name="utilisation", nullable=false)
    private String utilisation;
    @Column(name="date_ouverture", nullable = false)
    private Date date_ouverture;

    @Column(name="date_fermeture", nullable = false)
    private Date date_fermeture;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getTarif_solo() {
        return tarif_solo;
    }

    public void setTarif_solo(float tarif_solo) {
        this.tarif_solo = tarif_solo;
    }

    public float getTarif_instruction() {
        return tarif_instruction;
    }

    public void setTarif_instruction(float tarif_instruction) {
        this.tarif_instruction = tarif_instruction;
    }

    public String getUtilisation() {
        return utilisation;
    }

    public void setUtilisation(String utilisation) {
        this.utilisation = utilisation;
    }

    public Date getDate_ouverture() {
        return date_ouverture;
    }

    public void setDate_ouverture(Date date_ouverture) {
        this.date_ouverture = date_ouverture;
    }

    public Date getDate_fermeture() {
        return date_fermeture;
    }

    public void setDate_fermeture(Date date_fermeture) {
        this.date_fermeture = date_fermeture;
    }
}
