package com.wicookin.connecttomysql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "members")
public class MembersEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="lastname", nullable=false)
    private String lastname;

    @Column(name="firstname", nullable=false)
    private String firstname;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name="phonenumber")
    private String phonenumber;

    @Column(name="gender")
    private String gender;

    @Column(name="date_of_birth")
    private Date date_of_birth;

    @Temporal(TemporalType.TIMESTAMP)
    private Date account_creation_date;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="type", nullable=false)
    private String type;

    @Column(name="profile_picture")
    private String profile_picture;

    @Column(name="authentification_token")
    private String authentification_token;

    @Column(name = "confirm")
    private boolean confirm;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Date getAccount_creation_date() {
        return account_creation_date;
    }

    public void setAccount_creation_date(Date account_creation_date) {
        this.account_creation_date = account_creation_date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getAuthentification_token() {
        return authentification_token;
    }

    public void setAuthentification_token(String authentification_token) {
        this.authentification_token = authentification_token;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
}
