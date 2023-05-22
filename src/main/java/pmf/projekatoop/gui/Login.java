package pmf.projekatoop.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pmf.projekatoop.application.Korisnik;
import pmf.projekatoop.application.RadnikPozorista;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login extends Controller implements Initializable {
    public Button loginButton;
    public PasswordField loginLozinkaField;
    public TextField loginKorisnickoImeField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Korisnik.prijavljeniKorisnik = null;
    }

    public void promjeniScenuLoginButton(ActionEvent event) throws IOException {
        String korisnickoIme = loginKorisnickoImeField.getText().strip();
        String hesLozinka = hesirajLozinku(loginLozinkaField.getText());
        Korisnik korisnik = Korisnik.getKorisnikByKorisnickoIme(korisnickoIme);
        if (korisnik != null) {
            if (korisnik.getLozinka().equals(hesLozinka)) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(false);
                Korisnik.prijavljeniKorisnik = korisnik;
                if (korisnik instanceof RadnikPozorista) {
                    Parent radnikParent = FXMLLoader.load(getClass().getResource("radnikView.fxml"));
                    Scene radnikScene = new Scene(radnikParent, 800, 600);
                    stage.setScene(radnikScene);
                } else {
                    Parent posjetilacParent = FXMLLoader.load(getClass().getResource("posjetilacView.fxml"));
                    Scene posjetilacScene = new Scene(posjetilacParent, 800, 600);
                    stage.setScene(posjetilacScene);
                }
                stage.show();
            } else {
                prozorObavjestenja("Greška", "Pogresna lozinka!");
                loginKorisnickoImeField.clear();
                loginLozinkaField.clear();
            }
        } else {
            prozorObavjestenja("Greška", "Korisnik ne postoji!");
            loginKorisnickoImeField.clear();
            loginLozinkaField.clear();
        }
    }

    public void promijeniScenuRegistracija(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent registracijaParent = FXMLLoader.load(getClass().getResource("registracijaView.fxml"));
        Scene registracijaScene = new Scene(registracijaParent);
        stage.setScene(registracijaScene);
        stage.setResizable(false);
        stage.show();
    }

}
