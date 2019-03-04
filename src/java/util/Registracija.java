/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.MessagingException;

/**
 *
 * @author Rayker
 */
@ManagedBean
@SessionScoped
public class Registracija implements Serializable{

    private String username;
    private String password;
    private String ime;
    private String prezime;
    private String telefon;
    private String email;
    LoginHelper helper = new LoginHelper();;
    /**
     * Creates a new instance of Registracija
     */
    public Registracija() {
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean proveraLozinke(String lozinka) {

        boolean potvrda = Pattern.matches("^(?=[a-zA-Z])(?!.*([A-Za-z0-9])\\1{2})(?=.*\\d)(?=(.*[a-z]){3})(?=.*[A-Z])(?!.*\\s).{6,12}", lozinka);

        return potvrda;
    }

    public boolean proveraPolja() {
        if (username.isEmpty() || password.isEmpty() || ime.isEmpty() || prezime.isEmpty() || telefon.isEmpty() || email.isEmpty()) {

            return true;
        } else {
            return false;
        }

    }

    public String registracija() throws MessagingException {
        if (!proveraPolja()) {
            if(!helper.proveraImena(username)) {
                addErrorUsername(null);
                return"registracija";
            }
            if(!proveraLozinke(password)){
                addErrorPass(null);
                return"registracija";
            }
            addSucces(null);
            helper.dodajRegistraciju(username, ime, prezime, password, telefon, email);
            Email.Send("vinarija.rajic", "sanskompromis", email, "Zahtev za registracijom", "Hvala vam na poslatom zahtevu, bicete uskoro obavesteni o prihvatanju ili odbijanju Vaseg zahteva!");
            return "index";
        } else {

            addErrorField(null);
            return "registracija";
        }
        
    }
    
    public String nazad() {
        return "index";
    }

    public void addErrorPass(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska prilikom registracije:", "Šifra mora početi slovom(malim ili velikim),mora imati minumalno 3 mala slova, jedno veliko slovo i jedan broj ili specijalni znak u sebi, i mora imati izmedju 6 i 12 znakova !"));
    }

    public void addErrorField(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska prilikom registracije:", "Morate popuniti sva polja!!!!"));
    }
    
    public void addErrorUsername(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska prilikom registracije:", "Uneto korisnicko ime je vec zauzeto!!!!"));
    }

    public void addSucces(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh:", "Uspesno ste poslali zahtev za registraciju!!!"));
    }
}
