package com.example.gestionstock.controller;

import com.example.gestionstock.model.Magasin;
import com.example.gestionstock.model.Produit;
import com.example.gestionstock.model.Sortie;
import com.example.gestionstock.model.Unite;
import com.example.gestionstock.repository.*;
import com.example.gestionstock.service.SortieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@Getter
@Setter
public class SortieController {

    private final MagasinRepository magasinRepository;
    private final ProduitRepository produitRepository;
    private final EntreeRepository entreeRepository;
    private final MouvementRepository mouvementRepository;
    private final SortieRepository sortieRepository;
    private final UniteRepository uniteRepository;
    private final UniteEquivalenceRepository uniteEquivalenceRepository;
    private final HistoriqueRepository historiqueRepository;

    public SortieController(MagasinRepository magasinRepository, ProduitRepository produitRepository, EntreeRepository entreeRepository, MouvementRepository mouvementRepository, SortieRepository sortieRepository, UniteRepository uniteRepository, UniteEquivalenceRepository uniteEquivalenceRepository, HistoriqueRepository historiqueRepository) {
        this.magasinRepository = magasinRepository;
        this.produitRepository = produitRepository;
        this.entreeRepository = entreeRepository;
        this.mouvementRepository = mouvementRepository;
        this.sortieRepository = sortieRepository;
        this.uniteRepository = uniteRepository;
        this.uniteEquivalenceRepository = uniteEquivalenceRepository;
        this.historiqueRepository = historiqueRepository;
    }

    @GetMapping("/sortie")
    public String insert(Model model)
    {
        List<Produit> produits=produitRepository.findAll();
        List<Magasin> magasins=magasinRepository.findAll();
        List<Unite> unites=uniteRepository.findAll();
        model.addAttribute("produits",produits);
        model.addAttribute("magasins",magasins);
        model.addAttribute("unites",unites);
        System.out.println(produits.size()+" isa");
        return "stock/sortie";
    }

    @PostMapping("/sortie")
    public String sortie(Model model, HttpServletRequest request)
    {
        SortieService sortieService=new SortieService(magasinRepository,produitRepository,entreeRepository,mouvementRepository,sortieRepository,uniteRepository,uniteEquivalenceRepository,historiqueRepository);
        Sortie sortie=new Sortie();
        try {
            sortie.setDateSortie(request.getParameter("date"));
            sortie.setMagasin(magasinRepository,Long.parseLong(request.getParameter("magasin")));
            sortie.setProduit(produitRepository,Long.parseLong(request.getParameter("produit")));
            sortie.setQuantite(Double.parseDouble(request.getParameter("quantite")));
            sortie.setUnite(sortieService,request.getParameter("unite"));
            sortie.sortir(sortieService);
        }catch (RuntimeException e)
        {
            model.addAttribute("error",e.getMessage());

            return "stock/error";
        }
        return "redirect:/sortie";

    }
    @GetMapping("/list")
    public String get_sorties(Model model)
    {
        List<Sortie> sortieList=sortieRepository.findByEtat(0);
        model.addAttribute("sortieList",sortieList);
        return "stock/validation";
    }

    @GetMapping("/valider/{id}")
    public String valider(Model model,@PathVariable Long id)
    {
        SortieService sortieService=new SortieService(magasinRepository,produitRepository,entreeRepository,mouvementRepository,sortieRepository,uniteRepository,uniteEquivalenceRepository,historiqueRepository);
        Sortie sortie=sortieRepository.findById(id).get();
        try {
            sortie.valider(sortieService);
        }catch (RuntimeException e)
        {
            model.addAttribute("error",e.getMessage());
            return "stock/error";
        }
        return "stock/validation";
    }
}
