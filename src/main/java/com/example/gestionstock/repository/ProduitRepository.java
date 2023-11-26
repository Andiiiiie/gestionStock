package com.example.gestionstock.repository;

import com.example.gestionstock.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query("SELECT p FROM Produit p WHERE p.code LIKE %:code%")
    List<Produit> findProduitsByCode(@Param("code") String code);
}