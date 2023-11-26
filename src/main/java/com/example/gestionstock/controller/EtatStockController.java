package com.example.gestionstock.controller;

import com.example.gestionstock.model.Magasin;
import com.example.gestionstock.model_metier.EtatStock;
import com.example.gestionstock.repository.*;
import com.example.gestionstock.service.SortieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Controller
public class EtatStockController {
    private final MagasinRepository magasinRepository;
    private final EntreeRepository entreeRepository;
    private final SortieRepository sortieRepository;
    private final MouvementRepository mouvementRepository;
    private final ProduitRepository produitRepository;
    private final UniteRepository uniteRepository;
    private final UniteEquivalenceRepository uniteEquivalenceRepository;
    private final HistoriqueRepository historiqueRepository;

    public EtatStockController(MagasinRepository magasinRepository, EntreeRepository entreeRepository, SortieRepository sortieRepository, MouvementRepository mouvementRepository, ProduitRepository produitRepository, UniteRepository uniteRepository, UniteEquivalenceRepository uniteEquivalenceRepository, HistoriqueRepository historiqueRepository) {
        this.magasinRepository = magasinRepository;
        this.entreeRepository = entreeRepository;
        this.sortieRepository = sortieRepository;
        this.mouvementRepository = mouvementRepository;
        this.produitRepository = produitRepository;
        this.uniteRepository = uniteRepository;
        this.uniteEquivalenceRepository = uniteEquivalenceRepository;
        this.historiqueRepository = historiqueRepository;
    }

    @GetMapping("/")
    public String etat_stock(Model model)
    {
        /*List<Magasin> magasins=magasinRepository.findAll();

        model.addAttribute("magasins",magasins);*/
        return "stock/etat";
    }

    @PostMapping("/etat_stock")
    public String get_etat_stock(Model model, HttpServletRequest request)

    {
        SortieService sortieService=new SortieService(magasinRepository,produitRepository,entreeRepository,mouvementRepository,sortieRepository,uniteRepository,uniteEquivalenceRepository,historiqueRepository);
        try
        {
            EtatStock etatStock=EtatStock.get_etat_stock(sortieService,request.getParameter("date1"),request.getParameter("date2"),request.getParameter("produit"),request.getParameter("magasin"));
            model.addAttribute("etatStock",etatStock);
            return "stock/etat";
        }catch (RuntimeException e)
        {
            model.addAttribute("error",e.getMessage());
            return "stock/error";
        }
    }
}
