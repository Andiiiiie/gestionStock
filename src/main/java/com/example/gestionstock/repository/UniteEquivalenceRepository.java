package com.example.gestionstock.repository;

import com.example.gestionstock.model.Produit;
import com.example.gestionstock.model.Unite;
import com.example.gestionstock.model.UniteEquivalence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UniteEquivalenceRepository extends JpaRepository<UniteEquivalence, Long> {
    @Query("select u from UniteEquivalence u where u.produit = ?1 and u.unite1 = ?2")
    UniteEquivalence findByProduitAndUnite1(Produit produit, Unite unite1);

    @Query("select u from UniteEquivalence u where u.unite1 = ?1 and u.unite2 = ?2 and u.produit = ?3")
    UniteEquivalence findByUnite1AndUnite2AndProduit(Unite unite1, Unite unite2, Produit produit);

    @Query("select u from UniteEquivalence u where u.unite2 = ?1 and u.produit = ?2")
    UniteEquivalence findByUnite2AndProduit(Unite unite2, Produit produit);

    @Query("select u from UniteEquivalence u where u.unite2.id = ?1 and u.produit.id = ?2")
    UniteEquivalence findByUnite2_IdAndProduit_Id(Long id, Long id1);

    @Query("select u from UniteEquivalence u where u.unite1 = ?1 and u.produit = ?2")
    List<UniteEquivalence> findByUnite1AndProduit(Unite unite1, Produit produit);
}