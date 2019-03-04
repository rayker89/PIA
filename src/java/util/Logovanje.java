package util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import bean.Korisnik;
import java.io.Serializable;
import java.sql.Connection;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rayker
 */
@ManagedBean
@SessionScoped
public class Logovanje implements Serializable{

    private String user;
    private String pass;
    private String passOld;
    private Korisnik korisnik;
    public static Korisnik kor=new Korisnik();
    private int tip1 = 10;
    LoginHelper helper = new LoginHelper();

    public Logovanje() {
        helper = new LoginHelper();
    }

    public Logovanje(String user, String pass) {
        this.user = user;
        this.pass = pass;

    }

    public String getPassOld() {
        return passOld;
    }

    public void setPassOld(String passOld) {
        this.passOld = passOld;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public boolean proveraLozinke(String lozinka) {

        boolean potvrda = Pattern.matches("^(?=[a-zA-Z])(?!.*([A-Za-z0-9])\\1{2})(?=.*\\d)(?=(.*[a-z]){3})(?=.*[A-Z])(?!.*\\s).{6,12}", lozinka);

        return potvrda;
    }

    public String ulogujSe() {
        if (!user.equals("")) {
            korisnik = helper.getKorisnik(user);
            if (korisnik != null) {
                if (proveraLozinke(pass)) {
                    if (pass.equals(korisnik.getLozinka())) {
                        if (korisnik.getTip() == 0) {
                            addHelloMessage(null);
                            kor.setKorisnickoIme(user);
                            helper.uloguj(user);
                            return "registrovaniKorisnik";
                        } else if (korisnik.getTip() == 1) {
                            addHelloMessage(null);
                            return "admin";
                        }
                    } else {
                        addErrorPass1(null);
                        return "index";
                    }
                } else {
                    addErrorPass(null);
                    return "index";
                }
            } else {
                addErrorNon(null);
                return "index";
            }
        } else {
            addErrorEmpty(null);
            return "index";
        }
        return "index";
    }

    public String LogOut() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        helper.getFactory().close();
        return "index";
    }

    public String registracija() {
        return "registracija";
    }

    public String promenaLoz() {
        return "promenaLozinke";
    }

    public String promenaLozinke() {
        if (!user.equals("")) {
            korisnik = helper.getKorisnik(user);
            if (korisnik != null) {
                if (proveraLozinke(passOld)) {
                    if (passOld.equals(korisnik.getLozinka())) {
                        if (proveraLozinke(pass)) {
                            addSucces(null);
                            helper.promeniLozinku(user,pass);
                            return "index";
                        } else {
                            addErrorPass(null);
                            return "promenaLozinke";
                        }

                    } else {
                        addErrorPass1(null);
                        return "promenaLozinke";
                    }
                } else {
                    addErrorPass(null);
                    return "promenaLozinke";
                }
            } else {
                addErrorNon(null);
                return "promenaLozinke";
            }
        } else {
            addErrorEmpty(null);
            return "promenaLozinke";
        }
        
    }

    public void addErrorPass(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska šifra:", "Šifra mora početi slovom(malim ili velikim),mora imati jedno malo, jedno veliko slovo i jedan broj u sebi, i mora imati izmedju 6 i 12 znakova !"));
    }

    public void addErrorPass1(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska šifra:", "Neispravna sifra!!!"));
    }

    public void addItIsSame(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska prilikom promene šifre:", "Šifre se moraju razlikovati !"));
    }

    public void addErrorNon(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska korisnik:", "Ne postoji korisnik sa ovim username i pass"));
    }

    public void addErrorEmpty(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska korisnik:", "Niste uneli korisnicko ime!!!"));
    }

    public void addSucces(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh:", "Uspesno ste se promenili lozinku!!! Ulogujte se ponovo!!!"));
    }

    public void addHelloMessage(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dobrodošli", "" + korisnik.getIme() + " " + korisnik.getPrezime()));
    }

    public String gost() {
        return "neregistrovaniKorisnik";
    }
}
