package pmf.projekatoop.gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import pmf.projekatoop.application.Korisnik;
import pmf.projekatoop.application.RadnikPozorista;
import pmf.projekatoop.database.IzmjenaBaze;

import java.net.URL;
import java.util.ResourceBundle;

public class Radnik extends Controller implements Initializable {

    public TextArea infoTextArea;
    public PasswordField infoStaraTextBox;
    public PasswordField infoNovaTextBox;

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

}
