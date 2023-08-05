package com.aen.connecttomysql;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "membre")
public class MembersEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nom", nullable=false)
    private String nom;

    @Column(name="prenom", nullable=false)
    private String prenom;

    @Column(name="date_naissance", nullable = false)
    private Date date_naissance;

    @Column(name="adresse", nullable=false)
    private String adresse;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name="cotisation", nullable=false)
    private Boolean cotisation;

    @Column(name="ffa_adhesion", nullable=false)
    private Boolean ffa_adhesion;

    @Column(name="date_adhesion", nullable = false)
    private Date date_adhesion;

    @Column(name="date_renouvellement", nullable = false)
    private Date date_renouvellement;

    @Column(name="type", nullable=false)
    private String type;

    @Column(name="telephone")
    private String telephone;

    @Column(name="genre", nullable = false)
    private String genre;

    @Column(name="password")
    private String password;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getCotisation() {
        return cotisation;
    }

    public void setCotisation(Boolean cotisation) {
        this.cotisation = cotisation;
    }

    public Boolean getFfa_adhesion() {
        return ffa_adhesion;
    }

    public void setFfa_adhesion(Boolean ffa_adhesion) {
        this.ffa_adhesion = ffa_adhesion;
    }

    public Date getDate_adhesion() {
        return date_adhesion;
    }

    public void setDate_adhesion(Date date_adhesion) {
        this.date_adhesion = date_adhesion;
    }

    public Date getDate_renouvellement() {
        return date_renouvellement;
    }

    public void setDate_renouvellement(Date date_renouvellement) {
        this.date_renouvellement = date_renouvellement;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
