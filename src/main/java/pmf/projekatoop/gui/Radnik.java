package pmf.projekatoop.gui;

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
import java.util.Comparator;
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
    public ListView<String> posPpListView;
    public ListView<String> posKtListView;
    public Spinner<Integer> posKtSpinner;
    public ListView<String> osobljeListView;
    public TextField krImeTextBox;
    public TextField krPrezimeTextBox;
    public PasswordField krLozinkaTextBox;
    public TextField krKorImeTextBox;
    public ComboBox<String> krPozComboBox;
    public TextField krPozNazivTextBox;
    public TextField krPozGradTextBox;
    public TextField krPozSjedistaTextBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!(Korisnik.prijavljeniKorisnik instanceof RadnikPozorista)) {
            System.err.println("Pogresan korisnik prijavljen!");
        }

        // Informacije tab:
        RadnikPozorista rp = (RadnikPozorista) Korisnik.prijavljeniKorisnik;
        infoTextArea.appendText("Ime i prezime:\n");
        infoTextArea.appendText("\t" + rp.getIme() + " " + rp.getPrezime() + "\n\n");
        infoTextArea.appendText("Korisničko ime:\n");
        infoTextArea.appendText("\t" + rp.getKorisnickoIme() + "\n\n");
        infoTextArea.appendText("Pozorište:\n");
        infoTextArea.appendText("\t" + rp.getPozoriste().getNaziv() + "\n\n");
        infoTextArea.appendText("Grad:\n");
        infoTextArea.appendText("\t" + rp.getPozoriste().getGrad());

        // Pozoriste tab:
        pozoristeComboBox.getItems().add("Naredne");
        pozoristeComboBox.getItems().add("Prošle");

        // Karte tab:
        for (PosjetilacPozorista pp : PosjetilacPozorista.sviPosjetiociPozorista) {
            ktPpListView.getItems().add(pp.getId() + ". " + pp.getIme() + " " + pp.getPrezime() + " - " +
                    pp.getKorisnickoIme());
        }
        ispisiIzvodjenjaPredstava();

        // Predstava tab:
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

        // Termini tab:
        for (Predstava p : Predstava.svePredstave) {
            tmPredstaveListView.getItems().add(p.getNaziv());
        }

        // Posjetioci tab:
        for (PosjetilacPozorista pp : PosjetilacPozorista.sviPosjetiociPozorista) {
            posPpListView.getItems().add(pp.getId() + ". " + pp.getIme() + " " + pp.getPrezime() + " - " +
                    pp.getKorisnickoIme());
        }

        // Osoblje tab:
        prikaziOsoblje();

        // Kreiranje tab:
        for (Pozoriste pozoriste : Pozoriste.svaPozorista) {
            krPozComboBox.getItems().add(pozoriste.getId() + ". " + pozoriste.getNaziv());
        }
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

    public void odjavaTipka(ActionEvent event) {
        promijeniScenuLogin(event);
    }

    // Promjeni lozinku tipka:
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

    // Pozoriste tab spinner:
    public void odabirVrstePredstave() {
        pozoristeListView.getItems().clear();
        pozoristeTextArea.clear();
        pozoristeKtTextArea.clear();
        RadnikPozorista rp = (RadnikPozorista) Korisnik.prijavljeniKorisnik;
        Pozoriste pozoriste = rp.getPozoriste();
        if (pozoristeComboBox.getValue().equals("Naredne")) {
            for (IzvodjenjePredstave ip : IzvodjenjePredstave.svaIzvodjenjaPredstava) {
                if (ip.getPozoriste().equals(pozoriste) &&
                        ip.getDatumIVrijeme().getTime() > System.currentTimeMillis()) {
                    pozoristeListView.getItems().add(ip.getId() + ". " + ip.getPredstava().getNaziv() + " " +
                            ip.getDatumIVrijeme());
                }
            }
        } else if (pozoristeComboBox.getValue().equals("Prošle")) {
            for (IzvodjenjePredstave ip : IzvodjenjePredstave.svaIzvodjenjaPredstava) {
                if (ip.getPozoriste().equals(pozoriste) &&
                        ip.getDatumIVrijeme().getTime() < System.currentTimeMillis()) {
                    pozoristeListView.getItems().add(ip.getId() + ". " + ip.getPredstava().getNaziv() + " " +
                            ip.getDatumIVrijeme());
                }
            }
        }
    }

    // Pozoriste spisak predstava ListView:
    public void odabirPredstave() {
        pozoristeTextArea.clear();
        pozoristeKtTextArea.clear();
        String odabir = pozoristeListView.getSelectionModel().getSelectedItem();
        int id = Integer.parseInt(odabir.split("\\.")[0]);
        IzvodjenjePredstave ip = IzvodjenjePredstave.getIzvodjenjePredstaveById(id);
        if (ip != null) {
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
        } else {
            System.err.println("IzvodjenjePredstave ne postoji!");
        }
    }

    // Odabir izvodjenja predstave sa karte taba:
    public void odabirIzvodjenjaPredstave() {
        String odabirIp = ktIpListView.getSelectionModel().getSelectedItem();
        int idIzvodjenjaPredstave = Integer.parseInt(odabirIp.split("\\.")[0]);
        IzvodjenjePredstave ip = IzvodjenjePredstave.getIzvodjenjePredstaveById(idIzvodjenjaPredstave);
        if (ip != null) {
            int maksBrojKarata = ip.getPozoriste().getBrojSjedista() - ip.getBrojRezervisanihMjesta();
            SpinnerValueFactory<Integer> brojKarata = new SpinnerValueFactory
                    .IntegerSpinnerValueFactory(1, maksBrojKarata, 1);
            ktSpinner.setValueFactory(brojKarata);
        } else {
            System.err.println("IzvodjenjePredstave ne postoji!");
        }
    }

    // Rezervisi kartu tipka:
    public void rezervisiKartu() {
        if (!ktPpListView.getSelectionModel().isEmpty() && !ktIpListView.getSelectionModel().isEmpty()) {
            String odabirPp = ktPpListView.getSelectionModel().getSelectedItem();
            String odabirIp = ktIpListView.getSelectionModel().getSelectedItem();
            int idPosjetiocaPozorista = Integer.parseInt(odabirPp.split("\\.")[0]);
            int idIzvodjenjaPredstave = Integer.parseInt(odabirIp.split("\\.")[0]);
            IzvodjenjePredstave ip = IzvodjenjePredstave.getIzvodjenjePredstaveById(idIzvodjenjaPredstave);
            int brojKarata = ktSpinner.getValue();
            if (ip != null) {
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
                        int id = IzmjenaBaze.posaljiKarta(idIzvodjenjaPredstave, 1, idPosjetiocaPozorista,
                                brojKarata);
                        new Karta(id, idIzvodjenjaPredstave, 1, idPosjetiocaPozorista, brojKarata);
                    }
                    if (ip.getDatumIVrijeme().getTime() < 172800000) {
                        prozorObavjestenja("Upozorenje!",
                                "Posjetiocu je ostalo manje od 48 sati da preuzme rezervisanu kartu");
                    }
                    ispisiIzvodjenjaPredstava();
                    prozorObavjestenja("Gotovo", "Karta uspješno rezervisana!");
                } else {
                    prozorObavjestenja("Greška", "Nije dostupno nijedno slobodno mjesto.");
                }
            } else {
                System.err.println("IzvodjenjePredstave ne postoji!");
            }
        }
    }

    // Dodaj predstavu tipka:
    public void dodajPredstavu() {
        String naziv = predNazivTextBox.getText().strip();
        String zanr = predZanrComboBox.getValue();
        String odabraniAutor = predAutorListView.getSelectionModel().getSelectedItem();
        String odabraniReziser = predReziserListView.getSelectionModel().getSelectedItem();
        ObservableList<String> odabraniGlumci = predGlumciListView.getSelectionModel().getSelectedItems();
        Osoblje autor = Osoblje.getOsobljeByImeIPrezime(odabraniAutor.split(" ")[0],
                odabraniAutor.split(" ")[1]);
        Osoblje reziser = Osoblje.getOsobljeByImeIPrezime(odabraniReziser.split(" ")[0],
                odabraniReziser.split(" ")[1]);
        int zanrValue = Predstava.Zanrovi.valueOf(zanr.toUpperCase()).ordinal() + 1;

        if (!Predstava.postoji(naziv)) {
            if (!naziv.isEmpty() && !zanr.isEmpty() && autor != null && reziser != null &&
                    !odabraniGlumci.isEmpty()) {

                int idPredstave = IzmjenaBaze.posaljiPredstava(naziv, zanrValue);
                Predstava predstava = new Predstava(idPredstave, naziv, zanrValue);
                System.out.println("Predstava dodata");

                int idAutoraPredstave = IzmjenaBaze.posaljiOsobljePredstave(autor.getId(), predstava.getId());
                new OsobljePredstave(idAutoraPredstave, autor.getId(), predstava.getId());
                System.out.println("Autor dodat");

                int idReziseraPredstave = IzmjenaBaze.posaljiOsobljePredstave(reziser.getId(), predstava.getId());
                new OsobljePredstave(idReziseraPredstave, reziser.getId(), predstava.getId());
                System.out.println("Reziser dodat");

                for (String glumci : odabraniGlumci) {
                    Osoblje glumac = Osoblje.getOsobljeByImeIPrezime(glumci.split(" ")[0],
                            glumci.split(" ")[1]);
                    if (glumac != null) {
                        int idGlumcaPredstave = IzmjenaBaze.posaljiOsobljePredstave(glumac.getId(), predstava.getId());
                        new OsobljePredstave(idGlumcaPredstave, glumac.getId(), predstava.getId());
                        System.out.println("Glumac dodat");
                    } else {
                        System.err.println("Greska pri dodavanju glumaca!");
                    }
                }
                ispisiOsoblje();
                predNazivTextBox.clear();
                prozorObavjestenja("Gotovo", "Dodata nova predstava!");
                tmPredstaveListView.getItems().clear();
                for (Predstava p : Predstava.svePredstave) {
                    tmPredstaveListView.getItems().add(p.getNaziv());
                }
            } else {
                System.err.println("Pogresan unos!");
            }
        } else {
            prozorObavjestenja("Greška", "Predstava već postoji!");
        }
    }

    // Dodaj osoblje tipka:
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

    // Termini odabir predstave ListView
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

    // Dodaj termin tipka:
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
        Pozoriste pozoriste = ((RadnikPozorista) Korisnik.prijavljeniKorisnik).getPozoriste();
        Predstava predstava = Predstava.getPredstavaByNaziv(tmPredstaveListView.getSelectionModel().getSelectedItem());
        if (predstava != null) {
            int id = IzmjenaBaze.posaljiIzvodjenjePredstave(predstava.getId(), pozoriste.getId(), cijena, termin);
            new IzvodjenjePredstave(id, predstava.getId(), pozoriste.getId(), cijena, termin);
            prozorObavjestenja("Gotovo", "Dodat novi termin");
            tmSati.clear();
            tmMinute.clear();
            tmCijena.clear();
        } else {
            System.err.println("Greska pri dodavanju termina!");
        }
    }

    // Posjetioci - odabir posjetioca ListView:
    public void odabirPosjetioca() {
        posKtListView.getItems().clear();
        String odabraniPosjetilac = posPpListView.getSelectionModel().getSelectedItem();
        int idPosjetioca = Integer.parseInt(odabraniPosjetilac.split("\\.")[0]);
        PosjetilacPozorista pp = PosjetilacPozorista.getPosjetilacPozoristaById(idPosjetioca);
        Pozoriste pozoriste = ((RadnikPozorista) Korisnik.prijavljeniKorisnik).getPozoriste();

        for (Karta karta : Karta.sveKarte) {
            if (karta.getPosjetilacPozorista().equals(pp) &&
                    karta.getIzvodjenjePredstave().getPozoriste().equals(pozoriste)) {
                posKtListView.getItems().add(karta.getId() + ". " +
                        karta.getIzvodjenjePredstave().getPredstava().getNaziv() + ", " +
                        karta.getIzvodjenjePredstave().getDatumIVrijeme() + ", broj: " + karta.getBrojKarta() +
                        ", status: " + karta.getStatus().toString());
            }
        }
    }

    // Posjetioci - odabir rezervacije karte ListView:
    public void odabirRezervacije() {
        String odabranaKarta = posKtListView.getSelectionModel().getSelectedItem();
        int idKarte = Integer.parseInt(odabranaKarta.split("\\.")[0]);
        Karta karta = Karta.getKartaById(idKarte);
        if (karta != null) {
            int maksBrojKarata = karta.getBrojKarta();
            SpinnerValueFactory<Integer> brojKarata = new SpinnerValueFactory
                    .IntegerSpinnerValueFactory(1, maksBrojKarata, 1);
            posKtSpinner.setValueFactory(brojKarata);
        }
    }

    // Predaj karte tipka:
    public void predajKarte() {
        if (!posKtListView.getSelectionModel().isEmpty()) {
            String odabranaKarta = posKtListView.getSelectionModel().getSelectedItem();
            int idKarte = Integer.parseInt(odabranaKarta.split("\\.")[0]);
            Karta karta = Karta.getKartaById(idKarte);
            if (karta != null) {
                if (karta.getStatus().toString().equals("REZERVISANA")) {
                    karta.setStatus(Karta.Statusi.REZERVISANA_PREUZETA);
                } else {
                    prozorObavjestenja("Greška", "Karte su već preuzete!");
                }
            } else {
                System.err.println("Karta ne postoji!");
            }
        }
    }

    // Otkazi karte tipka:
    public void otkaziKarte() {
        String odabranaKarta = posKtListView.getSelectionModel().getSelectedItem();
        int idKarte = Integer.parseInt(odabranaKarta.split("\\.")[0]);
        Karta karta = Karta.getKartaById(idKarte);
        int brojOtkazanih = posKtSpinner.getValue();
        if (karta != null) {
            if (karta.getStatus().toString().equals("REZERVISANA")) {
                if (brojOtkazanih == karta.getBrojKarta()) {
                    IzmjenaBaze.brisanjeKarte(karta.getId());
                    Karta.sveKarte.remove(karta);
                } else {
                    IzmjenaBaze.izmjenaKarte(karta.getBrojKarta() - brojOtkazanih, karta.getId());
                    karta.setBrojKarta(karta.getBrojKarta() - brojOtkazanih);
                }
                prozorObavjestenja("Gotovo", "Karte uspješno otkazane!");
                odabirPosjetioca();
            } else {
                prozorObavjestenja("Greška", "Karta je već preuzeta");
            }
        } else {
            System.err.println("Karta ne postoji!");
        }
    }

    // Osoblje - prikazi osobljeListView:
    private void prikaziOsoblje() {
        Osoblje.svoOsoblje.sort(Comparator.comparing(Osoblje::getBrojIzvodjenja, Comparator.reverseOrder()));
        Osoblje.svoOsoblje.sort(Comparator.comparing(Osoblje::getTip));
        for (Osoblje osoblje : Osoblje.svoOsoblje) {
            osobljeListView.getItems().add(osoblje + ", " + osoblje.getTip().toString());
        }
    }

    // Kreiranje - pritisak tipke Kreiraj radnika:
    public void kreirajRadnika() {
        String ime = krImeTextBox.getText();
        String prezime = krPrezimeTextBox.getText();
        String korisnickoIme = krKorImeTextBox.getText();
        String lozinka = krLozinkaTextBox.getText();
        String hesLozinka = hesirajLozinku(lozinka);
        String odabranoPozoriste = krPozComboBox.getValue();
        int idPozorista = Integer.parseInt(odabranoPozoriste.split("\\.")[0]);
        Pozoriste pozoriste = Pozoriste.getPozoristeById(idPozorista);
        RadnikPozorista rp = (RadnikPozorista) Korisnik.prijavljeniKorisnik;
        if (pozoriste != null) {
            if (pozoriste.equals(rp.getPozoriste()) || pozoriste.getBrojRadnika() == 0) {
                int id = IzmjenaBaze.posaljiRadnikPozorista(ime, prezime, korisnickoIme, hesLozinka, idPozorista);
                new RadnikPozorista(id, ime, prezime, korisnickoIme, hesLozinka, idPozorista);
                prozorObavjestenja("Gotovo", "Kreiran novi radnik");
                krImeTextBox.clear();
                krPrezimeTextBox.clear();
                krKorImeTextBox.clear();
                krLozinkaTextBox.clear();
            } else {
                prozorObavjestenja("Greška", "Nije moguće dodati novog radnika!");
            }
        }
    }

    // Kreiranje - pritisak tipke Kreiraj pozoriste:
    public void kreirajPozoriste() {
        String naziv = krPozNazivTextBox.getText();
        String grad = krPozGradTextBox.getText();
        String sjedista = krPozSjedistaTextBox.getText();
        if (!naziv.isEmpty() && !grad.isEmpty() && !sjedista.isEmpty()) {
            int brojSjedista = Integer.parseInt(sjedista);
            int id = IzmjenaBaze.posaljiPozoriste(naziv, grad, brojSjedista);
            new Pozoriste(id, naziv, grad, brojSjedista);
            prozorObavjestenja("Gotovo", "Kreirano novo pozorište");
            krPozNazivTextBox.clear();
            krPozGradTextBox.clear();
            krPozSjedistaTextBox.clear();
            krPozComboBox.getItems().clear();
            for (Pozoriste pozoriste : Pozoriste.svaPozorista) {
                krPozComboBox.getItems().add(pozoriste.getId() + ". " + pozoriste.getNaziv());
            }
        }
    }

}
