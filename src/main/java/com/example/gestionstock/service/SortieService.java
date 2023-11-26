package com.example.gestionstock.service;

import com.example.gestionstock.model.*;
import com.example.gestionstock.model.Produit;
import com.example.gestionstock.repository.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Getter
@Setter
@Service
public class SortieService {
    private final MagasinRepository magasinRepository;
    private final ProduitRepository produitRepository;
    private final EntreeRepository entreeRepository;
    private final MouvementRepository mouvementRepository;
    private final SortieRepository sortieRepository;
    private final UniteRepository uniteRepository;
    private final UniteEquivalenceRepository uniteEquivalenceRepository;
    private final HistoriqueRepository historiqueRepository;

    public SortieService(MagasinRepository magasinRepository, ProduitRepository produitRepository, EntreeRepository entreeRepository, MouvementRepository mouvementRepository, SortieRepository sortieRepository, UniteRepository uniteRepository, UniteEquivalenceRepository uniteEquivalenceRepository, HistoriqueRepository historiqueRepository) {
        this.magasinRepository = magasinRepository;
        this.produitRepository = produitRepository;
        this.entreeRepository = entreeRepository;
        this.mouvementRepository = mouvementRepository;
        this.sortieRepository = sortieRepository;
        this.uniteRepository = uniteRepository;
        this.uniteEquivalenceRepository = uniteEquivalenceRepository;
        this.historiqueRepository = historiqueRepository;
    }
    /*public Double stock_dispo(Produit produit,Magasin magasin)
    {
        this.produit = produit;
        //total reste anle entre etree rehetra tao
        List<Entree> list=entreeRepository.findByProduit_IdAndMagasin_Id(produit.getId(),magasin.getId());
        Double total=0.0;
        for (Entree entree:list)
        {
            //total+=entree.getReste();
        }
        return total;
    }



    public boolean est_suffisant(Produit produit,Magasin magasin,double quantite_ilaina)
    {
        if(stock_dispo(produit,magasin)<quantite_ilaina)
        {
            return false;
        }
        return true;
    }

    public List<Entree> get_List_Entree(Produit produit,Magasin magasin)
    {
        List<Entree> sources=null;
        if(produit.getTypeSortie().equals("LIFO"))
        {
             sources=entreeRepository.findByProduit_IdAndMagasin_IdAndResteGreaterThanOrderByDate_entreeAsc(produit.getId(), magasin.getId(),0.0);
        }
        else {
            sources=entreeRepository.findByProduit_IdAndMagasin_IdAndResteGreaterThanOrderByDate_entreeDesc(produit.getId(), magasin.getId(),0.0);
        }
        return sources;
    }

    public List<Mouvement> mamindra(Produit produit, Magasin magasin, Sortie sortie)
    {
        List<Mouvement> list=new ArrayList<>();
        Double quantite_alaina=sortie.getQuantite();
        List<Entree> sources=get_List_Entree(produit,magasin);
        Double total_vola=0.0;
        for (Entree entree:sources) {
            if (quantite_alaina > 0) {
                double nalaina = 0;
                if (quantite_alaina > entree.getReste()) {
                    nalaina = entree.getReste();
                } else {
                    nalaina = quantite_alaina;
                }
                entree.setReste(entree.getReste() - nalaina);
                entreeRepository.save(entree);
                quantite_alaina -= nalaina;
                Mouvement mouvement = new Mouvement();
                mouvement.setEntree(entree);
                mouvement.setQuantite(nalaina);
                mouvement.setSortie(sortie);
                mouvementRepository.save(mouvement);
                list.add(mouvement);
                total_vola+=nalaina*entree.getPrix_unitaire();
            }
        }
        sortie.setMontant(total_vola);
        sortieRepository.save(sortie);
        return list;
    }

    public void sortir(LocalDateTime date, String produit, String magasin, String quantite)
    {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate date_sortie=LocalDate.parse(date,formatter);
        long id_magasin=Long.parseLong(magasin);
        Optional<Magasin> magasin1=magasinRepository.findById(id_magasin);
        Magasin m=magasin1.get();
        long id_produit=Long.parseLong(produit);
        Optional<Produit> produit1=produitRepository.findById(id_produit);
        Produit p=produit1.get();
        double quantite1=Double.parseDouble(quantite);

        if(est_suffisant(p,m,quantite1))
        {
            //getListMouvement
            Sortie sortie=new Sortie();
            sortie.setDateSortie(date);
            sortie.setMagasin(m);
            sortie.setProduit(p);
            sortie.setQuantite(quantite1);
            sortieRepository.save(sortie);
            List<Mouvement> mouvements=mamindra(p,m,sortie);

        }
        else {
            throw  new RuntimeException("quantite de stock insuffisante");
        }
    }*/

}
