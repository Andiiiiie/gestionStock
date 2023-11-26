package com.example.gestionstock.repository;

import com.example.gestionstock.model.Entree;
import com.example.gestionstock.model.Magasin;
import com.example.gestionstock.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EntreeRepository extends JpaRepository<Entree, Long> {

//    @Query("select e from Entree e where e.reste between ?1 and ?2")
//    List<Entree> test(Double resteStart, Double resteEnd);

//    @Query("select e from Entree e where e.produit.id = ?1 and e.magasin.id = ?2")
//    List<Entree> findByProduit_IdAndMagasin_Id(Long id, Long id1);
//
//    @Query("select e from Entree e where e.produit.id = ?1 and e.magasin.id = ?2 and e.reste > ?3 order by e.date_entree")
//    List<Entree> findByProduit_IdAndMagasin_IdAndResteGreaterThanOrderByDate_entreeAsc(Long id, Long id1, Double reste);

//    @Query("""
//            select e from Entree e
//            where e.produit.id = ?1 and e.magasin.id = ?2 and e.reste > ?3
//            order by e.date_entree DESC""")
//    List<Entree> findByProduit_IdAndMagasin_IdAndResteGreaterThanOrderByDate_entreeDesc(Long id, Long id1, Double reste);
//
//    @Query("SELECT DISTINCT(e.produit)  FROM Entree e where e.magasin.id=?1 and e.date_entree between ?2 and ?3")
//    List<Object> getDistinctProduit(long idMagasin, LocalDateTime date1, LocalDateTime date2);
//
//    @Query("select e from Entree e where e.produit.id = ?1 and e.magasin.id = ?2 and e.date_entree between ?3 and ?4")
//    List<Entree> findByProduit_IdAndMagasin_IdAndDate_entreeBetween(Long id, Long id1, LocalDate date_entreeStart, LocalDate date_entreeEnd);

    @Query("select e from Entree e where e.produit.id = ?1 and e.magasin.id = ?2 and e.date_entree <= ?3")
    List<Entree> findByProduit_IdAndMagasin_IdAndDate_entreeBefore(Long id, Long id1, LocalDateTime date_entree);


    @Query("select e from Entree e where e.produit = ?1 and e.magasin = ?2 and e.date_entree < ?3 order by e.date_entree")
    List<Entree> find_entree_fifo(Produit produit, Magasin magasin, LocalDateTime date_entree);

    @Query("""
            select e from Entree e
            where e.produit = ?1 and e.magasin = ?2 and e.date_entree < ?3
            order by e.date_entree DESC""")
    List<Entree> find_entree_lifo(Produit produit, Magasin magasin, LocalDateTime date_entree);

    

    
}