package pmf.projekatoop.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;

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

    public static void izmjenaKarte(int noviBrojKarata, int idKarte) {
        try {
            String upit = "UPDATE karta SET broj_karta = ? WHERE karta.id = ?";
            PreparedStatement izjava = UcitavanjeBaze.db.getVeza().prepareStatement(upit);
            izjava.setInt(1, noviBrojKarata);
            izjava.setInt(2, idKarte);
            izjava.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void brisanjeKarte(int idKarte) {
        try {
            String upit = "DELETE FROM karta WHERE karta.id = ?";
            PreparedStatement izjava = UcitavanjeBaze.db.getVeza().prepareStatement(upit);
            izjava.setInt(1, idKarte);
            izjava.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void izmjenaLozinkeRadnika(String novaLozinka, int idRadnikaPozorista) {
        try {
            String upit = "UPDATE radnik_pozorista SET lozinka = ? WHERE radnik_pozorista.id = ?";
            PreparedStatement izjava = UcitavanjeBaze.db.getVeza().prepareStatement(upit);
            izjava.setString(1, novaLozinka);
            izjava.setInt(2, idRadnikaPozorista);
            izjava.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int posaljiPredstava(String naziv, int zanr) {
        int id = 0;
        try {
            String upit = "INSERT INTO predstava(naziv, zanr)  VALUES (?,?)";
            PreparedStatement izjava = UcitavanjeBaze.db.getVeza().prepareStatement(upit, PreparedStatement.RETURN_GENERATED_KEYS);
            izjava.setString(1, naziv);
            izjava.setInt(2, zanr);
            izjava.executeUpdate();
            ResultSet rs = izjava.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static int posaljiOsobljePredstave(int osobljeId, int predstavaId) {
        int id = 0;
        try {
            String upit = "INSERT INTO osoblje_predstave(osoblje_id, predstava_id)  VALUES (?,?)";
            PreparedStatement izjava = UcitavanjeBaze.db.getVeza().prepareStatement(upit, PreparedStatement.RETURN_GENERATED_KEYS);
            izjava.setInt(1, osobljeId);
            izjava.setInt(2, predstavaId);
            izjava.executeUpdate();
            ResultSet rs = izjava.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static int posaljiOsoblje(String ime, String prezime, int tip) {
        int id = 0;
        try {
            String upit = "INSERT INTO osoblje(ime, prezime, tip)  VALUES (?,?,?)";
            PreparedStatement izjava = UcitavanjeBaze.db.getVeza().prepareStatement(upit, PreparedStatement.RETURN_GENERATED_KEYS);
            izjava.setString(1, ime);
            izjava.setString(2, prezime);
            izjava.setInt(3, tip);
            izjava.executeUpdate();
            ResultSet rs = izjava.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static int posaljiIzvodjenjePredstave(int predstavaId, int pozoristeId, double cijena,
                                                 Timestamp datumIVrijeme) {
        int id = 0;
        try {
            String upit = "INSERT INTO izvodjenje_predstave(predstava_id, pozoriste_id, cijena, datum_i_vrijeme)  VALUES (?,?,?,?)";
            PreparedStatement izjava = UcitavanjeBaze.db.getVeza().prepareStatement(upit, PreparedStatement.RETURN_GENERATED_KEYS);
            izjava.setInt(1, predstavaId);
            izjava.setInt(2, pozoristeId);
            izjava.setDouble(3, cijena);
            izjava.setTimestamp(4, datumIVrijeme);
            izjava.executeUpdate();
            ResultSet rs = izjava.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
