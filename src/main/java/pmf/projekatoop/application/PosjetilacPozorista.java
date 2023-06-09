package pmf.projekatoop.application;

import java.util.ArrayList;

public class PosjetilacPozorista extends Korisnik {

    public static ArrayList<PosjetilacPozorista> sviPosjetiociPozorista = new ArrayList<>();

    public PosjetilacPozorista(int id, String ime, String prezime, String korisnickoIme, String lozinka) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        if (!vecPostoji()) {
            sviPosjetiociPozorista.add(this);
            sviKorisnici.add(this);
        } else {
            System.err.println("Posjetilac pozorista: " + korisnickoIme + " vec postoji!");
        }
    }

    private boolean vecPostoji() {
        for (PosjetilacPozorista pp : sviPosjetiociPozorista) {
            if (pp.getKorisnickoIme().equals(this.korisnickoIme)) {
                return true;
            }
        }
        return false;
    }

    public static PosjetilacPozorista getPosjetilacPozoristaById(int posjetilacPozoristaId) {
        for (PosjetilacPozorista pp : sviPosjetiociPozorista) {
            if (pp.getId() == posjetilacPozoristaId) {
                return pp;
            }
        }
        System.err.println("(.getPosjetilacPozoristaById) Posjetilac pozorista sa id: " + posjetilacPozoristaId +
                " nije pronadjen!");
        return null;
    }

}
