package pmf.projekatoop.application;

import java.util.ArrayList;

public class OsobljePredstave {

    private int id;
    private Osoblje osoblje;
    private Predstava predstava;

    public static ArrayList<OsobljePredstave> svoOsobljePredstava = new ArrayList<>();

    public OsobljePredstave(int id, int osobljeId, int predstavaId) {
        this.id = id;
        this.osoblje = Osoblje.getOsobljeById(osobljeId);
        if (this.osoblje != null) {
            this.predstava = Predstava.getPredstavaById(predstavaId);
            if (this.predstava != null) {
                if (!vecPostoji()) {
                    svoOsobljePredstava.add(this);
                } else {
                    System.err.println("OsobljePredstave sa osoblje id: " + osobljeId + " i predstava id: " +
                            predstavaId + " vec postoji!");
                }
            }
        }
    }

    private boolean vecPostoji() {
        for (OsobljePredstave op : svoOsobljePredstava) {
            if (op.getOsoblje().getId() == this.osoblje.getId() &&
                    op.getPredstava().getId() == this.predstava.getId()) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public Osoblje getOsoblje() {
        return osoblje;
    }

    public Predstava getPredstava() {
        return predstava;
    }

}
