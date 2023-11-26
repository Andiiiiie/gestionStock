package com.example.gestionstock.model_metier;

import com.example.gestionstock.model.Magasin;
import com.example.gestionstock.model.Produit;
import com.example.gestionstock.service.SortieService;

import java.time.LocalDateTime;

public class Stock {
    Produit produit;
    Magasin magasin;

    double quantite_initial;

    double reste;

    double prix;

    double montant;


    public Stock() {
    }


    public static Stock get_stock(SortieService sortieService, Produit produit, Magasin magasin, LocalDateTime date1, LocalDateTime date2)
    {
        Stock stock=new Stock();
        stock.setProduit(produit);
        stock.setMagasin(magasin);
        stock.setQuantite_initial(magasin.get_quantite(sortieService,produit,date1));
        stock.setReste(magasin.get_quantite(sortieService,produit,date2));
        stock.setMontant(magasin.get_prix(sortieService,produit,date2));
        if(stock.getMontant()==0 || stock.getReste()==0)
        {
            stock.setPrix(0);
        }
        else
        {
            stock.setPrix(stock.getMontant()/stock.getReste());
        }

        return stock;
    }



    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public double getQuantite_initial() {
        return quantite_initial;
    }

    public void setQuantite_initial(double quantite_initial) {
        this.quantite_initial = quantite_initial;
    }

    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
