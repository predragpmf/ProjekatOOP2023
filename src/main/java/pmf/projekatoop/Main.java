package pmf.projekatoop;

import pmf.projekatoop.database.UcitavanjeBaze;
import pmf.projekatoop.gui.MainGUI;

public class Main {
    public static void main(String[] args) {
        // Ucitaj bazu podataka:
        UcitavanjeBaze.ucitavanje();

        // Pokreni GUI:
        MainGUI.main(args);

        // Prekini vezu sa bazom podataka:
        UcitavanjeBaze.db.prekidVeze();
    }
}
