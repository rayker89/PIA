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
import bean.Rezervacije;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Rayker
 */
@ManagedBean
@SessionScoped
public class KorisnikKontrola implements Serializable{

    private boolean pocetna = true;
    private boolean rezervisanje = false;
    private boolean mojeulaznice = false;
    private boolean detalji = false;
    private boolean formaZaRez = false;
    private boolean rezultatPretrage = false;
    private boolean izvodjaci=false;
    private boolean galerija=false;
    private String naziv;
    private Date datumOd;
    private String mesto;
    private String izvodjac;
    private List<Registracije> listaReg;
    private List<Izvodjaci> listaIzv;
    private List<Festival> listaFes;
    private List<Rezervacije> listaRezervacija;
    private Date kalendarDan;
    Festival festival;
    KorisnikHelper helper;
    PretragaHelper helper1;
    LoginHelper helper2;
    private int brUlaz;
    private int brPaketa;

    public KorisnikKontrola() {
        helper = new KorisnikHelper();
        helper1 = new PretragaHelper();
        helper2=new LoginHelper();
    }

    public Date getKalendarDan() {
        return kalendarDan;
    }

    public void setKalendarDan(Date kalendarDan) {
        this.kalendarDan = kalendarDan;
    }

    public List<Rezervacije> getListaRezervacija() {
        return listaRezervacija;
    }

    public void setListaRezervacija(List<Rezervacije> listaRezervacija) {
        this.listaRezervacija = listaRezervacija;
    }

    public int getBrUlaz() {
        return brUlaz;
    }

    public void setBrUlaz(int brUlaz) {
        this.brUlaz = brUlaz;
    }

    public int getBrPaketa() {
        return brPaketa;
    }

    public void setBrPaketa(int brPaketa) {
        this.brPaketa = brPaketa;
    }

    public List<Registracije> getListaReg() {
        return listaReg;
    }

    public void setListaReg(List<Registracije> listaReg) {
        this.listaReg = listaReg;
    }

    public List<Izvodjaci> getListaIzv() {
        return listaIzv;
    }

    public void setListaIzv(List<Izvodjaci> listaIzv) {
        this.listaIzv = listaIzv;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public boolean isIzvodjaci() {
        return izvodjaci;
    }

    public void setIzvodjaci(boolean izvodjaci) {
        this.izvodjaci = izvodjaci;
    }

    public boolean isGalerija() {
        return galerija;
    }

    public void setGalerija(boolean galerija) {
        this.galerija = galerija;
    }

    public boolean isRezultatPretrage() {
        return rezultatPretrage;
    }

    public void setRezultatPretrage(boolean rezultatPretrage) {
        this.rezultatPretrage = rezultatPretrage;
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

    public boolean isRezervisanje() {
        return rezervisanje;
    }

    public void setRezervisanje(boolean rezervisanje) {
        this.rezervisanje = rezervisanje;
    }

    public boolean isMojeulaznice() {
        return mojeulaznice;
    }

    public void setMojeulaznice(boolean mojeulaznice) {
        this.mojeulaznice = mojeulaznice;
    }

    public boolean isDetalji() {
        return detalji;
    }

    public void setDetalji(boolean detalji) {
        this.detalji = detalji;
    }

    public boolean isFormaZaRez() {
        return formaZaRez;
    }

    public void setFormaZaRez(boolean formaZaRez) {
        this.formaZaRez = formaZaRez;
    }

    public List<Festival> getListaFes() {
        return listaFes;
    }

    public void setListaFes(List<Festival> listaFes) {
        this.listaFes = listaFes;
    }

    public boolean isPocetna() {
        return pocetna;
    }

    public void setPocetna(boolean pocetna) {
        this.pocetna = pocetna;
    }

    public List<Festival> listaNajskorijih() {

        listaFes = helper.listaFestNajskorije();
        return listaFes;
    }

    public String pocetnaStr() {
        pocetna = true;
        rezervisanje = false;
        mojeulaznice = false;
        detalji = false;
        formaZaRez = false;
        rezultatPretrage = false;
        izvodjaci=false;
        galerija=false;
        return "registrovaniKorisnik";
    }
    public String LogOut() {
        pocetna = true;
        rezervisanje = false;
        mojeulaznice = false;
        detalji = false;
        formaZaRez = false;
        rezultatPretrage = false;
        izvodjaci=false;
        galerija=false;
        Korisnik korisnik = Logovanje.kor;
        helper2.izloguj(korisnik.getKorisnickoIme());
        helper.getFactory().close();
        helper1.getFactory().close();
        helper2.getFactory().close();

        return "index";
    }
    
    public String top5() {

        listaFes = helper1.listaFestivalaTop5();
        return rezultatPretr();
    }
    
    public String rezultatPretr(){
        pocetna = false;
        rezervisanje = false;
        mojeulaznice = false;
        detalji = false;
        formaZaRez = false;
        izvodjaci=false;
        galerija=false;
        rezultatPretrage = true;
        return "registrovaniKorisnik";
    }
    public String rezervisi() {
        pocetna = false;
        rezervisanje = true;
        mojeulaznice = false;
        detalji = false;
        formaZaRez = false;
        rezultatPretrage = false;
        izvodjaci=false;
        galerija=false;
        return "registrovaniKorisnik";
    }

    public String mojeRezervacije() {
        pocetna = false;
        rezervisanje = false;
        mojeulaznice = true;
        detalji = false;
        formaZaRez = false;
        rezultatPretrage = false;
        izvodjaci=false;
        galerija=false;
        Korisnik korisnik = Logovanje.kor;
        listaRezervacija=helper.dohvatiRezervacije(korisnik);
        return "registrovaniKorisnik";
    }
    
    public String detalji(Festival fes){
        pocetna = false;
        rezervisanje = false;
        mojeulaznice = false;
        detalji = true;
        formaZaRez = false;
        rezultatPretrage = false;
        izvodjaci=false;
        galerija=false;
        festival=fes;
        listaIzv=helper1.dohvatiIzv(festival.getNaziv());
        return "registrovaniKorisnik";
    }
    
    public void rejting(Festival fes) {
        helper.rejting(fes, fes.getOcena());
    }
    
    
    public String rezervacijaForma(Festival fes) {
        pocetna = false;
        rezervisanje = false;
        mojeulaznice = false;
        detalji = false;
        formaZaRez = true;
        izvodjaci=false;
        galerija=false;
        rezultatPretrage = false;
        festival = fes;
        listaIzv=helper1.dohvatiIzv(festival.getNaziv());
        return "registrovaniKorisnik";
    }
    
    public String rezervacijaPotvrda(Festival fes) throws ParseException{
         if (datumOd != null) {
             if(datumOd.after(fes.getVremeOd()) && datumOd.before(fes.getVremeDo())){
                if (helper1.getFestivalN(fes.getNaziv()).getMaxKarata() >= brUlaz) {
                    if (helper1.getFestivalN(fes.getNaziv()).getBrUlaznica() >= brUlaz) {
                        Integer i = helper1.KupovinaNereg(fes.getNaziv(), datumOd, brUlaz, false);
                        if (i == 0) {
                            addErrorMaxDnevno(null);
                            return rezervacijaForma(fes);
                        } else {
                            addSucces1(null);
                            helper.dodajRezervaciju(helper2.getKorisnik(),fes,datumOd,brUlaz,0,0);
                            return rezervacijaForma(fes);
                        }
                    } else {
                        addErrorBrojUlaznica(null);
                        return rezervacijaForma(fes);
                    }
                } else {
                    addErrorMaxKarata(null);
                    return rezervacijaForma(fes);
                }
             }else {
                 addErrorDan1(null);
                 return rezervacijaForma(fes);
             }
            } else {
                addErrorDan(null);
                return rezervacijaForma(fes);
            }
    }
    
   public String rezervacijaPotvrdaPaket(Festival fes) throws ParseException {
       Integer rez = helper1.KupovinaNeregPaket(fes.getNaziv(), brPaketa);
            if (rez == 1) {
                addSucces2(null);
                Date date= new Date();
                helper.dodajRezervaciju(helper2.getKorisnik(),fes,date,brPaketa,1,0);
                return rezervacijaForma(fes);
            } else {
                addErrorPaketBroj(null);
                return rezervacijaForma(fes);
            }
   }
    
    
    public String pretrazi() {
        if (mesto.equals("") && naziv.equals("") && datumOd == null && izvodjac.equals("")) {
            addErrorField(null);
            return rezervisi();

        }
        listaFes = helper1.listaFestivala(mesto, naziv, sqlDate(datumOd), izvodjac);
        if (listaFes.isEmpty()) {
            addErrorFestival(null);
            return rezervisi();
        } else {

            return rezultatPretr();
        }

    }
    
    public String pretraziPoDanu(Date dan) {
        
        listaFes = helper1.listaFestivalaDatum(dan);
        if (listaFes.isEmpty()) {
            addErrorFestival(null);
            return pocetnaStr();
        } else {

            return rezultatPretr();
        }

    }
    
    public String otkaziRezervaciju(Rezervacije rez) throws ParseException{
        if(rez.getPaket()==0 && rez.getKupljeno()==0){
        helper1.VracanjeUlaznica(rez.getFestival(), rez.getDatum(), rez.getBrojUlaznica());
        helper1.otkaziRezervisano(rez);
            addSuccesOtkaz(null);
        return mojeRezervacije();}
        else if(rez.getKupljeno()==0){
            helper1.VracanjeulaznicaPaket(rez.getFestival(),rez.getPaket());
            helper1.otkaziRezervisano(rez);
            addSuccesOtkaz(null);
            return mojeRezervacije();   
        }else {
            addErrorKupljeno(null);
           return mojeRezervacije(); 
     }
}
    

    public java.sql.Date sqlDate(java.util.Date datumOd) {
        if (datumOd != null) {
            return new java.sql.Date(datumOd.getTime());
        } else {
            return null;
        }
    }

    public void addErrorFestival(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska pretrage:", "Ne postoji festival sa unetim podacima!"));
    }

    public void addErrorField(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska pretrage:", "Morate popuniti bar jedno polje!"));
    }

    public void addErrorDatum(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska pretrage:", "Ne postoji festival sa unetim datumom!"));
    }
    
    public void addSucces(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Odobrenje:", "Uspesno ste se odobrili zahtev!!!"));
    }

    public void addSucces1(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Odobrenje:", "Uspesno ste se Rezervisali karte!!!"));
    }

    public void addSucces2(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Odobrenje:", "Uspesno ste se prodali paket(e) karata!!!"));
    }
    
    public void addSuccesOtkaz(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Odobrenje:", "Uspesno ste otkazali rezervaciju!!!"));
    }

    public void addErrorOdbijanje(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Odbijanje:", "Uspesno ste odbili zahtev!"));
    }

    public void addErrorIzmena(ActionEvent actionEvent, RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Odustali ste od promene", "Naziv festivala: " + ((Festival) event.getObject()).getNaziv()));
    }

    public void addErrorMaxDnevno(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:", "Za izabrani datum nema dovoljno karata!"));
    }
    
    public void addErrorDan(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:", "Morate uneti datum!"));
    }
    
    public void addErrorDan1(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:", "Morate uneti validan datum!"));
    }

    public void addErrorMaxKarata(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:", "Iznos kolicine karata je veci nego dozvoljeni!"));
    }

    public void addErrorBrojUlaznica(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:", "Nedovoljan ukupan broj ulaznica na festivalu!"));
    }

    public void addErrorPaketBroj(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:", "Nedovoljno ulaznica za sve dane festivala!"));
    }
    
    public void addErrorKupljeno(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:", "Odabrane ulaznice su vec kupljene!"));
    }
}
