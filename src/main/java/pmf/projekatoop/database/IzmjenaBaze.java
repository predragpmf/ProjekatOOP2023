package pmf.projekatoop.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class IzmjenaBaze {

    public static int posaljiPosjetilacPozorista(String ime, String prezime, String korisnickoIme, String lozinka) {
        int id = 0;
        try {
            String upit = "INSERT INTO posjetilac_pozorista(ime, prezime, korisnicko_ime, lozinka)  VALUES (?,?,?,?)";
            PreparedStatement izjava = UcitavanjeBaze.db.getVeza().prepareStatement(upit, PreparedStatement.RETURN_GENERATED_KEYS);
            izjava.setString(1, ime);
            izjava.setString(2, prezime);
            izjava.setString(3, korisnickoIme);
            izjava.setString(4, lozinka);
            izjava.executeUpdate();
            ResultSet rs = izjava.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static int posaljiKarta(int izvodjenjePredstaveId, int status, int posjetilacId, int brojKarta) {
        int id = 0;
        try {
            String upit = "INSERT INTO karta(izvodjenje_predstave_id, status, posjetilac_id, broj_karta)  VALUES (?,?,?,?)";
            PreparedStatement izjava = UcitavanjeBaze.db.getVeza().prepareStatement(upit, PreparedStatement.RETURN_GENERATED_KEYS);
            izjava.setInt(1, izvodjenjePredstaveId);
            izjava.setInt(2, status);
            izjava.setInt(3, posjetilacId);
            izjava.setInt(4, brojKarta);
            izjava.executeUpdate();
            ResultSet rs = izjava.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //TODO

}
