package pmf.projekatoop.application;

import java.util.ArrayList;

public class Predstava {

    private int id;
    private String naziv;
    public enum Zanrovi {
        KOMEDIJA,
        FARSA,
        SATIRA,
        KOMEDIJA_RESTAURACIJE,
        TRAGEDIJA,
        ISTORIJA,
        MJUZIKL
    }
    private Zanrovi zanr;

    public static ArrayList<Predstava> svePredstave = new ArrayList<>();

    public Predstava(int id, String naziv, int zanr) {
        this.id = id;
        this.naziv = naziv;
        switch (zanr) {
            case 1 -> this.zanr = Zanrovi.KOMEDIJA;
            case 2 -> this.zanr = Zanrovi.FARSA;
            case 3 -> this.zanr = Zanrovi.SATIRA;
            case 4 -> this.zanr = Zanrovi.KOMEDIJA_RESTAURACIJE;
            case 5 -> this.zanr = Zanrovi.TRAGEDIJA;
            case 6 -> this.zanr = Zanrovi.ISTORIJA;
            case 7 -> this.zanr = Zanrovi.MJUZIKL;
            default -> {
                System.err.println("Zanr predstave: " + naziv + " ne postoji!");
                return;
            }
        }
        if (!vecPostoji()) {
            svePredstave.add(this);
        } else {
            System.err.println("Predstava: " + naziv + " vec postoji!");
        }
    }

    private boolean vecPostoji() {
        for (Predstava p : svePredstave) {
            if (p.getNaziv().equals(this.naziv) && p.getZanr().ordinal() == this.zanr.ordinal()) {
                return true;
            }
        }
        return false;
    }

    public static Predstava getPredstavaById(int predstavaId) {
        for (Predstava p : svePredstave) {
            if (p.getId() == predstavaId) {
                return p;
            }
        }
        System.err.println("(.getPredstavaById) Predstava sa id: " + predstavaId +
                " nije pronadjena!");
        return null;
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public Zanrovi getZanr() {
        return zanr;
    }

    public static boolean postoji(String naziv) {
        for (Predstava p : svePredstave) {
            if (p.getNaziv().equals(naziv)) {
                return true;
            }
        }
        return false;
    }

    public static Predstava getPredstavaByNaziv(String naziv) {
        for (Predstava p : svePredstave) {
            if (p.getNaziv().equals(naziv)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.naziv;
    }
}
