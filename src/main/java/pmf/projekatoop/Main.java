package pmf.projekatoop;

import pmf.projekatoop.database.UcitavanjeBaze;
import pmf.projekatoop.gui.MainGUI;

public class Main {
    public static void main(String[] args) {
        UcitavanjeBaze.ucitavanje();
        MainGUI.main(args);
        UcitavanjeBaze.db.prekidVeze();
    }
}
