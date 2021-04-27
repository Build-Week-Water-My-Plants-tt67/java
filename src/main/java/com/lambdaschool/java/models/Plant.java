package com.lambdaschool.java.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name= "plants")
public class Plant extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long plantid;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String species;

    private String H2ofrequency;

    public Plant() {
    }

    public Plant(String nickname, String species, String h2ofrequency) {
        this.nickname = nickname;
        this.species = species;
        H2ofrequency = h2ofrequency;
    }
//    @ManyToOne
//    @JoinColumn(name="userid", nullable = false)
//    @JsonIgnoreProperties(value ="plant",allowSetters = true)
//    private User user;

    public long getPlantid() {
        return plantid;
    }

    public void setPlantid(long plantid) {
        this.plantid = plantid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getH2ofrequency() {
        return H2ofrequency;
    }

    public void setH2ofrequency(String h2ofrequency) {
        H2ofrequency = h2ofrequency;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
