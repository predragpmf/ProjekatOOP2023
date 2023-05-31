package pmf.projekatoop.application;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class IzvodjenjePredstave {

    public static ArrayList<IzvodjenjePredstave> svaIzvodjenjaPredstava = new ArrayList<>();
    private int id;
    private Predstava predstava;
    private Pozoriste pozoriste;
    private double cijena;
    private Timestamp datumIVrijeme;
    private int brojRezervisanihMjesta;

    public IzvodjenjePredstave(int id, int predstavaId, int pozoristeId, double cijena, Timestamp datumIVrijeme) {
        this.id = id;
        this.predstava = Predstava.getPredstavaById(predstavaId);
        this.pozoriste = Pozoriste.getPozoristeById(pozoristeId);
        this.cijena = cijena;
        this.datumIVrijeme = datumIVrijeme;
        this.brojRezervisanihMjesta = 0;
        if (this.predstava != null && this.pozoriste != null) {
            if (!vecPostoji()) {
                for (OsobljePredstave op : OsobljePredstave.svoOsobljePredstava) {
                    if (op.getPredstava().equals(this.predstava)) {
                        op.getOsoblje().povecajBrojIzvodjenja();
                    }
                }
                svaIzvodjenjaPredstava.add(this);
            } else {
                System.err.println("Termin izvodjenja predstave: " + id + " zauzet!");
            }
        }
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

    public static ArrayList<IzvodjenjePredstave> getNarednePredstavePozorista(Pozoriste p) {
        ArrayList<IzvodjenjePredstave> predstave = new ArrayList<>();
        for (IzvodjenjePredstave ip : svaIzvodjenjaPredstava) {
            if (ip.getPozoriste().equals(p)) {
                if (ip.getDatumIVrijeme().getTime() > System.currentTimeMillis()) {
                    predstave.add(ip);
                }
            }
        }
        return predstave;
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

    @Override
    public String toString() {
        Date datum = new Date(this.getDatumIVrijeme().getTime());
        return this.predstava.getNaziv() + ", " + this.pozoriste.getNaziv() + ", " + datum;
    }
}
