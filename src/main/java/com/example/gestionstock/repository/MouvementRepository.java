package com.example.gestionstock.repository;

import com.example.gestionstock.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MouvementRepository extends JpaRepository<Mouvement, Long> {
    @Query("select m from Mouvement m where m.entree = ?1 and m.sortie.dateSortie <= ?2")
    List<Mouvement> findByEntreeAndSortie_DateSortieBefore(Entree entree, LocalDateTime dateSortie);






}