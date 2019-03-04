package util;

import bean.Festival;
import bean.Izvodjaci;
import bean.Korisnik;
import bean.Registracije;
import bean.Rezervacije;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rayker
 */
public class LoginHelper {

    Session session = null;
    private  SessionFactory factory;

    public LoginHelper() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public  SessionFactory getFactory() {
        return factory;
    }
    
     
     
    public Korisnik getKorisnik(String korime) {
        Korisnik korisnik = null;
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql = "from Korisnik k where k.korisnickoIme = :ime";
            Query q = session.createQuery(hql).setParameter("ime", korime);

            korisnik = (Korisnik) q.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        if (korisnik != null) {
            return korisnik;
        } else {

            return null;
        }
    }

    public Festival getFestival(Festival fest) {
        Festival festival = null;
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql = "from Festival f where f.naziv = :naziv";
            Query q = session.createQuery(hql).setParameter("naziv", fest.getNaziv());

            festival = (Festival) q.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }

        return festival;
    }

    public String dodajRegistraciju(String username, String ime, String prezime,
            String password, String telefon, String email) {

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Registracije registracija = new Registracije(username, ime, prezime, password, telefon, email);
            session.save(registracija);
            session.getTransaction().commit();

        } catch (Exception E) {
            if (tx != null) {
                tx.rollback();
            }
            E.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void dodajFestival(Festival fest) {
        Session session = factory.openSession();
        Transaction tx = null;
        HashMap<Date, Integer> mapa = new HashMap<>();
        try {
            tx = session.beginTransaction();

            Festival fes = new Festival();
            fes = fest;
            Calendar start = Calendar.getInstance();
            start.setTime(fest.getVremeOd());
            Calendar end = Calendar.getInstance();
            end.setTime(fest.getVremeDo());

            for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                mapa.put(date, fest.getMaxDnevno());
            }
            fes.setPoDanima(mapa.toString());
            session.save(fes);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public boolean proveraImena(String username) {
        Korisnik korisnik = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Korisnik k where k.korisnickoIme = :ime";
            Query q = session.createQuery(hql).setParameter("ime", username);

            korisnik = (Korisnik) q.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        if (korisnik != null) {
            return false;
        } else {

            return true;
        }
    }

    public void promeniLozinku(String user, String pass) {
        Korisnik korisnik = getKorisnik(user);
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            korisnik.setLozinka(pass);
            session.update(korisnik);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }

    }

    void promenaPodatakaIzv(Integer id, String festival, String naziv, Date start, Date end) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Izvodjaci izv = (Izvodjaci) session.get(Izvodjaci.class, id);
            izv.setFestival(festival);
            izv.setNaziv(naziv);
            izv.setStart(start);
            izv.setEnd(end);
            session.update(izv);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    void uloguj(String user) {
        Korisnik korisnik = getKorisnik(user);
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            korisnik.setAktivan(1);
            session.update(korisnik);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        }
    
     void izloguj(String user) {
        Korisnik korisnik = getKorisnik(user);
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            korisnik.setAktivan(0);
            session.update(korisnik);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        }
    
    public Korisnik getKorisnik() {
        Korisnik korisnik = null;
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql = "from Korisnik k where k.aktivan = :akt";
            Query q = session.createQuery(hql).setParameter("akt", 1);

            korisnik = (Korisnik) q.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        if (korisnik != null) {
            return korisnik;
        } else {

            return null;
        }
    }
    
}
