package com.example.gestionstock.model;

import ch.qos.logback.core.sift.AppenderFactoryUsingSiftModel;
import com.example.gestionstock.service.SortieService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "entree")
public class Entree {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;


    @Column(name = "prix_unitaire")
    private Double prix_unitaire;

    @ManyToOne
    @JoinColumn(name = "unite_id")
    private Unite unite;

    @Column(name = "quantite")
    private Double quantite;

    @ManyToOne
    @JoinColumn(name = "magasin_id")
    private Magasin magasin;

    @Column(name = "date_entree")
    private LocalDateTime date_entree;


    public void enregistrer(SortieService sortieService)
    {
        sortieService.getEntreeRepository().save(this);
        Historique historique=new Historique();
        historique.setDate(this.getDate_entree());
        historique.setType("entree");
        historique.setUnite(this.getUnite());
        historique.setStock(this.getQuantite());
        historique.setProduit(this.getProduit());
        sortieService.getHistoriqueRepository().save(historique);
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }


    public double get_quantite_unitaire(SortieService sortieService)
    {
        return this.getQuantite()*this.getUnite().get_valeur_unitaire(sortieService,this.getProduit());
    }

    public double getReste(SortieService sortieService,LocalDateTime date_actuel)
    {
        List<Mouvement> mouvements=sortieService.getMouvementRepository().findByEntreeAndSortie_DateSortieBefore(this,date_actuel);
        double reste=this.get_quantite_unitaire(sortieService);
        for (Mouvement mouvement:mouvements)
        {
            reste-=mouvement.getQuantite();
        }
        return reste;
    }


    public static List<Entree> get_entree_par_type(SortieService sortieService,Sortie sortie)
    {
        List<Entree> entrees=new ArrayList<>();
        if(sortie.getProduit().getTypeSortie().equals("FIFO"))
        {
            entrees=sortieService.getEntreeRepository().find_entree_fifo(sortie.getProduit(),sortie.getMagasin(),sortie.getDateSortie());
        }
        else
        {
            entrees=sortieService.getEntreeRepository().find_entree_lifo(sortie.getProduit(),sortie.getMagasin(),sortie.getDateSortie());
        }
        return entrees;
    }




    //getter and setter


    public void setUnite(SortieService sortieService,String unite_id)
    {
        Long id1=Long.parseLong(unite_id);
        Unite unite=sortieService.getUniteRepository().findById(id1).get();
        if(unite.getId()!=1)
        {
            if(unite==null)
            {
                throw new RuntimeException("unite inexistant");
            }
            if(!unite.has_equivalence(sortieService,this.getProduit()))
            {
                throw  new RuntimeException("unite indisponible pour produit");
            }
        }


        this.setUnite(unite);
    }

    public void setDate_entree(SortieService sortieService,String date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime=null;
        try {
            dateTime = LocalDateTime.parse(date, formatter);
        }catch (RuntimeException e)
        {
            throw new RuntimeException("date_invalide");
        }

        Sortie sortie=this.getMagasin().get_last_Sortie(sortieService,this.produit);
        if(sortie!=null)
        {
            int diff=sortie.getDateSortie().compareTo(dateTime);
            if(diff>0)
            {
                //sortie later than this
                throw new RuntimeException("date invalide,periode fermee");
            }
        }
        setDate_entree(dateTime);
    }
    public Long getId() {
        return id;
    }

    public Produit getProduit() {
        return produit;
    }

    public Double getPrix_unitaire() {
        return prix_unitaire;
    }

    public Double getQuantite() {
        return quantite;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public LocalDateTime getDate_entree() {
        return date_entree;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public void setPrix_unitaire(Double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public void setQuantite(Double quantite) {
        if(quantite<0)
        {
            new RuntimeException("quantite negative invalide");
        }
        this.quantite = quantite;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public void setDate_entree(LocalDateTime date_entree) {
        this.date_entree = date_entree;
    }


}