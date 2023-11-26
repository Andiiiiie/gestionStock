package com.example.gestionstock.model;

import com.example.gestionstock.repository.MagasinRepository;
import com.example.gestionstock.repository.ProduitRepository;
import com.example.gestionstock.service.SortieService;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sortie")
public class Sortie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "magasin_id")
    private Magasin magasin;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "dateSortie")
    private LocalDateTime dateSortie;

    @Column(name = "quantite")
    private Double quantite;

    @Column(name = "etat")
    private Integer etat;




    public void sortir(SortieService sortieService)
    {
        //verification
        //verifier si mi-existe ilay produit

        //verifier si mi-existe ilay magasin

        //insertion
        this.setEtat(0);
        sortieService.getSortieRepository().save(this);

    }


    public double get_quantite_unitaire(SortieService sortieService)
    {
        return getQuantite()*this.getUnite().get_valeur_unitaire(sortieService,this.getProduit());
    }

    @Transactional
    public void valider(SortieService sortieService)
    {
        //verifier date
        Sortie sortie=this.getMagasin().get_last_Sortie(sortieService,this.produit);
        if(sortie!=null)
        {
            int diff=sortie.getDateSortie().compareTo(this.getDateSortie());
            if(diff>0)
            {
                //sortie later than this
                throw new RuntimeException("date invalide,periode fermee");
            }
        }

        //verifier stock
        if(this.magasin.stockSuffisant(sortieService,this.produit,this.dateSortie,this.quantite)==false)
        {
            throw new RuntimeException("stock insuffisant");
        }
        //decomposer
        List<Mouvement> mouvements=decomposer(sortieService);

        //enregistrer les mouvements
        this.setEtat(10);
        sortieService.getSortieRepository().save(this);
        double vola=0;
        for (Mouvement mouvement:mouvements)
        {
            vola+=mouvement.getEntree().getPrix_unitaire()*mouvement.getQuantite();
            sortieService.getMouvementRepository().save(mouvement);
        }
        this.setMontant(vola);
        this.setDateValidation(LocalDateTime.now());
        sortieService.getSortieRepository().save(this);
        Historique historique=new Historique();
        historique.setDate(this.getDateSortie());
        historique.setType("sortie");
        historique.setUnite(this.getUnite());
        historique.setStock(this.getQuantite());
        historique.setProduit(this.getProduit());
        sortieService.getHistoriqueRepository().save(historique);
    }


    @Column(name = "date_validation")
    private LocalDateTime dateValidation;


    @ManyToOne
    @JoinColumn(name = "unite_id")
    private Unite unite;

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }




    public List<Mouvement> decomposer(SortieService sortieService)
    {
        List<Entree> entrees=Entree.get_entree_par_type(sortieService,this);
        List<Mouvement> mouvements=new ArrayList<>();
        double alaina=this.get_quantite_unitaire(sortieService);
        int ind=0;
        while (alaina>0 && ind<entrees.size())
        {
            Entree tempEntre=entrees.get(ind);
            double reste_entre=tempEntre.getReste(sortieService,this.dateSortie);
            double miala=alaina;
            if(alaina>reste_entre)
            {
                alaina-=reste_entre;
                miala=reste_entre;
            }
            else if(alaina<=reste_entre)
            {
                miala=alaina;
                alaina=0;
            }
            if(miala>0)
            {
                Mouvement mouvement=new Mouvement();
                mouvement.setEntree(entrees.get(ind));
                //double miala1=miala/mouvement.getEntree().get_quantite_unitaire(sortieService);
                mouvement.setQuantite(miala);
                mouvement.setSortie(this);
                mouvements.add(mouvement);
            }
            ind++;
        }
        return mouvements;
    }



    //getter and setter


    public void setUnite(SortieService sortieService,String unite_id)
    {
        Long id1=Long.parseLong(unite_id);
        Unite unite=sortieService.getUniteRepository().findById(id1).get();
        if(unite==null)
        {
            throw new RuntimeException("unite inexistant");
        }
        if(unite.getId()!=1)
        {
            if(!unite.has_equivalence(sortieService,this.getProduit()))
            {
                throw  new RuntimeException("unite indisponible pour produit");
            }
        }


        this.setUnite(unite);
    }


    public void setMagasin(MagasinRepository magasin,Long id)
    {
        if(!magasin.existsById(id))
        {
            throw  new RuntimeException("magasin non existant");
        }
        setMagasin(magasin.findById(id).get());
    }
    public void setProduit(ProduitRepository produitRepository,Long id)
    {
        if(!produitRepository.existsById(id))
        {
            throw new RuntimeException("produit non existant");
        }
        setProduit(produitRepository.findById(id).get());
    }

    public void setDateSortie(String date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            setDateSortie(dateTime);
        }catch (RuntimeException e)
        {
            throw new RuntimeException("date_invalide");
        }
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public void setDateSortie(LocalDateTime dateSortie) {
        this.dateSortie = dateSortie;
    }

    public void setQuantite(Double quantite) {
        if(quantite<0)
        {
            throw new RuntimeException("quantite invalide(negative)");
        }
        this.quantite = quantite;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Long getId() {
        return id;
    }

    public Produit getProduit() {
        return produit;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public Double getMontant() {
        return montant;
    }

    public LocalDateTime getDateSortie() {
        return dateSortie;
    }

    public Double getQuantite() {
        return quantite;
    }

    public Integer getEtat() {
        return etat;
    }

    public LocalDateTime getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }
}