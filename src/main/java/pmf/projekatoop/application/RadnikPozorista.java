package pmf.projekatoop.application;

import java.util.ArrayList;

public class RadnikPozorista extends Korisnik {

    private Pozoriste pozoriste;
    
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
                this.pozoriste.povecajBrojRadnika();
                sviRadniciPozorista.add(this);
                sviKorisnici.add(this);
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

    public Pozoriste getPozoriste() {
        return pozoriste;
    }

}
