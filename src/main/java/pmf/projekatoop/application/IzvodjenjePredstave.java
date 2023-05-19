package pmf.projekatoop.application;

import java.sql.Timestamp;
import java.util.ArrayList;

public class IzvodjenjePredstave {

    private int id;
    private Predstava predstava;
    private Pozoriste pozoriste;
    private double cijena;
    private Timestamp datumIVrijeme;
    private int brojRezervisanihMjesta;

    public static ArrayList<IzvodjenjePredstave> svaIzvodjenjaPredstava = new ArrayList<>();

    public IzvodjenjePredstave(int id, int predstavaId, int pozoristeId, double cijena, Timestamp datumIVrijeme) {
        this.id = id;
        this.predstava = Predstava.getPredstavaById(predstavaId);
        this.pozoriste = Pozoriste.getPozoristeById(pozoristeId);
        this.cijena = cijena;
        this.datumIVrijeme = datumIVrijeme;
        this.brojRezervisanihMjesta = 0;
        if (this.predstava != null && this.pozoriste != null) {
            if (!vecPostoji()) {
                svaIzvodjenjaPredstava.add(this);
            } else {
                System.err.println("Termin izvodjenja predstave: " + id + " zauzet!");
            }
        }
    }

    private boolean vecPostoji() {
        for (IzvodjenjePredstave ip : svaIzvodjenjaPredstava) {
            if (ip.getDatumIVrijeme().getTime() == this.getDatumIVrijeme().getTime() &&
                    ip.getPozoriste().getId() == this.pozoriste.getId()) {
                return true;
            }
        }
        return false;
    }

    public static IzvodjenjePredstave getIzvodjenjePredstaveById(int izvodjenjePredstaveId) {
        for (IzvodjenjePredstave ip : svaIzvodjenjaPredstava) {
            if (ip.getId() == izvodjenjePredstaveId) {
                return ip;
            }
        }
        System.err.println("(.getIzvodjenjePredstaveById) Izvodjenje predstave sa id: " + izvodjenjePredstaveId +
                " nije pronadjeno!");
        return null;
    }

    public int getId() {
        return id;
    }

    public Predstava getPredstava() {
        return predstava;
    }

    public Pozoriste getPozoriste() {
        return pozoriste;
    }

    public double getCijena() {
        return cijena;
    }

    public Timestamp getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public int getBrojRezervisanihMjesta() {
        return brojRezervisanihMjesta;
    }

    public void setBrojRezervisanihMjesta(int brojRezervisanihMjesta) {
        this.brojRezervisanihMjesta += brojRezervisanihMjesta;
    }

}
