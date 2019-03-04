/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bean.Festival;
import bean.Korisnik;
import bean.Rezervacije;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Rayker
 */
public class KorisnikHelper {
    Session session = null;
    private SessionFactory factory;

    public KorisnikHelper() {
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
    
    List<Festival> listaFestNajskorije() {
        Date date= new Date();
        Session session = factory.openSession();
        Transaction tx = null;
        List<Festival> lista = null;

        try {
            tx = session.beginTransaction();
            String hql = "from Festival F where F.vremeOd>=:datum or F.vremeDo>:datum ORDER BY F.vremeOd ASC limit 5";
            Query q = session.createQuery(hql).setParameter("datum", date);

            lista = (List<Festival>) q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        return lista;
        }
    
    public void rejting(Festival fes, int ocena) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            fes.setOcena(ocena);
            session.update(fes);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }

    }

    void dodajRezervaciju(Korisnik k,Festival fes, Date dan, int broj, int paket, int kupljeno) {
        Session session = factory.openSession();
        Transaction tx = null;
        Rezervacije rezervacija=new Rezervacije();
        try {
            tx = session.beginTransaction();
            rezervacija.setKorisnickoIme(k.getKorisnickoIme());
            rezervacija.setFestival(fes.getNaziv());
            rezervacija.setBrojUlaznica(broj);
            rezervacija.setDatum(dan);
            rezervacija.setPaket(paket);
            rezervacija.setKupljeno(kupljeno);
            session.save(rezervacija);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }

    }

    List<Rezervacije> dohvatiRezervacije(Korisnik korisnik) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Rezervacije> lista = null;

        try {
            tx = session.beginTransaction();
            String hql = "from Rezervacije R where R.korisnickoIme=:ime";
            Query q = session.createQuery(hql).setParameter("ime", korisnik.getKorisnickoIme());

            lista = (List<Rezervacije>) q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        return lista;
        }
       
        
}
