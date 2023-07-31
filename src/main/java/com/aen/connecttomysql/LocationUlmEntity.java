package com.aen.connecttomysql;

import jakarta.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "location_ulm")
public class LocationUlmEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "membre_id")
    private MembersEntity membre_id;

    @ManyToOne
    @JoinColumn(name = "ulm_id")
    private UlmEntity ulm_id;

    @Column(name="date_location", nullable = false)
    private Date date_location;

    @Column(name="heure_debut", nullable = false)
    private Time heure_debut;

    @Column(name="heure_fin", nullable = false)
    private Time heure_fin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MembersEntity getMembre_id() {
        return membre_id;
    }

    public void setMembre_id(MembersEntity membre_id) {
        this.membre_id = membre_id;
    }

    public UlmEntity getUlm_id() {
        return ulm_id;
    }

    public void setUlm_id(UlmEntity ulm_id) {
        this.ulm_id = ulm_id;
    }

    public Date getDate_location() {
        return date_location;
    }

    public void setDate_location(Date date_location) {
        this.date_location = date_location;
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
}
