package com.example.gestionstock.repository;

import com.example.gestionstock.model.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MagasinRepository extends JpaRepository<Magasin, Long> {
    @Query("SELECT m FROM Magasin m WHERE m.nom LIKE %:code%")
    List<Magasin> findProduitsByName(@Param("code") String code);
}