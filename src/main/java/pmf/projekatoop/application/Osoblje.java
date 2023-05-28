package pmf.projekatoop.application;

import java.util.ArrayList;

public class Osoblje {

    private int id;
    private String ime;
    private String prezime;
    public enum Tipovi {
        AUTOR,
        REZISER,
        GLUMAC
    }
    private Tipovi tip;

    public static ArrayList<Osoblje> svoOsoblje = new ArrayList<>();

    public Osoblje(int id, String ime, String prezime, int tip) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        switch (tip) {
            case 1 -> this.tip = Tipovi.AUTOR;
            case 2 -> this.tip = Tipovi.REZISER;
            case 3 -> this.tip = Tipovi.GLUMAC;
            default -> {
                System.err.println("Tip osobe: " + ime + " " + prezime + " ne postoji!");
                return;
            }
        }
        if (!vecPostoji()) {
            svoOsoblje.add(this);
        } else {
            System.err.println("Osoblje: " + ime + " " + prezime + " vec postoji!");
        }
    }

    private boolean vecPostoji() {
        for (Osoblje o : svoOsoblje) {
            if (o.getIme().equals(this.ime) && o.getPrezime().equals(this.prezime)) {
                return true;
            }
        }
        return false;
    }

    public static Osoblje getOsobljeById(int osobljeId) {
        for (Osoblje o : svoOsoblje) {
            if (o.getId() == osobljeId) {
                return o;
            }
        }
        System.err.println("(.getOsobljeById) Osoblje sa id: " + osobljeId +
                " nije pronadjeno!");
        return null;
    }

    public static Osoblje getOsobljeByImeIPrezime(String ime, String prezime) {
        for (Osoblje o : svoOsoblje) {
            if (o.getIme().equals(ime) && o.getPrezime().equals(prezime)) {
                return o;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public Tipovi getTip() {
        return tip;
    }

    @Override
    public String toString() {
        return this.ime + " " + this.prezime;
    }
}
