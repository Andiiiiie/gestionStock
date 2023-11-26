package com.example.gestionstock.model;

import com.example.gestionstock.service.SortieService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "unite")
public class Unite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "designation")
    private String designation;


    public boolean has_equivalence(SortieService sortieService,Produit produit)
    {
        UniteEquivalence uniteEquivalence=sortieService.getUniteEquivalenceRepository().findByProduitAndUnite1(produit,this);
        if(uniteEquivalence==null)
        {
            return false;
        }
        return true;
    }


    public double conversion(SortieService sortieService,Unite unite1,Produit produit)
    {
        return unite1.get_valeur_unitaire(sortieService,produit)/this.get_valeur_unitaire(sortieService,produit);
    }





    public double get_valeur_unitaire(SortieService sortieService,Produit produit)
    {
        if(this.id!=1)
        {
            Unite unite=sortieService.getUniteRepository().findById(Long.parseLong("1")).get();
            UniteEquivalence uniteEquivalence1=sortieService.getUniteEquivalenceRepository().findByUnite2_IdAndProduit_Id(unite.getId(),produit.getId());
            double multiplication=1;
            while (uniteEquivalence1.getUnite1().getId()!=this.getId())
            {
                multiplication*=uniteEquivalence1.getValeur();
                uniteEquivalence1=sortieService.getUniteEquivalenceRepository().findByUnite2AndProduit(uniteEquivalence1.getUnite1(),produit);
            }
            multiplication*=uniteEquivalence1.getValeur();
            return multiplication;
        }
        return 1;


    }



    /*public static Unite get_min(SortieService sortieService, Produit produit)
    {
        Unite unite=sortieService.getUniteRepository().findByProduitOrderByValeurAsc(produit);
        return unite;
    }


    public double conversion(Unite unite,double quantite)
    {
        return quantite*unite.getValeur();
    }


    public Boolean appartient(SortieService sortieService,Produit produit)
    {

        return true;
    }*/






    //getter and setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

}