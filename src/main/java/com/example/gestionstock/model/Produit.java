package com.example.gestionstock.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type_sortie")
    private String typeSortie;

    @Column(name = "designation")
    private String designation;

    @Column(name = "code")
    private String code;


    //getter and setter

    public Long getId() {
        return id;
    }

    public String getTypeSortie() {
        return typeSortie;
    }

    public String getDesignation() {
        return designation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTypeSortie(String typeSortie) {
        this.typeSortie = typeSortie;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}