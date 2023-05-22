package pmf.projekatoop.gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pmf.projekatoop.application.*;
import pmf.projekatoop.database.IzmjenaBaze;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class Posjetilac extends Controller implements Initializable {

    public ComboBox<String> rezPozoristaCombo;
    public ComboBox<String> rezPredstaveCombo;
    public ComboBox<String> rezOsobljeCombo;
    public TextArea rezTextArea;
    public Spinner<Integer> rezBrojKarataSpinner;
    public TextArea ktNaredneTextArea;
    public TextArea ktProsleTextArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rezTextArea.clear();
        ktProsleTextArea.clear();
        ktNaredneTextArea.clear();
        for (String s : Pozoriste.getSpisakPozorista()) {
            rezTextArea.appendText(s);
            rezPozoristaCombo.getItems().add(s);
        }
        ucitajKarte();
    }

    // Ispisuje podatke o kupljenim kartama:
    private void ucitajKarte() {
        for (Karta k : Karta.getKarteByPosjetilacId(Korisnik.prijavljeniKorisnik.getId())) {
            Predstava p = k.getIzvodjenjePredstave().getPredstava();
            Date datum = new Date(k.getIzvodjenjePredstave().getDatumIVrijeme().getTime());
            if (k.getIzvodjenjePredstave().getDatumIVrijeme().getTime() > System.currentTimeMillis()) {
                ktNaredneTextArea.appendText(p.getNaziv() + ", " + datum + ", karte: " + k.getBrojKarta() + "\n");
            } else {
                ktProsleTextArea.appendText(p.getNaziv() + ", " + datum + ", karte: " + k.getBrojKarta() + "\n");
            }
        }
    }

    // ComboBox za odabir pozorista:
    public void odabirPozorista() {
        int pozoristeId = Integer.parseInt(rezPozoristaCombo.getValue().split("\\.")[0]);
        Pozoriste odabranoPozoriste = Pozoriste.getPozoristeById(pozoristeId);
        rezPredstaveCombo.getItems().clear();
        rezOsobljeCombo.getItems().clear();
        rezTextArea.clear();
        for (IzvodjenjePredstave ip : IzvodjenjePredstave.getNarednePredstavePozorista(odabranoPozoriste)) {
            Date datum = new Date(ip.getDatumIVrijeme().getTime());
            String tekst = ip.getId() + ". " + ip.getPredstava().getNaziv() + ", " + datum + "\n";
            rezTextArea.appendText(tekst);
            rezPredstaveCombo.getItems().add(tekst);
        }
    }

    // ComboBox za odabir predstave:
    public void odabirPredstave() {
        rezOsobljeCombo.getItems().clear();


        int izvodjenjePredstaveId = Integer.parseInt(rezPredstaveCombo.getValue().split("\\.")[0]);
        IzvodjenjePredstave odabranoIzvodjenje = IzvodjenjePredstave.getIzvodjenjePredstaveById(izvodjenjePredstaveId);
        if (odabranoIzvodjenje != null) {
            Predstava odabranaPredstava = odabranoIzvodjenje.getPredstava();

            Osoblje autor = OsobljePredstave.getOsobljeByTip(odabranaPredstava.getId(), "AUTOR");
            Osoblje reziser = OsobljePredstave.getOsobljeByTip(odabranaPredstava.getId(), "REZISER");

            rezTextArea.appendText("Naziv: " + odabranaPredstava.getNaziv() + "\n");
            rezTextArea.appendText("Žanr: " + odabranaPredstava.getZanr().toString() + "\n");
            rezTextArea.appendText("Cijena: " + odabranoIzvodjenje.getCijena() + "KM \n");
            rezTextArea.appendText("Termin: " + new Date(odabranoIzvodjenje.getDatumIVrijeme().getTime()) + "\n");
            if (autor != null && reziser != null) {
                rezTextArea.appendText("Autor: " + autor.getIme() + " " + autor.getPrezime() + "\n");
                rezTextArea.appendText("Režiser: " + reziser.getIme() + " " + reziser.getPrezime() + "\n");
            } else {
                System.err.println("Osoblje nije pronadjeno!");
            }
            rezTextArea.appendText("Glumci: \n");
            for (Osoblje o : OsobljePredstave.getGlumciByPredstavaId(odabranaPredstava.getId())) {
                rezTextArea.appendText(o.getIme() + " " + o.getPrezime() + "\n");
            }
            for (Osoblje o : OsobljePredstave.getSvoOsobljeByPredstavaId(odabranaPredstava.getId())) {
                rezOsobljeCombo.getItems().add(o.getId() + ". " + o.getIme() + " " + o.getPrezime());
            }

            int maksBrojKarata = odabranoIzvodjenje.getPozoriste().getBrojSjedista() -
                    odabranoIzvodjenje.getBrojRezervisanihMjesta();
            SpinnerValueFactory<Integer> brojKarata = new SpinnerValueFactory
                    .IntegerSpinnerValueFactory(1, maksBrojKarata, 1);
            rezBrojKarataSpinner.setValueFactory(brojKarata);
        } else {
            System.err.println("Odabrano izvodjenje predstave nije pronadjeno!");
        }
    }

    // ComboBox za odabir osoblja:
    public void odabirOsoblja() {
        rezTextArea.clear();
        int osobljeId = Integer.parseInt(rezOsobljeCombo.getValue().split("\\.")[0]);
        Osoblje osoblje = Osoblje.getOsobljeById(osobljeId);
        rezTextArea.appendText(osoblje.getIme() + " " + osoblje.getPrezime() + "\n");
        rezTextArea.appendText(osoblje.getTip().toString() + "\n");
        rezTextArea.appendText("Predstave:\n");
        for (Predstava p : OsobljePredstave.getPredstaveByOsobljeId(osobljeId)) {
            rezTextArea.appendText(p.getNaziv() + "\n");
        }
    }

    // Tipka za rezervisanje karata:
    public void rezervisiTipka() {
        if (!rezPredstaveCombo.getSelectionModel().isEmpty()) {
            int izvodjenjePredstaveId = Integer.parseInt(rezPredstaveCombo.getValue().split("\\.")[0]);
            IzvodjenjePredstave ip = IzvodjenjePredstave.getIzvodjenjePredstaveById(izvodjenjePredstaveId);
            if (ip != null) {
                int brojKarata = rezBrojKarataSpinner.getValue();
                if (ip.getBrojRezervisanihMjesta() < ip.getPozoriste().getBrojSjedista()) {
                    int id = IzmjenaBaze.posaljiKarta(izvodjenjePredstaveId, 2, Korisnik.prijavljeniKorisnik.getId(),
                            brojKarata);
                    new Karta(id, izvodjenjePredstaveId, 2, Korisnik.prijavljeniKorisnik.getId(), brojKarata);
                    if (ip.getDatumIVrijeme().getTime() < 172800000) {
                        prozorObavjestenja("Upozorenje!",
                                "Ostalo je manje od 48 sati da prezmete rezervisanu kartu");
                    }
                    ktProsleTextArea.clear();
                    ktNaredneTextArea.clear();
                    ucitajKarte();
                    System.out.println("Karta kupljena!");
                } else {
                    prozorObavjestenja("Greška", "Nije dostupno nijedno slobodno mjesto.");
                }
            } else {
                System.err.println("Odabrano izvodjenje predstave nije pronadjeno!");
            }
        }
    }

    public void nazadTipka(ActionEvent event) {
        promijeniScenuLogin(event);
    }

}
