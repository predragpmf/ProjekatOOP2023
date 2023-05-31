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
    public ListView<String> ktListView;
    public ListView<String> infoListView;
    public TextArea infoTextArea;
    public ComboBox<String> ktComboBox;
    public Spinner<Integer> ktSpinner;
    public Button ktOtkaziButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Rezervacija tab:
        for (String s : Pozoriste.getSpisakPozorista()) {
            rezTextArea.appendText(s);
            rezPozoristaCombo.getItems().add(s);
        }

        // Karte tab:
        ktComboBox.getItems().add("Rezervisane karte");
        ktComboBox.getItems().add("Istekle karte");
        ktOtkaziButton.setVisible(false);
        ktSpinner.setVisible(false);

        // Informacije tab:
        for (Osoblje o : Osoblje.svoOsoblje) {
            infoListView.getItems().add(o.getId() + ". " + o);
        }
    }

    // ComboBox za odabir pozorista:
    public void odabirPozorista() {
        if (rezPozoristaCombo.getValue() != null) {
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
    }

    // ComboBox za odabir predstave:
    public void odabirPredstave() {
        rezOsobljeCombo.getItems().clear();
        rezTextArea.clear();

        if (rezPredstaveCombo.getValue() != null) {
            int izvodjenjePredstaveId = Integer.parseInt(rezPredstaveCombo.getValue().split("\\.")[0]);
            IzvodjenjePredstave odabranoIzvodjenje = IzvodjenjePredstave
                    .getIzvodjenjePredstaveById(izvodjenjePredstaveId);
            if (odabranoIzvodjenje != null) {
                Predstava odabranaPredstava = odabranoIzvodjenje.getPredstava();

                Osoblje autor = OsobljePredstave.getOsobljeByTip(odabranaPredstava.getId(), "AUTOR");
                Osoblje reziser = OsobljePredstave.getOsobljeByTip(odabranaPredstava.getId(), "REZISER");

                rezTextArea.appendText("Naziv: " + odabranaPredstava.getNaziv() + "\n");
                rezTextArea.appendText("Žanr: " + odabranaPredstava.getZanr().toString() + "\n");
                rezTextArea.appendText("Cijena: " + odabranoIzvodjenje.getCijena() + "KM \n");
                rezTextArea.appendText("Termin: " + new Date(odabranoIzvodjenje.getDatumIVrijeme().getTime()) + "\n");
                if (autor != null && reziser != null) {
                    rezTextArea.appendText("Autor: " + autor + "\n");
                    rezTextArea.appendText("Režiser: " + reziser + "\n");
                } else {
                    System.err.println("Osoblje nije pronadjeno!");
                }
                rezTextArea.appendText("Glumci: \n");
                for (Osoblje osoblje : OsobljePredstave.getGlumciByPredstavaId(odabranaPredstava.getId())) {
                    rezTextArea.appendText(osoblje + "\n");
                }
                for (Osoblje osoblje : OsobljePredstave.getSvoOsobljeByPredstavaId(odabranaPredstava.getId())) {
                    rezOsobljeCombo.getItems().add(osoblje.getId() + ". " + osoblje);
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
    }

    // ComboBox za odabir osoblja:
    public void odabirOsoblja() {
        rezTextArea.clear();
        if (rezOsobljeCombo.getValue() != null) {
            int osobljeId = Integer.parseInt(rezOsobljeCombo.getValue().split("\\.")[0]);
            Osoblje osoblje = Osoblje.getOsobljeById(osobljeId);
            if (osoblje != null) {
                rezTextArea.appendText(osoblje + "\n");
                rezTextArea.appendText(osoblje.getTip().toString() + "\n");
                rezTextArea.appendText("Predstave:\n");
                for (Predstava predstava : OsobljePredstave.getPredstaveByOsobljeId(osobljeId)) {
                    rezTextArea.appendText(predstava.getNaziv() + "\n");
                }
            }
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
                    int postojiId = Karta.vecPostojiKarta(Korisnik.prijavljeniKorisnik.getId(), izvodjenjePredstaveId);
                    if (postojiId != -1) {
                        Karta karta = Karta.getKartaById(postojiId);
                        if (karta != null) {
                            IzmjenaBaze.izmjenaKarte(karta.getBrojKarta() + brojKarata, postojiId);
                            karta.setBrojKarta(karta.getBrojKarta() + brojKarata);
                        } else {
                            System.err.println("Karta ne postoji! (Posjetilac.rezervisiTipka())");
                        }
                    } else {
                        int id = IzmjenaBaze.posaljiKarta(izvodjenjePredstaveId, 2,
                                Korisnik.prijavljeniKorisnik.getId(), brojKarata);
                        new Karta(id, izvodjenjePredstaveId, 2, Korisnik.prijavljeniKorisnik.getId(), brojKarata);
                    }
                    if (ip.getDatumIVrijeme().getTime() < 172800000) {
                        prozorObavjestenja("Upozorenje!",
                                "Ostalo je manje od 48 sati da preuzmete rezervisanu kartu");
                    }
                    prozorObavjestenja("Gotovo", "Karta uspješno rezervisana!");
                } else {
                    prozorObavjestenja("Greška", "Nije dostupno nijedno slobodno mjesto.");
                }
            } else {
                System.err.println("Odabrano izvodjenje predstave nije pronadjeno!");
            }
        }
    }

    // Odaberi tip karte ComboBox:
    public void odabirTipaKarte() {
        ktListView.getItems().clear();
        if (ktComboBox.getValue().equals("Rezervisane karte")) {
            ktOtkaziButton.setVisible(true);
            ktSpinner.setVisible(true);
            for (Karta k : Karta.getKarteByPosjetilacId(Korisnik.prijavljeniKorisnik.getId())) {
                IzvodjenjePredstave ip = k.getIzvodjenjePredstave();
                if (ip.getDatumIVrijeme().getTime() > System.currentTimeMillis()) {
                    ktListView.getItems().add(k.getId() + ". " + ip + ", karte: " + k.getBrojKarta());
                }
            }
        } else if (ktComboBox.getValue().equals("Istekle karte")) {
            ktOtkaziButton.setVisible(false);
            ktSpinner.setVisible(false);
            ktSpinner.setValueFactory(null);
            for (Karta k : Karta.getKarteByPosjetilacId(Korisnik.prijavljeniKorisnik.getId())) {
                IzvodjenjePredstave ip = k.getIzvodjenjePredstave();
                if (ip.getDatumIVrijeme().getTime() < System.currentTimeMillis()) {
                    ktListView.getItems().add(k.getId() + ". " + ip + ", karte: " + k.getBrojKarta());
                }
            }
        }
    }

    // Informacije - odabir infoListView
    public void odabirInformacije() {
        infoTextArea.clear();
        int osobljeId = Integer.parseInt(infoListView.getSelectionModel().getSelectedItem().split("\\.")[0]);
        Osoblje osoblje = Osoblje.getOsobljeById(osobljeId);
        if (osoblje != null) {
            infoTextArea.appendText(osoblje + "\n");
            infoTextArea.appendText(osoblje.getTip().toString() + "\n");
            infoTextArea.appendText("Predstave:\n");
            for (Predstava predstava : OsobljePredstave.getPredstaveByOsobljeId(osobljeId)) {
                infoTextArea.appendText(predstava.getNaziv() + "\n");
            }
        }
    }

    // Karte - odabir ktListView
    public void odaberiKarte() {
        if (ktComboBox.getValue().equals("Rezervisane karte")) {
            String karta = ktListView.getSelectionModel().getSelectedItem();
            int maksBrojKarata = Integer.parseInt(karta.substring(karta.length() - 1));
            SpinnerValueFactory<Integer> brojKarata = new SpinnerValueFactory
                    .IntegerSpinnerValueFactory(1, maksBrojKarata, 1);
            ktSpinner.setValueFactory(brojKarata);
        }
    }

    // Karte - otkazivanje tipka
    public void otkaziRezervaciju() {
        int idKarte = Integer.parseInt(ktListView.getSelectionModel().getSelectedItem().split("\\.")[0]);
        Karta karta = Karta.getKartaById(idKarte);
        int brojKarata = ktSpinner.getValue();
        if (karta != null) {
            IzvodjenjePredstave ip = karta.getIzvodjenjePredstave();
            if (ip != null) {
                if ((System.currentTimeMillis() + 172800000) < ip.getDatumIVrijeme().getTime()) {
                    if (brojKarata == karta.getBrojKarta()) {
                        IzmjenaBaze.brisanjeKarte(idKarte);
                        Karta.sveKarte.remove(karta);
                    } else {
                        IzmjenaBaze.izmjenaKarte(karta.getBrojKarta() - brojKarata, idKarte);
                        karta.setBrojKarta(karta.getBrojKarta() - brojKarata);
                    }
                    prozorObavjestenja("Gotovo", "Karte uspješno otkazane!");
                    odabirTipaKarte();
                } else {
                    prozorObavjestenja("Otkazivanje nije moguće", "Predstava počinje za manje od 48 sati!");
                }
            } else {
                System.err.println("Izvodjenje predstave ne postoji! (Posjetilac.otkaziRezervaciju())");
            }
        } else {
            System.err.println("Karta ne postoji!");
        }
    }

    public void odjavaTipka(ActionEvent event) {
        promijeniScenuLogin(event);
    }

}
