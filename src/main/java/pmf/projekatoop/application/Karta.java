package pmf.projekatoop.application;

import java.util.ArrayList;

public class Karta {
    private int id;
    private IzvodjenjePredstave izvodjenjePredstave;
    private enum Statusi {
        KUPLJENA,
        REZERVISANA,
        REZERVISANA_PREUZETA
    }
    private Statusi status;
    private PosjetilacPozorista posjetilacPozorista;
    private int brojKarta;

    public static ArrayList<Karta> sveKarte = new ArrayList<>();

    public Karta(int id, int izvodjenjePredstaveId, int status, int posjetilacPozoristaId, int brojKarta) {
        this.id = id;
        this.izvodjenjePredstave = IzvodjenjePredstave.getIzvodjenjePredstaveById(izvodjenjePredstaveId);
        switch (status) {
            case 1 -> this.status = Statusi.KUPLJENA;
            case 2 -> this.status = Statusi.REZERVISANA;
            case 3 -> this.status = Statusi.REZERVISANA_PREUZETA;
            default -> {
                System.err.println("Status karte sa id: " + id + " nije vazeci!");
                return;
            }
        }
        this.posjetilacPozorista = PosjetilacPozorista.getPosjetilacPozoristaById(posjetilacPozoristaId);
        this.brojKarta = brojKarta;
        if (this.izvodjenjePredstave != null && this.posjetilacPozorista != null) {
            this.izvodjenjePredstave.setBrojRezervisanihMjesta(brojKarta);
            if (slobodno()) {
                sveKarte.add(this);
            } else {
                System.err.println("Nema vise slobodnih mjesta!");
            }
        }
    }

    private boolean slobodno() {
        return this.izvodjenjePredstave.getBrojRezervisanihMjesta() <
                this.izvodjenjePredstave.getPozoriste().getBrojSjedista();
    }

    public int getId() {
        return id;
    }

    public IzvodjenjePredstave getIzvodjenjePredstave() {
        return izvodjenjePredstave;
    }

    public Statusi getStatus() {
        return status;
    }

    public PosjetilacPozorista getPosjetilacPozorista() {
        return posjetilacPozorista;
    }

    public int getBrojKarta() {
        return brojKarta;
    }

}
