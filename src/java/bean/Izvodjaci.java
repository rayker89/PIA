package bean;
// Generated 19-Feb-2017 19:06:23 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Izvodjaci generated by hbm2java
 */
public class Izvodjaci  implements java.io.Serializable {


     private Integer id;
     private String festival;
     private String naziv;
     private Date start;
     private Date end;

    public Izvodjaci() {
    }

    public Izvodjaci(String festival, String naziv, Date start, Date end) {
       this.festival = festival;
       this.naziv = naziv;
       this.start = start;
       this.end = end;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFestival() {
        return this.festival;
    }
    
    public void setFestival(String festival) {
        this.festival = festival;
    }
    public String getNaziv() {
        return this.naziv;
    }
    
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public Date getStart() {
        return this.start;
    }
    
    public void setStart(Date start) {
        this.start = start;
    }
    public Date getEnd() {
        return this.end;
    }
    
    public void setEnd(Date end) {
        this.end = end;
    }




}


