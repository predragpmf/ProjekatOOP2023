package pmf.projekatoop.database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class UcitavanjeBaze {

    public static BazaPodataka db = new BazaPodataka();

    public static void ucitavanje() {
        db.uspostaviVezu();
        ucitajPozoriste();
        ucitajRadnikPozorista();
        ucitajOsoblje();
        ucitajPredstava();
        ucitajOsobljePredstave();
        ucitajPosjetilacPozorista();
        ucitajIzvodjenjePredstave();
        ucitajKarta();
        System.out.println("Ucitavanje baze zavrseno!");
    }

    private static void ucitajPozoriste() {
        try {
            Statement izjava = db.getVeza().createStatement();
            ResultSet setRezultata = izjava.executeQuery("select * from pozoriste");
            while (setRezultata.next()) {
                int id = setRezultata.getInt("id");
                String naziv = setRezultata.getString("naziv");
                String grad = setRezultata.getString("grad");
                int brojSjedista = setRezultata.getInt("broj_sjedista");
                new pmf.projekatoop.application.Pozoriste(id, naziv, grad, brojSjedista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ucitajRadnikPozorista() {
        try {
            Statement izjava = db.getVeza().createStatement();
            ResultSet setRezultata = izjava.executeQuery("select * from radnik_pozorista");
            while (setRezultata.next()) {
                int id = setRezultata.getInt("id");
                String ime = setRezultata.getString("ime");
                String prezime = setRezultata.getString("prezime");
                String korisnickoIme = setRezultata.getString("korisnicko_ime");
                String lozinka = setRezultata.getString("lozinka");
                int pozoristeId = setRezultata.getInt("pozoriste_id");
                new pmf.projekatoop.application.RadnikPozorista(id, ime, prezime, korisnickoIme, lozinka, pozoristeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ucitajOsoblje() {
        try {
            Statement izjava = db.getVeza().createStatement();
            ResultSet setRezultata = izjava.executeQuery("select * from osoblje");
            while (setRezultata.next()) {
                int id = setRezultata.getInt("id");
                String ime = setRezultata.getString("ime");
                String prezime = setRezultata.getString("prezime");
                int tip = setRezultata.getInt("tip");
                new pmf.projekatoop.application.Osoblje(id, ime, prezime, tip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ucitajPredstava() {
        try {
            Statement izjava = db.getVeza().createStatement();
            ResultSet setRezultata = izjava.executeQuery("select * from predstava");
            while (setRezultata.next()) {
                int id = setRezultata.getInt("id");
                String naziv = setRezultata.getString("naziv");
                int zanr = setRezultata.getInt("zanr");
                new pmf.projekatoop.application.Predstava(id, naziv, zanr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ucitajOsobljePredstave() {
        try {
            Statement izjava = db.getVeza().createStatement();
            ResultSet setRezultata = izjava.executeQuery("select * from osoblje_predstave");
            while (setRezultata.next()) {
                int id = setRezultata.getInt("id");
                int osobljeId = setRezultata.getInt("osoblje_id");
                int predstavaId = setRezultata.getInt("predstava_id");
                new pmf.projekatoop.application.OsobljePredstave(id, osobljeId, predstavaId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ucitajPosjetilacPozorista() {
        try {
            Statement izjava = db.getVeza().createStatement();
            ResultSet setRezultata = izjava.executeQuery("select * from posjetilac_pozorista");
            while (setRezultata.next()) {
                int id = setRezultata.getInt("id");
                String ime = setRezultata.getString("ime");
                String prezime = setRezultata.getString("prezime");
                String korisnickoIme = setRezultata.getString("korisnicko_ime");
                String lozinka = setRezultata.getString("lozinka");
                new pmf.projekatoop.application.PosjetilacPozorista(id, ime, prezime, korisnickoIme, lozinka);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ucitajIzvodjenjePredstave() {
        try {
            Statement izjava = db.getVeza().createStatement();
            ResultSet setRezultata = izjava.executeQuery("select * from izvodjenje_predstave");
            while (setRezultata.next()) {
                int id = setRezultata.getInt("id");
                int predstavaId = setRezultata.getInt("predstava_id");
                int pozoristeId = setRezultata.getInt("pozoriste_id");
                double cijena = setRezultata.getDouble("cijena");
                Timestamp datumIVrijeme = setRezultata.getTimestamp("datum_i_vrijeme");
                new pmf.projekatoop.application.IzvodjenjePredstave(id, predstavaId, pozoristeId, cijena,
                        datumIVrijeme);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ucitajKarta() {
        try {
            Statement izjava = db.getVeza().createStatement();
            ResultSet setRezultata = izjava.executeQuery("select * from karta");
            while (setRezultata.next()) {
                int id = setRezultata.getInt("id");
                int izvodjenjePredstaveId = setRezultata.getInt("izvodjenje_predstave_id");
                int status = setRezultata.getInt("status");
                int posjetilacId = setRezultata.getInt("posjetilac_id");
                int brojKarta = setRezultata.getInt("broj_karta");
                new pmf.projekatoop.application.Karta(id, izvodjenjePredstaveId, status, posjetilacId, brojKarta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
