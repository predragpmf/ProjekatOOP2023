package pmf.projekatoop.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pmf.projekatoop.application.Korisnik;
import pmf.projekatoop.application.PosjetilacPozorista;
import pmf.projekatoop.database.IzmjenaBaze;

public class Registracija extends Controller {

    public TextField regImeField;
    public TextField regPrezimeField;
    public TextField regKorisnickoImeField;
    public PasswordField regLozinkaField;


    public void registracijaKorisnika(ActionEvent event) {
        String korisnickoIme = regKorisnickoImeField.getText().strip();
        if (Korisnik.getKorisnikByKorisnickoIme(korisnickoIme) == null) {
            String ime = regImeField.getText().strip();
            String prezime = regPrezimeField.getText().strip();
            String lozinka = regLozinkaField.getText().strip();
            if (lozinka.length() < 5) {
                prozorObavjestenja("Greška", "Lozinka mora biti duža od 5 karaktera!");
            } else {
                String lozinkaHes = hesirajLozinku(regLozinkaField.getText().strip());
                int id = IzmjenaBaze.posaljiPosjetilacPozorista(ime, prezime, korisnickoIme, lozinkaHes);
                new PosjetilacPozorista(id, ime, prezime, korisnickoIme, lozinkaHes);
                prozorObavjestenja("Gotovo", "Korisnik je uspješno registrovan!");
                promijeniScenuLogin(event);
            }
        } else {
            prozorObavjestenja("Greška", "Korisničko ime je zauzeto!");
            regKorisnickoImeField.clear();
        }
    }

}

