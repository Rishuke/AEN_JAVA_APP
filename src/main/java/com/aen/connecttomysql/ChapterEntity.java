package com.aen.connecttomysql;

import jakarta.persistence.*;


@Entity
@Table(name = "quiz")
public class ChapterEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="titre", nullable=false)
    private String titre;

    @Column(name="score_passage", nullable=false)
    private int score_passage;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private FormationEntity formation_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getScore_passage() {
        return score_passage;
    }

    public void setScore_passage(int score_passage) {
        this.score_passage = score_passage;
    }

    public FormationEntity getFormation_id() {
        return formation_id;
    }

    public void setFormation_id(FormationEntity formation_id) {
        this.formation_id = formation_id;
    }
}
