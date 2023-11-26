package com.example.gestionstock.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sortie")
public class Sortie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entree_id")
    private Entree entree;

    @Column(name = "quantite")
    private Double quantite;

    @ManyToOne
    @JoinColumn(name = "sortie_mere_id")
    private SortieMere sortieMere;

}