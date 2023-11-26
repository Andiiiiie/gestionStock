package com.example.gestionstock.model;

import com.example.gestionstock.service.SortieService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "magasin")
public class Magasin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nom")
    private String nom;


    public double get_quantite(SortieService sortieService, Produit produit, LocalDateTime temps)
    {
        //mijery quantite ana produit iray @ daty iray
        List<Entree> entreesList=sortieService.getEntreeRepository().findByProduit_IdAndMagasin_IdAndDate_entreeBefore(produit.getId(),this.getId(),temps);
        double total=0;
        for (Entree entree:entreesList)
        {
            total+=entree.getReste(sortieService,temps);
        }
        //mamerina isa
        return total;
    }

    public double get_prix(SortieService sortieService, Produit produit, LocalDateTime temps)
    {
        //mijery quantite ana produit iray @ daty iray
        List<Entree> entreesList=sortieService.getEntreeRepository().findByProduit_IdAndMagasin_IdAndDate_entreeBefore(produit.getId(),this.getId(),temps);
        double total=0;
        for (Entree entree:entreesList)
        {
            total+=(entree.getReste(sortieService,temps)/entree.getUnite().get_valeur_unitaire(sortieService,entree.getProduit()))*entree.getPrix_unitaire();
        }
        return total;
    }

    public Boolean stockSuffisant(SortieService sortieService,Produit produit,LocalDateTime temps,double ilaina)
    {
       if(get_quantite(sortieService,produit,temps)<ilaina)
       {
           return false;
       }
        return true;
    }

    public Sortie get_last_Sortie(SortieService sortieService,Produit produit)
    {
        Sortie sortie=sortieService.getSortieRepository().findByProduitAndMagasinAndEtatOrderByDateSortieDesc(produit,this,10);

        return sortie;
    }







    //getter and setter


    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}