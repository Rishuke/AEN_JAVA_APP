package com.aen.connecttomysql;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "planification")
public class PlanificationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="date", nullable = false)
    private Date date;

    @Column(name="heure", nullable = false)
    private Time heure;

    @ManyToOne
    @JoinColumn(name = "activite_id")
    private ActivitiesEntity activite_id;

    @ManyToOne
    @JoinColumn(name = "avion_id")
    private AvionEntity avion_id;
    @ManyToOne
    @JoinColumn(name = "ulm_id")
    private UlmEntity ulm_id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private MembersEntity client_id;

    @ManyToOne
    @JoinColumn(name = "pilote_id")
    private MembersEntity pilote_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public ActivitiesEntity getActivite_id() {
        return activite_id;
    }

    public void setActivite_id(ActivitiesEntity activite_id) {
        this.activite_id = activite_id;
    }

    public AvionEntity getAvion_id() {
        return avion_id;
    }

    public void setAvion_id(AvionEntity avion_id) {
        this.avion_id = avion_id;
    }

    public UlmEntity getUlm_id() {
        return ulm_id;
    }

    public void setUlm_id(UlmEntity ulm_id) {
        this.ulm_id = ulm_id;
    }

    public MembersEntity getClient_id() {
        return client_id;
    }

    public void setClient_id(MembersEntity client_id) {
        this.client_id = client_id;
    }

    public MembersEntity getPilote_id() {
        return pilote_id;
    }

    public void setPilote_id(MembersEntity pilote_id) {
        this.pilote_id = pilote_id;
    }
}
