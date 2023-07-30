package com.aen.connecttomysql;

import com.wicookin.connecttomysql.RoomsEntiy;
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

    public String getNom_activite() {
        return nom_activite;
    }

    public void setNom_activite(String nom_activite) {
        this.nom_activite = nom_activite;
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
