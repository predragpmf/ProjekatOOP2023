package pmf.projekatoop.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pmf.projekatoop.application.*;
import pmf.projekatoop.database.IzmjenaBaze;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class Radnik extends Controller implements Initializable {

    public TextArea infoTextArea;
    public PasswordField infoStaraTextBox;
    public PasswordField infoNovaTextBox;
    public ListView<String> pozoristeListView;
    public ComboBox<String> pozoristeComboBox;
    public TextArea pozoristeTextArea;
    public TextArea pozoristeKtTextArea;
    public Spinner<Integer> ktSpinner;
    public ListView<String> ktIpListView;
    public ListView<String> ktPpListView;
    public TextField predNazivTextBox;
    public ComboBox<String> predZanrComboBox;
    public ListView<String> predAutorListView;
    public ListView<String> predReziserListView;
    public ListView<String> predGlumciListView;
    public TextField predImeTextBox;
    public TextField predPrezimeTextBox;
    public ComboBox<String> predTipComboBox;
    public ListView<String> tmPredstaveListView;
    public TextArea tmTextArea;
    public DatePicker tmDatum;
    public TextField tmSati;
    public TextField tmMinute;
    public TextField tmCijena;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!(Korisnik.prijavljeniKorisnik instanceof RadnikPozorista)) {
            System.err.println("Pogresan korisnik prijavljen!");
        }
        RadnikPozorista rp = (RadnikPozorista) Korisnik.prijavljeniKorisnik;
        infoTextArea.appendText("Ime i prezime:\n");
        infoTextArea.appendText("\t" + rp.getIme() + " " + rp.getPrezime() + "\n\n");
        infoTextArea.appendText("Korisničko ime:\n");
        infoTextArea.appendText("\t" + rp.getKorisnickoIme() + "\n\n");
        infoTextArea.appendText("Pozorište:\n");
        infoTextArea.appendText("\t" + rp.getPozoriste().getNaziv() + "\n\n");
        infoTextArea.appendText("Grad:\n");
        infoTextArea.appendText("\t" + rp.getPozoriste().getGrad());

        pozoristeComboBox.getItems().add("Naredne");
        pozoristeComboBox.getItems().add("Prošle");

        for (PosjetilacPozorista pp : PosjetilacPozorista.sviPosjetiociPozorista) {
            ktPpListView.getItems().add(pp.getId() + ". " + pp.getIme() + " " + pp.getPrezime() + " - " +
                    pp.getKorisnickoIme());
        }
        ispisiIzvodjenjaPredstava();

        predZanrComboBox.getItems().add("Komedija");
        predZanrComboBox.getItems().add("Farsa");
        predZanrComboBox.getItems().add("Satira");
        predZanrComboBox.getItems().add("Komedija_restauracije");
        predZanrComboBox.getItems().add("Tragedija");
        predZanrComboBox.getItems().add("Istorija");
        predZanrComboBox.getItems().add("Mjuzikl");
        predTipComboBox.getItems().add("Autor");
        predTipComboBox.getItems().add("Reziser");
        predTipComboBox.getItems().add("Glumac");

        ispisiOsoblje();

        for (Predstava p : Predstava.svePredstave) {
            tmPredstaveListView.getItems().add(p.getNaziv());
        }

        tmSati.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tmSati.setText(newValue.replaceAll("\\D", ""));
                }
            }
        });

        tmMinute.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tmSati.setText(newValue.replaceAll("\\D", ""));
                }
            }
        });
    }

    private void ispisiIzvodjenjaPredstava() {
        ktIpListView.getItems().clear();
        Pozoriste pozoriste = ((RadnikPozorista) Korisnik.prijavljeniKorisnik).getPozoriste();
        for (IzvodjenjePredstave ip : IzvodjenjePredstave.svaIzvodjenjaPredstava) {
            if (ip.getPozoriste().equals(pozoriste) && ip.getDatumIVrijeme().getTime() > System.currentTimeMillis()) {
                ktIpListView.getItems().add(ip.getId() + ". " + ip.getPredstava().getNaziv() + " " +
                        ip.getDatumIVrijeme());
            }
        }
    }

    private void ispisiOsoblje() {
        predAutorListView.getItems().clear();
        predReziserListView.getItems().clear();
        predGlumciListView.getItems().clear();
        ObservableList<String> list = FXCollections.observableArrayList();
        predGlumciListView.setItems(list);
        for (Osoblje o : Osoblje.svoOsoblje) {
            if (o.getTip().toString().equals("AUTOR")) {
                predAutorListView.getItems().add(o.toString());
            } else if (o.getTip().toString().equals("REZISER")) {
                predReziserListView.getItems().add(o.toString());
            } else if (o.getTip().toString().equals("GLUMAC")) {
                list.add(o.toString());
            }
        }
        predGlumciListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void nazadTipka(ActionEvent event) {
        promijeniScenuLogin(event);
    }

    public void promjeniLozinku() {
        if (infoStaraTextBox.getText().isEmpty()) {
            prozorObavjestenja("Greška", "Polje za staru lozinku je prazno!");
        } else if (infoNovaTextBox.getText().isEmpty()) {
            prozorObavjestenja("Greška", "Polje za novu lozinku je prazno!");
        } else {
            String lozinka = Korisnik.prijavljeniKorisnik.getLozinka();
            String staraLozinka = hesirajLozinku(infoStaraTextBox.getText());
            if (lozinka.equals(staraLozinka)) {
                String novaLozinka = infoNovaTextBox.getText();
                if (novaLozinka.length() < 5) {
                    prozorObavjestenja("Greška", "Lozinka mora biti duža od 5 karaktera!");
                } else {
                    String novaLozinkaHes = hesirajLozinku(novaLozinka);
                    Korisnik.prijavljeniKorisnik.setLozinka(novaLozinkaHes);
                    IzmjenaBaze.izmjenaLozinkeRadnika(novaLozinkaHes, Korisnik.prijavljeniKorisnik.getId());
                    prozorObavjestenja("Gotovo", "Lozinka uspješno promijenjena!");
                    infoStaraTextBox.clear();
                    infoNovaTextBox.clear();
                }
            } else {
                prozorObavjestenja("Greška", "Pogrešna stara lozinka!");
            }
        }
    }

    public void odabirVrstePredstave() {
        pozoristeListView.getItems().clear();
        pozoristeTextArea.clear();
        pozoristeKtTextArea.clear();
        RadnikPozorista rp = (RadnikPozorista) Korisnik.prijavljeniKorisnik;
        Pozoriste pozoriste = rp.getPozoriste();
        if (pozoristeComboBox.getValue().equals("Naredne")) {
            for (IzvodjenjePredstave ip : IzvodjenjePredstave.svaIzvodjenjaPredstava) {
                if (ip.getPozoriste().equals(pozoriste) && ip.getDatumIVrijeme().getTime() > System.currentTimeMillis()) {
                    pozoristeListView.getItems().add(ip.getId() + ". " + ip.getPredstava().getNaziv() + " " +
                            ip.getDatumIVrijeme());
                }
            }
        } else if (pozoristeComboBox.getValue().equals("Prošle")) {
            for (IzvodjenjePredstave ip : IzvodjenjePredstave.svaIzvodjenjaPredstava) {
                if (ip.getPozoriste().equals(pozoriste) && ip.getDatumIVrijeme().getTime() < System.currentTimeMillis()) {
                    pozoristeListView.getItems().add(ip.getId() + ". " + ip.getPredstava().getNaziv() + " " +
                            ip.getDatumIVrijeme());
                }
            }
        }
    }

    public void odabirPredstave() {
        pozoristeTextArea.clear();
        pozoristeKtTextArea.clear();
        String odabir = pozoristeListView.getSelectionModel().getSelectedItem();
        int id = Integer.parseInt(odabir.split("\\.")[0]);
        IzvodjenjePredstave ip = IzvodjenjePredstave.getIzvodjenjePredstaveById(id);
        pozoristeTextArea.appendText("Naziv: " + ip.getPredstava().getNaziv() + "\n");
        pozoristeTextArea.appendText("Žanr: " + ip.getPredstava().getZanr().toString() + "\n");
        pozoristeTextArea.appendText("Termin: " + ip.getDatumIVrijeme() + "\n");
        pozoristeTextArea.appendText("Cijena: " + ip.getCijena() + "KM\n");
        int brojMjesta = ip.getPozoriste().getBrojSjedista() - ip.getBrojRezervisanihMjesta();
        pozoristeTextArea.appendText("Broj slobodnih sjedišta: " + brojMjesta + "\n");
        Osoblje autor = OsobljePredstave.getOsobljeByTip(ip.getPredstava().getId(), "AUTOR");
        Osoblje reziser = OsobljePredstave.getOsobljeByTip(ip.getPredstava().getId(), "REZISER");
        if (autor != null && reziser != null) {
            pozoristeTextArea.appendText("Autor: " + autor + "\n");
            pozoristeTextArea.appendText("Režiser: " + reziser + "\n");
        } else {
            System.err.println("Osoblje nije pronadjeno!");
        }
        pozoristeTextArea.appendText("Glumci: \n");
        for (Osoblje osoblje : OsobljePredstave.getGlumciByPredstavaId(ip.getPredstava().getId())) {
            pozoristeTextArea.appendText(osoblje + "\n");
        }

        for (Karta k : Karta.sveKarte) {
            if (k.getIzvodjenjePredstave().equals(ip)) {
                PosjetilacPozorista pp = k.getPosjetilacPozorista();
                pozoristeKtTextArea.appendText(pp.getId() + ". " + pp.getIme() + " " + pp.getPrezime() + "\n");
            }
        }

    }

    public void odabirIzvodjenjaPredstave() {
        String odabirIp = ktIpListView.getSelectionModel().getSelectedItem();
        int idIzvodjenjaPredstave = Integer.parseInt(odabirIp.split("\\.")[0]);
        IzvodjenjePredstave ip = IzvodjenjePredstave.getIzvodjenjePredstaveById(idIzvodjenjaPredstave);
        int maksBrojKarata = ip.getPozoriste().getBrojSjedista() - ip.getBrojRezervisanihMjesta();
        SpinnerValueFactory<Integer> brojKarata = new SpinnerValueFactory
                .IntegerSpinnerValueFactory(1, maksBrojKarata, 1);
        ktSpinner.setValueFactory(brojKarata);
    }

    public void rezervisiKartu() {
        if (!ktPpListView.getSelectionModel().isEmpty() && !ktIpListView.getSelectionModel().isEmpty()) {
            String odabirPp = ktPpListView.getSelectionModel().getSelectedItem();
            String odabirIp = ktIpListView.getSelectionModel().getSelectedItem();
            int idPosjetiocaPozorista = Integer.parseInt(odabirPp.split("\\.")[0]);
            int idIzvodjenjaPredstave = Integer.parseInt(odabirIp.split("\\.")[0]);
            PosjetilacPozorista pp = PosjetilacPozorista.getPosjetilacPozoristaById(idPosjetiocaPozorista);
            IzvodjenjePredstave ip = IzvodjenjePredstave.getIzvodjenjePredstaveById(idIzvodjenjaPredstave);
            int brojKarata = ktSpinner.getValue();

            if (ip.getBrojRezervisanihMjesta() < ip.getPozoriste().getBrojSjedista()) {
                int postojiId = Karta.vecPostojiKarta(idPosjetiocaPozorista, idIzvodjenjaPredstave);
                if (postojiId != -1) {
                    Karta karta = Karta.getKartaById(postojiId);
                    if (karta != null) {
                        IzmjenaBaze.izmjenaKarte(karta.getBrojKarta() + brojKarata, postojiId);
                        karta.setBrojKarta(karta.getBrojKarta() + brojKarata);
                    } else {
                        System.err.println("Karta ne postoji! (Radnik.rezervisiKartu())");
                    }
                } else {
                    int id = IzmjenaBaze.posaljiKarta(idIzvodjenjaPredstave, 2, idPosjetiocaPozorista,
                            brojKarata);
                    new Karta(id, idIzvodjenjaPredstave, 2, idPosjetiocaPozorista, brojKarata);
                }
                if (ip.getDatumIVrijeme().getTime() < 172800000) {
                    prozorObavjestenja("Upozorenje!",
                            "Posjetiocu je ostalo manje od 48 sati da preuzme rezervisanu kartu");
                }
                ispisiIzvodjenjaPredstava();
                //System.out.println("Karta rezervisana!");
                prozorObavjestenja("Gotovo", "Karta uspješno rezervisana!");
            } else {
                prozorObavjestenja("Greška", "Nije dostupno nijedno slobodno mjesto.");
            }
        }
    }

    public void dodajPredstavu() {

        String naziv = predNazivTextBox.getText().strip();
        String zanr = predZanrComboBox.getValue();
        String odabraniAutor = predAutorListView.getSelectionModel().getSelectedItem();
        String odabraniReziser = predReziserListView.getSelectionModel().getSelectedItem();
        ObservableList<String> odabraniGlumci = predGlumciListView.getSelectionModel().getSelectedItems();


        int zanrValue = Predstava.Zanrovi.valueOf(zanr.toUpperCase()).ordinal() + 1;
        if (!Predstava.postoji(naziv)) {
            if (!naziv.isEmpty() && !zanr.isEmpty() && !odabraniAutor.isEmpty() && !odabraniReziser.isEmpty() &&
                    !odabraniGlumci.isEmpty()) {

                int idPredstave = IzmjenaBaze.posaljiPredstava(naziv, zanrValue);
                Predstava predstava = new Predstava(idPredstave, naziv, zanrValue);
                System.out.println("Predstava dodata");

                Osoblje autor = Osoblje.getOsobljeByImeIPrezime(odabraniAutor.split(" ")[0],
                        odabraniAutor.split(" ")[1]);
                int idAutoraPredstave = IzmjenaBaze.posaljiOsobljePredstave(autor.getId(), predstava.getId());
                new OsobljePredstave(idAutoraPredstave, autor.getId(), predstava.getId());
                System.out.println("Autor dodat");

                Osoblje reziser = Osoblje.getOsobljeByImeIPrezime(odabraniReziser.split(" ")[0],
                        odabraniReziser.split(" ")[1]);
                int idReziseraPredstave = IzmjenaBaze.posaljiOsobljePredstave(reziser.getId(), predstava.getId());
                new OsobljePredstave(idReziseraPredstave, reziser.getId(), predstava.getId());
                System.out.println("Reziser dodat");

                for (String s : odabraniGlumci) {
                    Osoblje glumac = Osoblje.getOsobljeByImeIPrezime(s.split(" ")[0], s.split(" ")[1]);
                    int idGlumcaPredstave = IzmjenaBaze.posaljiOsobljePredstave(glumac.getId(), predstava.getId());
                    new OsobljePredstave(idGlumcaPredstave, glumac.getId(), predstava.getId());
                    System.out.println("Glumac dodat");
                }

                ispisiOsoblje();
                predNazivTextBox.clear();

                prozorObavjestenja("Gotovo", "Dodata nova predstava!");
            }
        } else {
            prozorObavjestenja("Greška", "Predstava već postoji!");
        }


    }

    public void dodajOsoblje() {
        String ime = predImeTextBox.getText();
        String prezime = predPrezimeTextBox.getText();
        String tip = predTipComboBox.getValue();
        int tipValue = Osoblje.Tipovi.valueOf(tip.toUpperCase()).ordinal() + 1;
        if (!ime.isEmpty() && !prezime.isEmpty() && !tip.isEmpty()) {
            int id = IzmjenaBaze.posaljiOsoblje(ime, prezime, tipValue);
            new Osoblje(id, ime, prezime, tipValue);
            prozorObavjestenja("Gotovo", "Novo osoblje dodato");
            ispisiOsoblje();
            predImeTextBox.clear();
            predPrezimeTextBox.clear();
        }
    }

    public void odabirPredstaveTermini() {
        tmTextArea.clear();
        RadnikPozorista rp = (RadnikPozorista) Korisnik.prijavljeniKorisnik;
        String nazivPredstave = tmPredstaveListView.getSelectionModel().getSelectedItem();
        Predstava predstava = Predstava.getPredstavaByNaziv(nazivPredstave);
        if (predstava != null) {
            for (IzvodjenjePredstave ip : IzvodjenjePredstave.svaIzvodjenjaPredstava) {
                if (ip.getPredstava().equals(predstava) && ip.getPozoriste().equals(rp.getPozoriste())) {
                    tmTextArea.appendText(ip.getDatumIVrijeme().toString() + " ");
                    tmTextArea.appendText(ip.getCijena() + "KM\n");
                }
            }
        }

    }

    public void dodajTermin() {
        LocalDate datum = tmDatum.getValue();
        int sati = Integer.parseInt(tmSati.getText());
        int minute = Integer.parseInt(tmMinute.getText());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, datum.getYear());
        calendar.set(Calendar.MONTH, datum.getMonthValue());
        calendar.set(Calendar.DATE, datum.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, sati);
        calendar.set(Calendar.MINUTE, minute);
        Timestamp termin = new Timestamp(calendar.getTimeInMillis());
        double cijena = Double.parseDouble(tmCijena.getText());
        Pozoriste pozoriste = ((RadnikPozorista)Korisnik.prijavljeniKorisnik).getPozoriste();
        Predstava predstava = Predstava.getPredstavaByNaziv(tmPredstaveListView.getSelectionModel().getSelectedItem());

        int id = IzmjenaBaze.posaljiIzvodjenjePredstave(predstava.getId(), pozoriste.getId(), cijena, termin);
        new IzvodjenjePredstave(id, predstava.getId(), pozoriste.getId(), cijena, termin);
        prozorObavjestenja("Gotovo", "Dodat novi termin");
        tmSati.clear();
        tmMinute.clear();
        tmCijena.clear();
    }

}
