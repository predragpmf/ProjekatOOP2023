package pmf.projekatoop.application;

import java.util.ArrayList;

public class RadnikPozorista {

    private int id;
    private Pozoriste pozoriste;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    
    public static ArrayList<RadnikPozorista> sviRadniciPozorista = new ArrayList<>();

    public RadnikPozorista(int id, String ime, String prezime,
                           String korisnickoIme, String lozinka, int pozoristeId) {
        this.id = id;
        this.pozoriste = Pozoriste.getPozoristeById(pozoristeId);
        if (this.pozoriste != null) {
            this.ime = ime;
            this.prezime = prezime;
            this.korisnickoIme = korisnickoIme;
            this.lozinka = lozinka;
            if (!vecPostoji()) {
                sviRadniciPozorista.add(this);
            } else {
                System.err.println("Radnik pozorista korisnickoIme:" + korisnickoIme + " vec postoji!");
            }
        } else {
            System.err.println("Pozoriste radnika korisnickoIme:" + korisnickoIme + " ne postoji!");
        }
    }

    private boolean vecPostoji() {
        for (RadnikPozorista rp : sviRadniciPozorista) {
            if (rp.getKorisnickoIme().equals(this.korisnickoIme)) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public Pozoriste getPozoriste() {
        return pozoriste;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

}
