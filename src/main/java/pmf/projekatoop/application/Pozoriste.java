package pmf.projekatoop.application;

import java.util.ArrayList;

public class Pozoriste {

    private int id;
    private String naziv;
    private String grad;
    private int brojSjedista;

    public static ArrayList<Pozoriste> svaPozorista = new ArrayList<>();

    public Pozoriste(int id, String naziv, String grad, int brojSjedista) {
        this.id = id;
        this.naziv = naziv;
        this.grad = grad;
        this.brojSjedista = brojSjedista;
        if (!vecPostoji()) {
            svaPozorista.add(this);
        } else {
            System.err.println("Pozoriste naziv:" + naziv + ", grad:" + grad + " vec postoji!");
        }
    }

    private boolean vecPostoji() {
        for (Pozoriste p : svaPozorista) {
            if (p.getNaziv().equals(this.naziv) && p.getGrad().equals(this.grad)) {
                return true;
            }
        }
        return false;
    }

    public static Pozoriste getPozoristeById(int pozoristeId) {
        for (Pozoriste p : svaPozorista) {
            if (p.getId() == pozoristeId) {
                return p;
            }
        }
        System.err.println("(.getPozoristeById) Pozoriste sa id: " + pozoristeId +
                " nije pronadjeno!");
        return null;
    }

    public static Pozoriste getPozoristeByNazivIGrad(String naziv, String grad) {
        for (Pozoriste p : svaPozorista) {
            if (p.getNaziv().equals(naziv) && p.getGrad().equals(grad)) {
                return p;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getGrad() {
        return grad;
    }

    public int getBrojSjedista() {
        return brojSjedista;
    }

    public static ArrayList<String> getSpisakPozorista() {
        ArrayList<String> pozorista = new ArrayList<>();
        String pozoriste;
        for (Pozoriste p : svaPozorista) {
            pozoriste = p.getId() + ". " + p.getNaziv() + ", " + p.getGrad() + "\n";
            pozorista.add(pozoriste);
        }
        return pozorista;
    }

}
