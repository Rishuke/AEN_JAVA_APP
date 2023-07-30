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
    @JoinColumn(name = "ulm_id")
    private UlmEntity ulm_id;

}
