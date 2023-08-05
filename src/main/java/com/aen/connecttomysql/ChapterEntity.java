package com.aen.connecttomysql;

import jakarta.persistence.*;


@Entity
@Table(name = "chapitre")
public class ChapterEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="titre", nullable=false)
    private String titre;

    @Column(name="contenu", nullable=false)
    private String contenu;

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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public FormationEntity getFormation_id() {
        return formation_id;
    }

    public void setFormation_id(FormationEntity formation_id) {
        this.formation_id = formation_id;
    }
}
