package com.example.gestionstock.controller;

import com.example.gestionstock.model.*;
import com.example.gestionstock.repository.*;
import com.example.gestionstock.service.SortieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Getter
@Setter
@Controller
public class EntreeController {
    private final MagasinRepository magasinRepository;
    private final ProduitRepository produitRepository;
    private final EntreeRepository entreeRepository;
    private final MouvementRepository mouvementRepository;
    private final SortieRepository sortieRepository;
    private final UniteRepository uniteRepository;
    private final UniteEquivalenceRepository uniteEquivalenceRepository;
    private final HistoriqueRepository historiqueRepository;

    public EntreeController(MagasinRepository magasinRepository, ProduitRepository produitRepository, EntreeRepository entreeRepository, MouvementRepository mouvementRepository, SortieRepository sortieRepository, UniteRepository uniteRepository, UniteEquivalenceRepository uniteEquivalenceRepository,
                            HistoriqueRepository historiqueRepository) {
        this.magasinRepository = magasinRepository;
        this.produitRepository = produitRepository;
        this.entreeRepository = entreeRepository;
        this.mouvementRepository = mouvementRepository;
        this.sortieRepository = sortieRepository;
        this.uniteRepository = uniteRepository;
        this.uniteEquivalenceRepository = uniteEquivalenceRepository;
        this.historiqueRepository = historiqueRepository;
    }

    @GetMapping("/entree")
    public String entree(Model model)
    {
        List<Produit> produits=produitRepository.findAll();
        List<Magasin> magasins=magasinRepository.findAll();
        List<Unite> unites=uniteRepository.findAll();
        model.addAttribute("produits",produits);
        model.addAttribute("magasins",magasins);
        model.addAttribute("unites",unites);
        return "stock/entree";
    }

    @PostMapping("/entree")
    public String inserer(Model model, HttpServletRequest request)
    {
        SortieService sortieService=new SortieService(magasinRepository,produitRepository,entreeRepository,mouvementRepository,sortieRepository,uniteRepository,uniteEquivalenceRepository,historiqueRepository);
        Entree entree=new Entree();
        try {
            entree.setMagasin(magasinRepository.findById(Long.parseLong(request.getParameter("magasin"))).get());
            entree.setProduit(produitRepository.findById(Long.parseLong(request.getParameter("produit"))).get());
            entree.setUnite(sortieService,request.getParameter("unite"));
            entree.setQuantite(Double.parseDouble(request.getParameter("quantite")));
            entree.setPrix_unitaire(Double.parseDouble(request.getParameter("prix")));
            entree.setDate_entree(sortieService,request.getParameter("date"));
            entree.enregistrer(sortieService);
        } catch (RuntimeException e)
        {
            model.addAttribute("error",e.getMessage());
            return "stock/error";
        }
        return "redirect:/entree";
    }

    @GetMapping("/historique")
    public String get_historique(Model model)
    {
        List<Historique> historiques=historiqueRepository.findAll();
        model.addAttribute("historiques",historiques);
        return "stock/list";
    }
}
