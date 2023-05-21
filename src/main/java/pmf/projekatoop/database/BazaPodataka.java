package pmf.projekatoop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BazaPodataka {

    private Connection veza = null;

    public void uspostaviVezu() {
        try {
            String dbKorisnik = "admin";
            String dbSifra = "admin1234";
            String url = "jdbc:mariadb://localhost";
            int port = 3306;
            String dbNaziv = "pozoriste";
            url = url + ":" + port + "/" + dbNaziv;
            veza = DriverManager.getConnection(url, dbKorisnik, dbSifra);
            System.out.println("Veza sa bazom podataka uspostavljena!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Prekid veze ako postoji:
    public void prekidVeze() {
        if (veza != null) {
            try {
                veza.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Veza sa bazom podataka prekinuta!");
    }

    // Vraca vezu:
    public Connection getVeza() {
        return veza;
    }

}
