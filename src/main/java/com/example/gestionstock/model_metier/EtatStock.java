package com.example.gestionstock.model_metier;

import com.example.gestionstock.model.Magasin;
import com.example.gestionstock.model.Produit;
import com.example.gestionstock.service.SortieService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class EtatStock {
    List<Stock> stocks;
    Double total;

    LocalDateTime dateDebut;
    LocalDateTime dateFin;

    public EtatStock() {
    }

    public static boolean verification_dates(LocalDateTime date1,LocalDateTime date2)
    {
        if(date1==null || date2==null)
        {
            throw new RuntimeException("date null");
        }
        int diff=date1.compareTo(date2);
        if(diff>0)
        {
            //sortie later than this
            throw new RuntimeException("date invalide ");
        }
        return true;
    }

    public static EtatStock get_etat_stock(SortieService sortieService, String date1, String date2, String produit, String magasin)
    {
        //verification_dates(date1,date2);
        List<Produit> produitList=sortieService.getProduitRepository().findProduitsByCode(produit);
        List<Magasin> magasinsList=sortieService.getMagasinRepository().findProduitsByName(magasin);
        EtatStock etatStock=new EtatStock();
        etatStock.setDateDebut(date1);
        etatStock.setDateFin(date2);
        etatStock.setStocks(etatStock.generate_stock(sortieService,produitList,magasinsList,etatStock.dateDebut,etatStock.dateFin));
        etatStock.setTotal(etatStock.cacul_total());
        return etatStock;
    }

    public double cacul_total()
    {
        double total=0;
        for (Stock stock:getStocks())
        {
            total+=stock.getMontant();
        }
        return total;
    }


    public List<Stock> generate_stock(SortieService sortieService,List<Produit> produitList,List<Magasin> magasinList,LocalDateTime dateDebut,LocalDateTime dateFin)
    {
        List<Stock> stockList=new ArrayList<>();
        for (Magasin magasin:magasinList)
        {
            for (Produit produit:produitList)
            {
                Stock stock=Stock.get_stock(sortieService,produit,magasin,dateDebut,dateFin);
                stockList.add(stock);
            }
        }
        return stockList;
    }


    //getter and setter

    public void setDateDebut(String date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            setDateDebut(dateTime);
        }catch (RuntimeException e)
        {
            throw new RuntimeException("date_invalide");
        }

    }
    public void setDateFin(String dateFin)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateFin, formatter);
            verification_dates(getDateDebut(),dateTime);
            setDateFin(dateTime);
        }catch (RuntimeException e)
        {
            throw new RuntimeException("date_invalide");
        }
    }
    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        if(stocks.size()==0)
        {
            throw  new RuntimeException("produit ou magasin inexxistant");
        }
        this.stocks = stocks;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }
}
