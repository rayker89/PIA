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
import static com.mchange.v2.c3p0.impl.C3P0Defaults.user;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Rayker
 */
@ManagedBean
@SessionScoped
public class Admin implements Serializable{

    private boolean pocetnaAdmin = true;
    private boolean dodajFestival = false;
    private boolean promeniPostojeci = false;
    private boolean prodajRezervisano = false;
    private boolean prodajNerezervisano = false;
    private boolean registracije = false;
    private boolean izvodjaci = false;
    private boolean otkazivanje = false;
    private String emailuser = "vinarija.rajic";
    private String emailpass = "sanskompromis";
    private List<Registracije> listaReg;
    private List<Izvodjaci> listaIzv;
    private List<Festival> listaF;
    private List<Rezervacije> listaRez;
    PretragaHelper helper;
    LoginHelper helper1;
    private boolean skip;
    private Festival festival = new Festival();
    private Izvodjaci izvodjac = new Izvodjaci();
    private String nazivF;
    private Date danFes;
    private int brUlaz;
    private boolean paket = false;

    public boolean isOtkazivanje() {
        return otkazivanje;
    }

    public void setOtkazivanje(boolean otkazivanje) {
        this.otkazivanje = otkazivanje;
    }

    public String getNazivF() {
        return nazivF;
    }

    public void setNazivF(String nazivF) {
        this.nazivF = nazivF;
    }

    public Date getDanFes() {
        return danFes;
    }

    public void setDanFes(Date danFes) {
        this.danFes = danFes;
    }

    public int getBrUlaz() {
        return brUlaz;
    }

    public void setBrUlaz(int brUlaz) {
        this.brUlaz = brUlaz;
    }

    public boolean isPaket() {
        return paket;
    }

    public void setPaket(boolean paket) {
        this.paket = paket;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public Izvodjaci getIzvodjac() {
        return izvodjac;
    }

    public void setIzvodjac(Izvodjaci izvodjac) {
        this.izvodjac = izvodjac;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public List<Izvodjaci> getListaIzv() {
        return listaIzv;
    }

    public void setListaIzv(List<Izvodjaci> listaIzv) {
        this.listaIzv = listaIzv;
    }

    public List<Registracije> getListaReg() {
        return listaReg;
    }

    public void setListaReg(List<Registracije> listaReg) {
        this.listaReg = listaReg;
    }

    public boolean isIzvodjaci() {
        return izvodjaci;
    }

    public void setIzvodjaci(boolean izvodjaci) {
        this.izvodjaci = izvodjaci;
    }

    public Admin() {
        helper = new PretragaHelper();
        helper1 = new LoginHelper();
    }

    public boolean isPocetnaAdmin() {
        return pocetnaAdmin;
    }

    public void setPocetnaAdmin(boolean pocetnaAdmin) {
        this.pocetnaAdmin = pocetnaAdmin;
    }

    public boolean isDodajFestival() {
        return dodajFestival;
    }

    public void setDodajFestival(boolean dodajFestival) {
        this.dodajFestival = dodajFestival;
    }

    public boolean isPromeniPostojeci() {
        return promeniPostojeci;
    }

    public void setPromeniPostojeci(boolean promeniPostojeci) {
        this.promeniPostojeci = promeniPostojeci;
    }

    public boolean isProdajRezervisano() {
        return prodajRezervisano;
    }

    public void setProdajRezervisano(boolean prodajRezervisano) {
        this.prodajRezervisano = prodajRezervisano;
    }

    public boolean isProdajNerezervisano() {
        return prodajNerezervisano;
    }

    public void setProdajNerezervisano(boolean prodajNerezervisano) {
        this.prodajNerezervisano = prodajNerezervisano;
    }

    public boolean isRegistracije() {
        return registracije;
    }

    public void setRegistracije(boolean registracije) {
        this.registracije = registracije;
    }

    public List<Rezervacije> getListaRez() {
        return listaRez;
    }

    public void setListaRez(List<Rezervacije> listaRez) {
        this.listaRez = listaRez;
    }

    public String pocetna() {
        pocetnaAdmin = true;
        dodajFestival = false;
        promeniPostojeci = false;
        prodajRezervisano = false;
        prodajNerezervisano = false;
        registracije = false;
        izvodjaci = false;
        otkazivanje = false;
        return "admin";
    }
     public String rezervisaneProdaja() {
        pocetnaAdmin = false;
        dodajFestival = false;
        promeniPostojeci = false;
        prodajRezervisano = true;
        prodajNerezervisano = false;
        registracije = false;
        izvodjaci = false;
        otkazivanje = false;
        listaRez= helper.listaSvihRezervacija();
        return "admin";
    }

    public String dodavanjeFestivala() {
        pocetnaAdmin = false;
        dodajFestival = true;
        promeniPostojeci = false;
        prodajRezervisano = false;
        prodajNerezervisano = false;
        registracije = false;
        izvodjaci = false;
        otkazivanje = false;
        return "admin";
    }

    public String registracijeFunkcija() {
        pocetnaAdmin = false;
        dodajFestival = false;
        promeniPostojeci = false;
        prodajRezervisano = false;
        prodajNerezervisano = false;
        izvodjaci = false;
        otkazivanje = false;
        registracije = true;

        dohvatiListu();
        return "admin";
    }

    public String nerezervisanoProdaja() {
        pocetnaAdmin = false;
        dodajFestival = false;
        promeniPostojeci = false;
        prodajRezervisano = false;
        prodajNerezervisano = true;
        izvodjaci = false;
        otkazivanje = false;
        registracije = false;
        return "admin";
    }

    public String prodajaNer() throws ParseException {
        if (!paket) {
            if (danFes != null) {
                if (helper.getFestivalN(nazivF).getMaxKarata() >= brUlaz) {
                    if (helper.getFestivalN(nazivF).getBrUlaznica() >= brUlaz) {
                        Integer i = helper.KupovinaNereg(nazivF, danFes, brUlaz, paket);
                        if (i == 0) {
                            addErrorMaxDnevno(null);
                            return "admin";
                        } else {
                            addSucces1(null);
                            return "admin";
                        }
                    } else {
                        addErrorBrojUlaznica(null);
                        return "admin";
                    }
                } else {
                    addErrorMaxKarata(null);
                    return "admin";
                }
            } else {
                addErrorDan(null);
                return "admin";
            }
        } else {
            Integer rez = helper.KupovinaNeregPaket(nazivF, brUlaz);
            if (rez == 1) {
                addSucces2(null);
                return "admin";
            } else {
                addErrorPaketBroj(null);
                return "admin";
            }
        }
    }

    public void dohvatiListu() {
        listaReg = helper.listaRegistracija();
    }

    public String odobri(Registracije reg, String ime, String prezime,
            String korIme, String lozinka, String telefon, String email) throws MessagingException {

        helper.dodajKorisnika(ime, prezime, korIme, lozinka, telefon, email);
        helper.obrisiZahtev(reg);
        listaReg = helper.listaRegistracija();
        addSucces(null);
        pocetnaAdmin = false;
        dodajFestival = false;
        promeniPostojeci = false;
        prodajRezervisano = false;
        prodajNerezervisano = false;
        izvodjaci = false;
        otkazivanje = false;
        registracije = true;
        Email.Send(emailuser, emailpass, reg.getEmail(), "Automatska poruka: Registracija",
                "Vas zahtev za registraciju je prihvacen! Mozete se ulogovati sa username: " + reg.getKorisnickoIme() + " i  pass: " + reg.getLozinka() + ". Hvala na registraciji!");
        return "admin";
    }

    public String odbij(Registracije reg) throws MessagingException {

        helper.obrisiZahtev(reg);
        listaReg = helper.listaRegistracija();
        Email.Send(emailuser, emailpass, reg.getEmail(), "Automatska poruka", "Vas zahtev za registraciju nije prihvacen!");
        addErrorOdbijanje(null);
        pocetnaAdmin = false;
        dodajFestival = false;
        promeniPostojeci = false;
        prodajRezervisano = false;
        prodajNerezervisano = false;
        izvodjaci = false;
        registracije = true;
        otkazivanje = false;
        return "admin";
    }

    public String listaFes() {
        pocetnaAdmin = false;
        dodajFestival = false;
        promeniPostojeci = true;
        prodajRezervisano = false;
        prodajNerezervisano = false;
        izvodjaci = false;
        otkazivanje = false;
        registracije = false;
        
        return "admin";
    }
    
    public String otkazivanjeFes() {
        pocetnaAdmin = false;
        dodajFestival = false;
        promeniPostojeci = false;
        prodajRezervisano = false;
        prodajNerezervisano = false;
        izvodjaci = false;
        registracije = false;
        otkazivanje = true;
        
        return "admin";
    }

    public String listaIzvodjaca(String naziv) {
        pocetnaAdmin = false;
        dodajFestival = false;
        promeniPostojeci = false;
        prodajRezervisano = false;
        prodajNerezervisano = false;
        registracije = false;
        izvodjaci = true;
        otkazivanje = false;
        listaIzv = helper.dohvatiIzv(naziv);
        return "admin";
    }

    public String onRowEdit(RowEditEvent event) {
        Izvodjaci izv = (Izvodjaci) event.getObject();
        helper1.promenaPodatakaIzv(izv.getId(), izv.getFestival(), izv.getNaziv(), izv.getStart(), izv.getEnd());
        return "admin";
    }

    public String onRowCancel(RowEditEvent event) {
        return "admin";
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;
            Izvodjaci izv = izvodjac;
            helper.dodajIzvodjaca(festival.getNaziv(), izv);
            FacesMessage msg = new FacesMessage("Successful", "Uspesno ste dodali novog izvodjaca!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "izvodjaci";
        } else {
            return event.getNewStep();
        }
    }

    public void save(Festival fes) {
        helper1.dodajFestival(fes);
        Izvodjaci izv = izvodjac;
        helper.dodajIzvodjaca(festival.getNaziv(), izv);
        FacesMessage msg = new FacesMessage("Successful", "Uspesno ste dodali novi festival!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String LogOut() {
        pocetnaAdmin = true;
        dodajFestival = false;
        promeniPostojeci = false;
        prodajRezervisano = false;
        prodajNerezervisano = false;
        registracije = false;
        izvodjaci = false;
        otkazivanje = false;
        helper.getFactory().close();
        helper1.getFactory().close();
        
        
        return "index";
    }
    
    public String prodaj(Rezervacije rez) throws MessagingException {
        helper.prodajRezervisano(rez);
        addSucces1(null);
        Korisnik k=helper1.getKorisnik(rez.getKorisnickoIme());
        Email.Send(emailuser, emailpass, k.getEmail(), "Automatska poruka: Kupovina karata",
                "Vas rezervacija za kupovinom karata je prihvacena! Kupili ste karte za festival: " 
                        + rez.getFestival()+ " id rezervacije: " + rez.getId()+ ". Hvala na kupovini!");
        listaRez = helper.listaSvihRezervacija();
        return "admin";
    }
    
    public String ponistiProdaju(Rezervacije rez) throws MessagingException, ParseException {
        helper.otkaziRezervisano(rez);
        if(rez.getPaket()==1) {
            helper.VracanjeulaznicaPaket(rez.getFestival(), rez.getBrojUlaznica());
        }else{
            helper.VracanjeUlaznica(rez.getFestival(), rez.getDatum(), rez.getBrojUlaznica());
        }
        addErrorOdbijanje(null);
        Korisnik k=helper1.getKorisnik(rez.getKorisnickoIme());
        Email.Send(emailuser, emailpass, k.getEmail(), "Automatska poruka: Kupovina karata",
                "Vas rezervacija za kupovinom karata je odbijena! Rezervisali ste karte za festival: " 
                        + rez.getFestival()+ " id rezervacije: " + rez.getId()+ ". Pokusajte ponovo!");
        listaRez = helper.listaSvihRezervacija();
        return "admin";
    }
    
    public void addSucces(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Odobrenje:", "Uspesno ste se odobrili zahtev!!!"));
    }

    public void addSucces1(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Odobrenje:", "Uspesno ste se prodali karte!!!"));
    }

    public void addSucces2(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Odobrenje:", "Uspesno ste se prodali paket(e) karata!!!"));
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

    public void addErrorMaxKarata(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:", "Iznos kolicine karata je veci nego dozvoljeni!"));
    }

    public void addErrorBrojUlaznica(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:", "Nedovoljan ukupan broj ulaznica na festivalu!"));
    }

    public void addErrorPaketBroj(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska:", "Nedovoljno ulaznica za sve dane festivala!"));
    }
}
