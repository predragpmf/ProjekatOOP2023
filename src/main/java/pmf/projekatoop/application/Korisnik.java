package pmf.projekatoop.application;

import java.util.ArrayList;

public class Korisnik {

    protected int id;
    protected String ime, prezime, korisnickoIme, lozinka;
    public static ArrayList<Korisnik> sviKorisnici = new ArrayList<>();
    public static Korisnik prijavljeniKorisnik;

    public int getId() {
        return id;
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

    public static Korisnik getKorisnikByKorisnickoIme(String korisnickoIme) {
        for (Korisnik k : sviKorisnici) {
            if (k.getKorisnickoIme().equals(korisnickoIme)) {
                return k;
            }
        }
        return null;
    }

}
