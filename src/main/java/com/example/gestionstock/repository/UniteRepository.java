package com.example.gestionstock.repository;

import com.example.gestionstock.model.Produit;
import com.example.gestionstock.model.Unite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UniteRepository extends JpaRepository<Unite, Long> {

}