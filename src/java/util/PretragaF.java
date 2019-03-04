/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bean.Festival;
import bean.Izvodjaci;
import bean.Korisnik;
import bean.Registracije;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Rayker
 */
@ManagedBean
@SessionScoped
public class PretragaF implements Serializable{

    private String naziv;
    private Date datumOd;
    private String mesto;
    private String izvodjac;
    private List<Festival> lista;
    private List<Registracije> listaReg;
    private List<Izvodjaci> listaIzv;
    PretragaHelper helper;

    public PretragaF() {
        helper = new PretragaHelper();
        
    }

    public List<Festival> getLista() {
        return lista;
    }

    public void setLista(List<Festival> lista) {
        this.lista = lista;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getIzvodjac() {
        return izvodjac;
    }

    public void setIzvodjac(String izvodjac) {
        this.izvodjac = izvodjac;
    }

    public String pretrazi() {
        if (mesto.equals("") && naziv.equals("") && datumOd == null && izvodjac.equals("")) {
            addErrorField(null);
            return "neregistrovaniKorisnik";

        }
        lista = helper.listaFestivala(mesto, naziv, sqlDate(datumOd), izvodjac);
        if (lista.isEmpty()) {
            addErrorFestival(null);
            return "neregistrovaniKorisnik";
        } else {
            return "rezultatPretrage";
        }

    }

    public List<Festival> adminTop5() {

        lista = helper.listaFestivalaTop5();
        return lista;
    }

    public String top5() {

        lista = helper.listaFestivalaTop5();
        return "rezultatPretrage";
    }
    
    public List<Festival> getListaSvih() {
        return lista = helper.listaSvihF();
    }
    
    public String nazad() {

        return "neregistrovaniKorisnik";
    }

    public java.sql.Date sqlDate(java.util.Date datumOd) {
        if (datumOd != null) {
            return new java.sql.Date(datumOd.getTime());
        } else {
            return null;
        }
    }
    
    
    public String brisiFes(Festival f) throws MessagingException {
        List<Korisnik> kor=helper.listaKorisnikaFest(f);
        helper.brisanje(f);
        helper.otkaziKarte(f);
        for (Korisnik k : kor) {
           Email.Send("vinarija.rajic", "sanskompromis", k.getEmail(), "Automatska poruka: Otkazivanje festivala",
                "Obavestavamo Vas da je festival: " +f.getNaziv()+" nazalost otkazan! Ukoliko ste kupili ulaznice, mozete doci na blagajnu po svoj novac.");
        }
        addSuccesBrisanje(null);
        return "admin";
    }
    public String onRowEdit(RowEditEvent event) {
        Festival fest = (Festival) event.getObject();
        helper.promenaPodatakaFes(fest);
        return "admin";
    }

    public String onRowCancel(RowEditEvent event) {
        return "admin";
    }
    
    
    public void addErrorFestival(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska pretrage:", "Ne postoji festival sa unetim podacima!"));
    }
    
    public void addSuccesBrisanje(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh:", "Uspesno otkazan festival!"));
    }
    
    public void addErrorField(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska pretrage:", "Morate popuniti bar jedno polje!"));
    }

    public void addErrorDatum(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska pretrage:", "Ne postoji festival sa unetim datumom!"));
    }
}
