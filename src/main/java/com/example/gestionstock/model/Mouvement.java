package com.example.gestionstock.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mouvement")
public class Mouvement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entree_id")
    private Entree entree;

    @Column(name = "quantite")
    private Double quantite;

    @ManyToOne
    @JoinColumn(name = "sortie_id")
    private Sortie sortie;




    //getter and setter
    public Long getId() {
        return id;
    }

    public Entree getEntree() {
        return entree;
    }

    public Double getQuantite() {
        return quantite;
    }

    public Sortie getSortie() {
        return sortie;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEntree(Entree entree) {
        this.entree = entree;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public void setSortie(Sortie sortie) {
        this.sortie = sortie;
    }
}