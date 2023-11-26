package com.example.gestionstock.model;

import com.example.gestionstock.service.SortieService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "unite_equivalence")
public class UniteEquivalence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unite_2_id")
    private Unite unite2;

    @ManyToOne
    @JoinColumn(name = "unite_1_id")
    private Unite unite1;

    @Column(name = "valeur")
    private Double valeur;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;



    public static UniteEquivalence get_equivalence(SortieService sortieService,Unite u1 ,Unite u2,Produit produit)
    {
        UniteEquivalence uniteEquivalence=sortieService.getUniteEquivalenceRepository().findByUnite1AndUnite2AndProduit(u1,u2,produit);
        return uniteEquivalence;
    }


    //getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unite getUnite2() {
        return unite2;
    }

    public void setUnite2(Unite unite2) {
        this.unite2 = unite2;
    }

    public Unite getUnite1() {
        return unite1;
    }

    public void setUnite1(Unite unite1) {
        this.unite1 = unite1;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}