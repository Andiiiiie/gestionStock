package com.example.gestionstock.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sortie_mere")
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

    @Column(name = "date_sortie")
    private LocalDateTime date_sortie;

    @Column(name = "quantite")
    private Double quantite;

}