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

    public static Osoblje getOsobljeByTip(int idPredstave, String tip) {
        for (OsobljePredstave op : svoOsobljePredstava) {
            if (op.getPredstava().getId() == idPredstave) {
                if (op.getOsoblje().getTip().toString().equals(tip)) {
                    return op.getOsoblje();
                }
            }
        }
        return null;
    }

    public static ArrayList<Osoblje> getGlumciByPredstavaId(int idPredstave) {
        ArrayList<Osoblje> glumci = new ArrayList<>();
        for (OsobljePredstave op : svoOsobljePredstava) {
            if (op.getPredstava().getId() == idPredstave) {
                if (op.getOsoblje().getTip().toString().equals("GLUMAC")) {
                    glumci.add(op.getOsoblje());
                }
            }
        }
        return glumci;
    }

    public static ArrayList<Osoblje> getSvoOsobljeByPredstavaId(int idPredstave) {
        ArrayList<Osoblje> osoblje = new ArrayList<>();
        for (OsobljePredstave op : svoOsobljePredstava) {
            if (op.getPredstava().getId() == idPredstave) {
                osoblje.add(op.getOsoblje());
            }
        }
        return osoblje;
    }

    public static ArrayList<Predstava> getPredstaveByOsobljeId(int osobljeId) {
        ArrayList<Predstava> predstave = new ArrayList<>();
        for (OsobljePredstave op : svoOsobljePredstava) {
            if (op.getOsoblje().getId() == osobljeId) {
                predstave.add(op.getPredstava());
            }
        }
        return predstave;
    }

}
