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
import com.sun.faces.config.WebConfiguration;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Rayker
 */
public class PretragaHelper {

    Session session = null;
    private  SessionFactory factory;

    public PretragaHelper() {
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
    
    public List listaFestivalaDatum(java.util.Date datum) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Festival.class);

        if (datum != null) {
            criteria.add(Restrictions.le("vremeOd", datum));
            criteria.add(Restrictions.ge("vremeDo", datum));
        }

        return criteria.list();
    }    
    
    public List listaFestivala(String mesto, String naziv, Date datum, String izvodjac) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Festival.class);
        if (!mesto.equals("")) {
            criteria.add(Restrictions.eq("mesto", mesto));
        }

        if (!naziv.equals("")) {
            criteria.add(Restrictions.eq("naziv", naziv));
        }

        if (datum != null) {
            criteria.add(Restrictions.le("vremeOd", datum));
            criteria.add(Restrictions.ge("vremeDo", datum));
        }

        if (!izvodjac.equals("")) {
            Criteria criteria2 = session.createCriteria(Izvodjaci.class);
            criteria2.setProjection(Projections.property("festival"));
            criteria2.add(Restrictions.eq("naziv", izvodjac));
            criteria2.list();
            criteria.add(Restrictions.in("naziv", criteria2.list()));
        }
        return criteria.list();
    }

    public List listaFestivalaTop5() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Festival> lista = null;

        try {
            tx = session.beginTransaction();
            String hql = "from Festival F ORDER BY F.ocena DESC limit 5";
            Query q = session.createQuery(hql);

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

    public List listaSvihF() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Festival> lista = null;

        try {
            tx = session.beginTransaction();
            String hql = "from Festival";
            Query q = session.createQuery(hql);

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

    public List listaSvihRezervacija() {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Rezervacije.class);
        Integer i = 0;
        criteria.add(Restrictions.eq("kupljeno", i));
        return criteria.list();
    }

    public List listaKorisnikaFest(Festival fes) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Korisnik.class);
        Criteria criteria2 = session.createCriteria(Rezervacije.class);
        criteria2.setProjection(Projections.property("korisnickoIme"));
        criteria2.add(Restrictions.eq("festival", fes.getNaziv()));
        criteria2.list();
        criteria.add(Restrictions.in("korisnickoIme", criteria2.list()));
        return criteria.list();
    }

    public Rezervacije dohvatiRezervaciju(Rezervacije rez) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Rezervacije.class);
        criteria.add(Restrictions.eq("id", rez.getId()));
        return (Rezervacije) criteria.uniqueResult();

    }

    public void prodajRezervisano(Rezervacije rez) {
        Session session = factory.openSession();
        Transaction tx = null;
        Rezervacije rezerv = dohvatiRezervaciju(rez);

        try {
            tx = session.beginTransaction();
            rezerv.setKupljeno(1);
            session.update(rezerv);
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

    public void otkaziRezervisano(Rezervacije rez) {
        Session session = factory.openSession();
        Transaction tx = null;
        Rezervacije rezerv = dohvatiRezervaciju(rez);

        try {
            tx = session.beginTransaction();
            session.delete(rezerv);
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

    public List listaRegistracija() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Registracije> lista = null;

        try {
            tx = session.beginTransaction();
            String hql = "from Registracije R";
            Query q = session.createQuery(hql);

            lista = (List<Registracije>) q.list();
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
    
    
    public void dodajKorisnika(String ime, String prezime, String korIme, String lozinka, String telefon, String email) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Korisnik kor = new Korisnik(ime, prezime, korIme, lozinka, telefon, email, 0,0);
            session.save(kor);
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

    public void dodajIzvodjaca(String festival, Izvodjaci izv) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Izvodjaci izvo = new Izvodjaci();
            izvo = izv;
            izvo.setFestival(festival);
            session.save(izvo);
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

    public void obrisiZahtev(Registracije reg) {
        Session session = factory.openSession();
        Transaction tx = null;
        Registracije regist = reg;
        try {
            tx = session.beginTransaction();
            session.delete(regist);
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

    public void brisanje(Festival fest) {
        Festival fes = getFestival(fest);
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(fes);
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

    public Festival getFestivalN(String naziv) {
        Festival festival = null;
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql = "from Festival f where f.naziv = :naziv";
            Query q = session.createQuery(hql).setParameter("naziv", naziv);

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

    public void promenaPodatakaFes(Festival fes) {
        Festival fest = getFestival(fes);
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            fest.setMesto(fes.getMesto());
            fest.setVremeOd(fes.getVremeOd());
            fest.setVremeDo(fes.getVremeDo());
            fest.setBrUlaznica(fes.getBrUlaznica());
            fest.setCenaZaDan(fes.getCenaZaDan());
            fest.setCanaZaPaket(fes.getCanaZaPaket());
            fest.setOcena(fes.getOcena());
            fest.setMaxKarata(fes.getMaxKarata());
            fest.setMaxDnevno(fes.getMaxDnevno());

            session.update(fest);
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

    public int KupovinaNereg(String naziv, java.util.Date datum, Integer brUlaz, boolean paket) throws ParseException {
        Festival fest = getFestivalN(naziv);
        Session session = factory.openSession();
        Transaction tx = null;
        HashMap<java.util.Date, Integer> mapa = new HashMap();
        String mojamapa = fest.getPoDanima();
        String mojamapa1;
        mojamapa1 = mojamapa.replace("{", "");
        mojamapa1 = mojamapa1.replace("}", "");
        String[] parovi = mojamapa1.split(", ");
        for (String par : parovi) {
            String[] keyValue = par.split("=");
            DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            java.util.Date date = formatter.parse(keyValue[0]);
            mapa.put(date, Integer.valueOf(keyValue[1]));
        }
        if (mapa.get(datum) >= brUlaz) {
            try {
                tx = session.beginTransaction();
                Integer i = mapa.get(datum);
                mapa.replace(datum, i, i - brUlaz);
                fest.setPoDanima(mapa.toString());
                fest.setBrUlaznica(fest.getBrUlaznica() - brUlaz);
                session.update(fest);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();

            } finally {
                session.close();
            }
            return 1;
        } else {

            return 0;
        }
    }

    public void VracanjeUlaznica(String naziv, java.util.Date datum, Integer brUlaz) throws ParseException {
        Festival fest = getFestivalN(naziv);
        Session session = factory.openSession();
        Transaction tx = null;
        HashMap<java.util.Date, Integer> mapa = new HashMap();
        String mojamapa = fest.getPoDanima();
        String mojamapa1;
        mojamapa1 = mojamapa.replace("{", "");
        mojamapa1 = mojamapa1.replace("}", "");
        String[] parovi = mojamapa1.split(", ");
        for (String par : parovi) {
            String[] keyValue = par.split("=");
            DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            java.util.Date date = formatter.parse(keyValue[0]);
            mapa.put(date, Integer.valueOf(keyValue[1]));
        }

        try {
            tx = session.beginTransaction();
            Integer i = mapa.get(datum);
            mapa.replace(datum, i, i + brUlaz);
            fest.setPoDanima(mapa.toString());
            fest.setBrUlaznica(fest.getBrUlaznica() + brUlaz);
            session.update(fest);
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

    public int KupovinaNeregPaket(String naziv, Integer brUlaz) throws ParseException {
        Festival fest = getFestivalN(naziv);
        Session session = factory.openSession();
        Transaction tx = null;
        Integer counter = 0;
        Integer vrednost;
        HashMap<java.util.Date, Integer> mapa = new HashMap();
        String mojamapa = fest.getPoDanima();
        String mojamapa1;
        mojamapa1 = mojamapa.replace("{", "");
        mojamapa1 = mojamapa1.replace("}", "");
        String[] parovi = mojamapa1.split(", ");
        for (String par : parovi) {
            String[] keyValue = par.split("=");
            DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            java.util.Date date = formatter.parse(keyValue[0]);
            mapa.put(date, Integer.valueOf(keyValue[1]));
        }
        Calendar start = Calendar.getInstance();
        start.setTime(fest.getVremeOd());
        Calendar end = Calendar.getInstance();
        end.setTime(fest.getVremeDo());
        for (java.util.Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            vrednost = mapa.get(date);
            if (vrednost < brUlaz) {
                return 0;
            }
            mapa.replace(date, vrednost, vrednost - brUlaz);
            counter++;
        }

        try {
            tx = session.beginTransaction();
            fest.setBrUlaznica(fest.getBrUlaznica() - brUlaz * counter);
            fest.setPoDanima(mapa.toString());
            session.update(fest);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        return 1;

    }

    public void VracanjeulaznicaPaket(String naziv, Integer brUlaz) throws ParseException {
        Festival fest = getFestivalN(naziv);
        Session session = factory.openSession();
        Transaction tx = null;
        Integer counter = 0;
        Integer vrednost;
        HashMap<java.util.Date, Integer> mapa = new HashMap();
        String mojamapa = fest.getPoDanima();
        String mojamapa1;
        mojamapa1 = mojamapa.replace("{", "");
        mojamapa1 = mojamapa1.replace("}", "");
        String[] parovi = mojamapa1.split(", ");
        for (String par : parovi) {
            String[] keyValue = par.split("=");
            DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            java.util.Date date = formatter.parse(keyValue[0]);
            mapa.put(date, Integer.valueOf(keyValue[1]));
        }
        Calendar start = Calendar.getInstance();
        start.setTime(fest.getVremeOd());
        Calendar end = Calendar.getInstance();
        end.setTime(fest.getVremeDo());
        for (java.util.Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            vrednost = mapa.get(date);
            mapa.replace(date, vrednost, vrednost + brUlaz);
            counter++;
        }

        try {
            tx = session.beginTransaction();
            fest.setBrUlaznica(fest.getBrUlaznica() + brUlaz * counter);
            fest.setPoDanima(mapa.toString());
            session.update(fest);
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

    public List dohvatiIzv(String fest) {
        List<Izvodjaci> lista = null;
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql = "from Izvodjaci i where i.festival = :naziv";
            Query q = session.createQuery(hql).setParameter("naziv", fest);

            lista = (List<Izvodjaci>) q.list();
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

    void otkaziKarte(Festival f) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Rezervacije> rez = listaSvihRezervacija();

        tx = session.beginTransaction();
        for (Rezervacije re : rez) {
            if(re.getFestival().equals(f.getNaziv()))
                {
                    session.delete(re);
                }
        }
                tx.commit();
                session.close();
            }

    void dodajRezervaciju() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        

    }
