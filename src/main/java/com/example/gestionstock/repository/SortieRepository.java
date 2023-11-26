package com.example.gestionstock.repository;

import com.example.gestionstock.model.Magasin;
import com.example.gestionstock.model.Produit;
import com.example.gestionstock.model.Sortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SortieRepository extends JpaRepository<Sortie, Long> {
    @Query("select s from Sortie s where s.etat = ?1")
    List<Sortie> findByEtat(Integer etat);
//    @Query("select s from Sortie s where s.produit = ?1 and s.magasin = ?2 and s.dateSortie < ?3")
//    List<Sortie> findByProduitAndMagasinAndDateSortieBefore(Produit produit,
//                                                            Magasin magasin, LocalDateTime date_sortie);

    @Query("select s from Sortie s where s.produit = ?1 and s.magasin = ?2 and s.etat = ?3 order by s.dateSortie DESC")
    Sortie findByProduitAndMagasinAndEtatOrderByDateSortieDesc(Produit produit, Magasin magasin, Integer etat);


}